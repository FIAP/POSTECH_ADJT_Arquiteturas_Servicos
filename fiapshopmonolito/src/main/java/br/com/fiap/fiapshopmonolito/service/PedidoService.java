package br.com.fiap.fiapshopmonolito.service;

import br.com.fiap.fiapshopmonolito.model.ItemPedido;
import br.com.fiap.fiapshopmonolito.model.Pedido;
import br.com.fiap.fiapshopmonolito.repository.PedidoRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;


    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public Pedido criarPedido(Pedido pedido) {

        // Passo 1: Verificar a disponibilidade dos produtos
        boolean produtosDisponiveis = verificarDisponibilidadeProdutos(pedido.getItensPedido());

        if (!produtosDisponiveis) {
            throw new NoSuchElementException("Um ou mais produtos não estão disponíveis.");
        }

        // Passo 2: Calcular o valor total do pedido
        double valorTotal = calcularValorTotal(pedido.getItensPedido());
        pedido.setValorTotal(valorTotal);
//
        // Passo 3: Atualize o estoque chamando o Microsserviço de Produtos
        atualizarEstoqueProdutos(pedido.getItensPedido());

        // Passo 4: Salvar o pedido no banco de dados
        return pedidoRepository.save(pedido);
    }

    private boolean verificarDisponibilidadeProdutos(List<ItemPedido> itensPedido) {

        for (ItemPedido itemPedido : itensPedido) {
            Integer idProduto = itemPedido.getIdProduto();
            int quantidade = itemPedido.getQuantidade();

            ResponseEntity<String> response = restTemplate.getForEntity(
                    "http://localhost:8083/v2/api/produtos/{produtoId}",
                    String.class,
                    idProduto
            );

            if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new NoSuchElementException("Produto não encontrado");
            } else if (response.getStatusCode() == HttpStatus.OK) {
                try {
                    JsonNode produtoJson = objectMapper.readTree(response.getBody());
                    int quantidadeEstoque = produtoJson.get("quantidade_estoque").asInt();

                    if (quantidadeEstoque < quantidade) {
                        return false; // Produto não disponível em quantidade suficiente
                    }
                } catch (IOException e) {
                    // Trate qualquer exceção de desserialização, se necessário
                    System.out.println("Erro de desserialização:" + e.getMessage());
                }
            }
        }
        return true; // Todos os produtos estão disponíveis
    }

    private double calcularValorTotal(List<ItemPedido> itensPedido) {

        double valorTotal = 0.0;

        for (ItemPedido itemPedido : itensPedido) {
            Integer idProduto = itemPedido.getIdProduto();
            int quantidade = itemPedido.getQuantidade();

            ResponseEntity<String> response = restTemplate.getForEntity(
                    "http://localhost:8083/v2/api/produtos/{produtoId}",
                    String.class,
                    idProduto
            );

            if (response.getStatusCode() == HttpStatus.OK) {
                try {
                    JsonNode produtoJson = objectMapper.readTree(response.getBody());
                    double preco = produtoJson.get("preco").asDouble();
                    valorTotal += preco * quantidade;
                } catch (IOException e) {
                    // Trate qualquer exceção de desserialização, se necessário
                }
            }
        }

        return valorTotal;
    }


    private void atualizarEstoqueProdutos(List<ItemPedido> itensPedido) {
        for (ItemPedido itemPedido : itensPedido) {
            Integer idProduto = itemPedido.getIdProduto();
            int quantidade = itemPedido.getQuantidade();

            restTemplate.put(
                    "http://localhost:8083/v2/api/produtos/atualizar/estoque/{produtoId}/{quantidade}",
                    null,
                    idProduto,
                    quantidade
            );
        }
    }


    public List<Pedido> listarPedidos(){
        return pedidoRepository.findAll();
    }

    public Pedido listarPedidoPorId(String pedidoId) {
        return pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new NoSuchElementException("Pedido não encontrado"));
    }

    public List<Pedido> listarProdutoPorNomeCliente(String nome) {
        return pedidoRepository.findByNomeCliente(nome);
    }

    public Pedido atualizarPedido(String pedidoId, Pedido novoPedido) {
        Pedido pedidoExistente = pedidoRepository.findById(pedidoId).orElseThrow(() -> new NoSuchElementException("Pedido não encontrado"));

        pedidoExistente.setNomeCliente(novoPedido.getNomeCliente());
        pedidoExistente.setItensPedido(novoPedido.getItensPedido());
        pedidoExistente.setValorTotal(novoPedido.getValorTotal());

        return pedidoRepository.save(pedidoExistente);
    }

    public void excluirPedido(String pedidoId) {
        Pedido pedidoExistente = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new NoSuchElementException("Pedido não encontrado"));

        // Exclua o pedido do banco de dados
        pedidoRepository.delete(pedidoExistente);
    }
}


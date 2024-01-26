package br.com.fiap.gerenciadorpedido.controller;

import br.com.fiap.gerenciadorpedido.model.Pedido;
import br.com.fiap.gerenciadorpedido.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;


@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<?> criarPedido(@RequestBody Pedido pedido) {

        try {
            Pedido novoPedido = pedidoService.criarPedido(pedido);
            return new ResponseEntity<>(novoPedido, HttpStatus.CREATED);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("Um ou mais produtos não estão disponíveis.", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public List<Pedido> listarPedidos() {
        return pedidoService.listarPedidos();
    }

    @GetMapping("/{pedidoId}")
    public Pedido listarPedidoPorId(@PathVariable String pedidoId) {
        return pedidoService.listarPedidoPorId(pedidoId);
    }

    @GetMapping("/cliente")
    public List<Pedido> listarPedidoPorNomeCliente(@RequestHeader("cliente") String cliente) {
        return pedidoService.listarProdutoPorNomeCliente(cliente);
    }

    @PutMapping("/{pedidoId}")
    public ResponseEntity<Pedido> atualizarPedido(@PathVariable String pedidoId, @RequestBody Pedido novoPedido) {
        Pedido pedidoAtualizado = pedidoService.atualizarPedido(pedidoId, novoPedido);
        return ResponseEntity.ok(pedidoAtualizado);
    }

    @DeleteMapping("/{pedidoId}")
    public ResponseEntity<Void> excluirPedido(@PathVariable String pedidoId) {
        pedidoService.excluirPedido(pedidoId);
        return ResponseEntity.noContent().build();
    }
}

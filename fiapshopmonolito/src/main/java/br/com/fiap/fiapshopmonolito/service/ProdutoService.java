package br.com.fiap.fiapshopmonolito.service;

import br.com.fiap.fiapshopmonolito.model.Produto;
import br.com.fiap.fiapshopmonolito.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public List<Produto> listarProdutos() {
        return produtoRepository.findAll();
    }

    public ResponseEntity<?> listarUmProduto(Integer produtoId) {


        Produto produto = produtoRepository.findById(produtoId).orElse(null);
        if (produto != null) {
            return ResponseEntity.ok(produto); // Retorna 200 OK com o produto encontrado
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado");
        }
    }

    public Produto cadastrarProduto(Produto produto) {

        return produtoRepository.save(produto);
    }

    public Produto atualizarProduto(Integer produtoId, Produto novoProduto) {
        Produto produtoExistente = produtoRepository.findById(produtoId).orElse(null);

        if (produtoExistente != null) {
            produtoExistente.setNome(novoProduto.getNome());
            produtoExistente.setDescricao(novoProduto.getDescricao());
            produtoExistente.setQuantidade_estoque(novoProduto.getQuantidade_estoque());

            return produtoRepository.save(produtoExistente);
        } else {
            throw new NoSuchElementException("Produto não encontrado");
        }
    }

    public void excluirProduto(Integer produtoId) {
        Produto produtoExistente = produtoRepository.findById(produtoId).orElse(null);

        if (produtoExistente != null) {
            produtoRepository.delete(produtoExistente);
        } else {
            throw new NoSuchElementException("Produto não encontrado");
        }
    }

    public Produto atualizarEstoque(Integer produtoId, int quantidade) {
        Produto produto = produtoRepository.findById(produtoId).orElse(null);
        if (produto != null) {
            produto.setQuantidade_estoque(produto.getQuantidade_estoque() - quantidade);

            return produtoRepository.save(produto);
        }
        return null;
    }
}

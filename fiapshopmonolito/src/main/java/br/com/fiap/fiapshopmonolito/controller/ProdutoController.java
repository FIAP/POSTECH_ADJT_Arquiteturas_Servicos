package br.com.fiap.fiapshopmonolito.controller;

import br.com.fiap.fiapshopmonolito.model.Produto;
import br.com.fiap.fiapshopmonolito.service.ProdutoService;
import jakarta.persistence.Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v2/api/produtos") //EXPLICAR ESSE CONCEITO de APi REST
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping
    public List<Produto> listarProdutos() {
        return produtoService.listarProdutos();
    }

    @GetMapping("/{produtoId}")
    public ResponseEntity<?> listarUmProduto(@PathVariable Integer produtoId) {
        return produtoService.listarUmProduto(produtoId);
    }
    @PostMapping
    public Produto cadastrarProduto(@RequestBody Produto produto) {
        return produtoService.cadastrarProduto(produto);
    }

    @PutMapping("/{produtoId}")
    public Produto atualizarProduto(
            @PathVariable Integer produtoId, @RequestBody Produto novoProduto) {

        return produtoService.atualizarProduto(produtoId, novoProduto);
    }

    @DeleteMapping("/{produtoId}")
    public void excluirProduto(@PathVariable Integer produtoId) {
        produtoService.excluirProduto(produtoId);
    }

    @PutMapping("/atualizar/estoque/{produtoId}/{quantidade}")
    public Produto atualizarEstoque(
            @PathVariable Integer produtoId, @PathVariable int quantidade) {
        return produtoService.atualizarEstoque(produtoId,quantidade);
    }
}

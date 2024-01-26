package br.com.fiap.gerenciadorproduto.repository;

import br.com.fiap.gerenciadorproduto.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
}

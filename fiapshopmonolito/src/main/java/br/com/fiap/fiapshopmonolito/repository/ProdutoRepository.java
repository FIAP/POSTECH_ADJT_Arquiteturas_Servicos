package br.com.fiap.fiapshopmonolito.repository;

import br.com.fiap.fiapshopmonolito.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
}

package br.com.fiap.gerenciadorpedido.repository;

import br.com.fiap.gerenciadorpedido.model.Pedido;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface PedidoRepository extends MongoRepository<Pedido, String> {

    @Query("{ 'nomeCliente' : { $regex: ?0, $options: 'i' } }")
    List<Pedido> findByNomeCliente(String nomeCliente);
}

package br.com.fiap.gerenciadorpedido.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ItemPedido {

    private Integer idProduto;
    private int quantidade;
}

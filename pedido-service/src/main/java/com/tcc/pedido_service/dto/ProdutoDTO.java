package com.tcc.pedido_service.dto;

import lombok.Data;

@Data
public class ProdutoDTO {
    private Long id;
    private String nome;
    private Double preco;
    private Integer estoque;
}

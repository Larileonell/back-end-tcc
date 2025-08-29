package com.tcc.pedido_service.dto;

import lombok.Data;

@Data
public class PedidoDTO {
    private Long userId;
    private Long produtoId;
    private Integer quantidade;

}
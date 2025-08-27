package com.tcc.pagamento_service.dto;

import lombok.Data;

@Data
public class PedidoCriadoEvent {
    private Long id;
    private Long userId;
    private  Long produtoId;
    private int quantidade;
}

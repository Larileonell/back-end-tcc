package com.tcc.notification_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoCriadoEvent {
    private Long id;
    private Long userId;
    private Long produtoId;
    private Integer quantidade;
    private Double Valortotal;
    private LocalDateTime dataCriacao = LocalDateTime.now();
}

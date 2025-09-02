package com.tcc.notification_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagamentoProcessadoEvent {
    private Long pedidoId;
    private String status;
    private Double valorTotal;
    private LocalDateTime dataProcessamento = LocalDateTime.now();
}


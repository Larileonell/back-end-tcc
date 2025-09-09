package com.tcc.pagamento_service.event;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagamentoProcessadoEvent {
    private Long pedidoId;
    private String status;
    private BigDecimal valor;
    private LocalDateTime dataProcessamento;
}
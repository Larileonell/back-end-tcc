package com.tcc.pagamento_service.event;

import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagamentoProcessadoEvent {
    private Long pedidoId;
    private String status;
    private Double valor;
    private LocalDateTime dataProcessamento;
}
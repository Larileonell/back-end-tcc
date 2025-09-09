package com.tcc.pagamento_service.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoCriadoEvent {
    private Long id;
    private Long userId;
    private Long produtoId;
    private Integer quantidade;
    private BigDecimal valorTotal;
    private LocalDateTime dataCriacao;
}
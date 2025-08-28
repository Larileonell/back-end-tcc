package com.tcc.pagamento_service.event;

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
    private Double total;
    private LocalDateTime dataCriacao;
}
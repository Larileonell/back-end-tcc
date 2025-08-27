package com.tcc.pagamento_service.dto;

import lombok.Data;

@Data
public class PagamentoProcessadoEvent {
    private Long pedidoId;
    private Long status;
}

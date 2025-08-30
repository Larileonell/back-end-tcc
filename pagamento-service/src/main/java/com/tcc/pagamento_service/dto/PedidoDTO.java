package com.tcc.pagamento_service.dto;
import lombok.Data;

@Data
public class PedidoDTO {
    private Long id;
    private Long userId;
    private Double valorTotal;
    private String status;
}

package com.tcc.notification_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDTO {
    private Long pedidoId;
    private String tipo;
    private String status;
    private String valorTotal;
    private String mensagem;
    private String canal;
}

package com.tcc.notification_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "notificacoes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notification {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private Long PedidoId;
    private String tipo;
    private String canal;
    private String status;
    private String valorTotal;
    private String mensagem;

    private LocalDateTime dataHora =  LocalDateTime.now();
}

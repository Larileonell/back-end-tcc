package com.tcc.pagamento_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "pagamentos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long pedidoId;
    private String status;
    private Double valor;
    private LocalDateTime dataCriacao = LocalDateTime.now();

    public Pagamento(Long pedidoId, String status, Double valor) {
        this.pedidoId = pedidoId;
        this.status = status;
        this.valor = valor;

    }
}

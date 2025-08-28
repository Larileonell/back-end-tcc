package com.tcc.pagamento_service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
public class Pagamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long pedidoId;
    private String status;
    private Double valor;
    private LocalDateTime dataCriacao;


    public Pagamento() {}


    public Pagamento(Long id, Long pedidoId, String status, Double valor, LocalDateTime dataCriacao) {
        this.id = id;
        this.pedidoId = pedidoId;
        this.status = status;
        this.valor = valor;
        this.dataCriacao = dataCriacao;
    }


    public Pagamento(Long pedidoId, String status, Double valor) {
        this.pedidoId = pedidoId;
        this.status = status;
        this.valor = valor;
        this.dataCriacao = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(Long pedidoId) {
        this.pedidoId = pedidoId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
}

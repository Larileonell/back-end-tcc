package com.tcc.pagamento_service.dto;

import java.math.BigDecimal;

public class PagamentoRequest {

    private Long pedidoId;
    private BigDecimal valorPago;
    private String formaPagamento;

    public PagamentoRequest() {}

    public PagamentoRequest(Long pedidoId, BigDecimal valorPago, String formaPagamento) {
        this.pedidoId = pedidoId;
        this.valorPago = valorPago;
        this.formaPagamento = formaPagamento;
    }

    public Long getPedidoId() { return pedidoId; }
    public void setPedidoId(Long pedidoId) { this.pedidoId = pedidoId; }

    public BigDecimal getValorPago() { return valorPago; }
    public void setValorPago(BigDecimal valorPago) { this.valorPago = valorPago; }

    public String getFormaPagamento() { return formaPagamento; }
    public void setFormaPagamento(String formaPagamento) { this.formaPagamento = formaPagamento; }
}

package com.tcc.pagamento_service.service;


import com.tcc.pagamento_service.dto.PagamentoRequest;
import com.tcc.pagamento_service.event.PagamentoProcessadoEvent;
import com.tcc.pagamento_service.model.Pagamento;
import com.tcc.pagamento_service.repository.PagamentoRepository;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class PagamentoProcessor {
    private final PagamentoRepository repository;
    private final Counter pagamentosAprovados;
    private final Counter pagamentosRecusados;

    public PagamentoProcessor(PagamentoRepository repository, MeterRegistry registry) {
        this.repository = repository;
        this.pagamentosAprovados = registry.counter("pagamentos_aprovados_total");
        this.pagamentosRecusados = registry.counter("pagamentos_recusados_total");
    }

    public Pagamento criarPagamento(PagamentoRequest request, BigDecimal valorTotal) {
        Pagamento pagamento = new Pagamento();
        pagamento.setPedidoId(request.getPedidoId());
        pagamento.setFormaPagamento(request.getFormaPagamento());
        pagamento.setValorPago(request.getValorPago());
        pagamento.setStatus("PENDENTE");
        pagamento.setDataCriacao(LocalDateTime.now());
        return repository.save(pagamento);
    }

    public PagamentoProcessadoEvent processar(Pagamento pagamento, BigDecimal valorPedido) {
        String status;

        if (pagamento.getValorPago().compareTo(valorPedido) != 0) {
            status = "RECUSADO";
        } else if ("CARTAO".equalsIgnoreCase(pagamento.getFormaPagamento())
                && pagamento.getValorPago().compareTo(new BigDecimal("500")) > 0) {
            status = "RECUSADO";
        } else {
            status = "APROVADO";
        }

        pagamento.setStatus(status);
        pagamento.setDataCriacao(LocalDateTime.now());
        repository.save(pagamento);

        if ("APROVADO".equals(status)) pagamentosAprovados.increment();
        else pagamentosRecusados.increment();

        return new PagamentoProcessadoEvent(
                pagamento.getPedidoId(),
                status,
                pagamento.getValorPago(),
                pagamento.getDataCriacao()
        );
    }
}


package com.tcc.pagamento_service.service;


import com.tcc.pagamento_service.event.PagamentoProcessadoEvent;
import com.tcc.pagamento_service.event.PedidoCriadoEvent;
import com.tcc.pagamento_service.model.Pagamento;
import com.tcc.pagamento_service.repository.PagamentoRepository;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class PagamentoProcessor {

    private final PagamentoRepository repository;
    private final Random random = new Random();
    private final MeterRegistry registry;

    public PagamentoProcessor(PagamentoRepository repository, MeterRegistry registry) {
        this.repository = repository;
        this.registry = registry;
    }


    public Pagamento criarPagamento(PedidoCriadoEvent pedido) {
        Pagamento pagamento = new Pagamento();
        pagamento.setPedidoId(pedido.getId());
        pagamento.setStatus("PENDENTE");
        pagamento.setValor(pedido.getValorTotal());
        pagamento.setDataCriacao(LocalDateTime.now());

        Pagamento saved = repository.save(pagamento);


        registry.counter("pagamentos.criados").increment();

        return saved;
    }


    public PagamentoProcessadoEvent processar(Pagamento pagamento) {
        if ("PENDENTE".equals(pagamento.getStatus())) {
            String status = random.nextDouble() < 0.8 ? "APROVADO" : "RECUSADO";

            pagamento.setStatus(status);
            pagamento.setDataCriacao(LocalDateTime.now());
            repository.save(pagamento);


            registry.counter("pagamentos.processados.total", "status", status).increment();

            return new PagamentoProcessadoEvent(
                    pagamento.getPedidoId(),
                    status,
                    pagamento.getValor(),
                    pagamento.getDataCriacao()
            );
        }


        return new PagamentoProcessadoEvent(
                pagamento.getPedidoId(),
                pagamento.getStatus(),
                pagamento.getValor(),
                pagamento.getDataCriacao()
        );
    }
}

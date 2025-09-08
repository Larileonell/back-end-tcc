package com.tcc.pagamento_service.service;


import com.tcc.pagamento_service.dto.PagamentoRequest;
import com.tcc.pagamento_service.event.PagamentoProcessadoEvent;
import com.tcc.pagamento_service.event.PedidoCriadoEvent;
import com.tcc.pagamento_service.model.Pagamento;
import com.tcc.pagamento_service.repository.PagamentoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
public class PagamentoProcessor {
    private final PagamentoRepository repository;
    private final Random random = new Random();

    public PagamentoProcessor(PagamentoRepository repository) {
        this.repository = repository;
    }

    public Pagamento criarPagamento(PedidoCriadoEvent pedido) {
        Pagamento pagamento = new Pagamento();
        pagamento.setPedidoId(pedido.getId());
        pagamento.setStatus("PENDENTE");
        pagamento.setValor(pedido.getValorTotal());
        pagamento.setDataCriacao(LocalDateTime.now());
        return repository.save(pagamento);
    }

    public PagamentoProcessadoEvent processar(Pagamento pagamento) {
        if ("PENDENTE".equals(pagamento.getStatus())) {
            String status = random.nextDouble() < 0.8 ? "APROVADO" : "RECUSADO";

            pagamento.setStatus(status);
            pagamento.setDataCriacao(LocalDateTime.now());
            repository.save(pagamento);

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
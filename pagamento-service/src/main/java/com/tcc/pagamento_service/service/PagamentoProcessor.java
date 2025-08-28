package com.tcc.pagamento_service.service;

import com.tcc.pagamento_service.dto.PagamentoProcessadoEvent;
import com.tcc.pagamento_service.dto.PedidoCriadoEvent;
import com.tcc.pagamento_service.model.Pagamento;
import com.tcc.pagamento_service.repository.PagamentoRepository;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class PagamentoProcessor {
    private final PagamentoRepository repository;
    private final Random random = new Random();

    public PagamentoProcessor(PagamentoRepository repository) {
        this.repository = repository;
    }

    public PagamentoProcessadoEvent processar(PedidoCriadoEvent pedido) {
        String status = random.nextDouble() < 0.8 ? "APROVADO" : "RECUSADO";


        Pagamento pagamento = new Pagamento(
                pedido.getId(),   // pedidoId
                status,
                pedido.getTotal() // valor vindo do pedido
        );

        repository.save(pagamento);


        PagamentoProcessadoEvent event = new PagamentoProcessadoEvent();
        event.setPedidoId(pedido.getId());
        event.setStatus(status);

        return event;
    }
}
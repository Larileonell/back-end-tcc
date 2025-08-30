package com.tcc.pagamento_service.service;


import com.tcc.pagamento_service.event.PagamentoProcessadoEvent;
import com.tcc.pagamento_service.event.PedidoCriadoEvent;
import com.tcc.pagamento_service.model.Pagamento;
import com.tcc.pagamento_service.repository.PagamentoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
                pedido.getId(),     // pedidoId
                status,
                pedido.getValorTotal()   // valor
        );
        repository.save(pagamento);


        return new PagamentoProcessadoEvent(
                pedido.getId(),
                status,
                pedido.getValorTotal(),
                LocalDateTime.now()
        );
    }
}
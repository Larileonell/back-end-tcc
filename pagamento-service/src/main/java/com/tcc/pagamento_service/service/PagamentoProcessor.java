package com.tcc.pagamento_service.service;

import com.tcc.pagamento_service.dto.PagamentoProcessadoEvent;
import com.tcc.pagamento_service.dto.PedidoCriadoEvent;
import com.tcc.pagamento_service.model.Pagamento;
import com.tcc.pagamento_service.repository.PagamentoRepository;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class PagamentoProcessor {
    private final PagamentoRepository pagamentoRepository;

    public PagamentoProcessor(PagamentoRepository pagamentoRepository) {
        this.pagamentoRepository = pagamentoRepository;

    }
    public PagamentoProcessadoEvent processar(PedidoCriadoEvent pedido) {
      String status = new Random().nextBoolean() ? "APROVADO" : "REPROVADO";
        Pagamento pagamento = new Pagamento(null, pedido.getId(), status);
        pagamentoRepository.save(pagamento);

        PagamentoProcessadoEvent event = new PagamentoProcessadoEvent();
        event.setPedidoId(pedido.getId());
        event.setStatus(Long.valueOf(status));
        return event;
    }
}

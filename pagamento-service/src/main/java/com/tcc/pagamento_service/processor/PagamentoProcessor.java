package com.tcc.pagamento_service.processor;

import com.tcc.pagamento_service.dto.PedidoCriadoEvent;
import com.tcc.pagamento_service.event.PagamentoProcessadoEvent;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PagamentoProcessor {


        public PagamentoProcessadoEvent processar(PedidoCriadoEvent pedido) {
            String status = "APROVADO"; // simulação

            return new PagamentoProcessadoEvent(
                    pedido.getId(),
                    status,
                    pedido.getTotal(),
                    LocalDateTime.now()
            );
        }
}


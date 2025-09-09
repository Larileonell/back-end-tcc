package com.tcc.pagamento_service.consumer;


import com.tcc.pagamento_service.dto.PagamentoRequest;
import com.tcc.pagamento_service.event.PagamentoProcessadoEvent;
import com.tcc.pagamento_service.event.PedidoCriadoEvent;
import com.tcc.pagamento_service.model.Pagamento;
import com.tcc.pagamento_service.producer.PagamentoProducerKafka;
import com.tcc.pagamento_service.service.PagamentoProcessor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class PedidoConsumerKafka {

    private final PagamentoProcessor processor;
    private final PagamentoProducerKafka producer;

    public PedidoConsumerKafka(PagamentoProcessor processor, PagamentoProducerKafka producer) {
        this.processor = processor;
        this.producer = producer;
    }

    @KafkaListener(
            topics = "${app.kafka.topic.pedido-criado}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void consume(PedidoCriadoEvent pedido) {
        PagamentoRequest request = new PagamentoRequest(
                pedido.getId(),
                pedido.getValorTotal(),
                "PIX"
        );

        Pagamento pagamento = processor.criarPagamento(request, pedido.getValorTotal());
        PagamentoProcessadoEvent evento = processor.processar(pagamento, pedido.getValorTotal());

        producer.enviar(evento);
        System.out.println("📤 Pagamento processado enviado para Kafka: " + evento);
    }
}
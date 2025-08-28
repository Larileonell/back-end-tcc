package com.tcc.pagamento_service.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tcc.pagamento_service.dto.PagamentoProcessadoEvent;
import com.tcc.pagamento_service.dto.PedidoCriadoEvent;
import com.tcc.pagamento_service.producer.PagamentoProducerKafka;
import com.tcc.pagamento_service.service.PagamentoProcessor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

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
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "pedidoKafkaListenerContainerFactory" // 🔥 linkado com a config
    )
    public void consume(PedidoCriadoEvent pedido) {
        producer.enviar(processor.processar(pedido));
    }
}

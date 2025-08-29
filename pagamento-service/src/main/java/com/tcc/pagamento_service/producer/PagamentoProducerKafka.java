package com.tcc.pagamento_service.producer;



import com.tcc.pagamento_service.event.PagamentoProcessadoEvent;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class PagamentoProducerKafka {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${app.kafka.topic.pagamento-processado}")
    private String pagamentoTopic;

    public PagamentoProducerKafka(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void enviar(PagamentoProcessadoEvent event) {
        kafkaTemplate.send(pagamentoTopic, event);
    }
}

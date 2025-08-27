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
    private final ObjectMapper mapper = new ObjectMapper();
    private final PagamentoProcessor processor;
    private final PagamentoProducerKafka producer;

    public PedidoConsumerKafka(PagamentoProcessor processor, PagamentoProducerKafka producer) {
        this.processor = processor;
        this.producer = producer;
    }
    @KafkaListener(topics = "pedido-criado", groupId = "pagamento-group")
    public void consume(String message) throws Exception{
        PedidoCriadoEvent pedido = mapper.readValue(message, PedidoCriadoEvent.class);
        producer.enviar(processor.processar(pedido));
    }
}

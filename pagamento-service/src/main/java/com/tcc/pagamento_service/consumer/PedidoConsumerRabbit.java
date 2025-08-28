package com.tcc.pagamento_service.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tcc.pagamento_service.dto.PagamentoProcessadoEvent;
import com.tcc.pagamento_service.dto.PedidoCriadoEvent;
import com.tcc.pagamento_service.producer.PagamentoProducerRabbit;
import com.tcc.pagamento_service.service.PagamentoProcessor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class PedidoConsumerRabbit {
    private final PagamentoProcessor processor;
    private final PagamentoProducerRabbit producer;

    public PedidoConsumerRabbit(PagamentoProcessor processor, PagamentoProducerRabbit producer) {
        this.processor = processor;
        this.producer = producer;
    }

    @RabbitListener(queues = "${app.rabbitmq.queue.pedido-criado}")
    public void consume(PedidoCriadoEvent pedido) {
        // processa o evento e publica o pagamento processado
        var pagamento = processor.processar(pedido);
        producer.enviar(pagamento);
    }
}

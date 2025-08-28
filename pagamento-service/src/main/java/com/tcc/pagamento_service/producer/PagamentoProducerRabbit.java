package com.tcc.pagamento_service.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tcc.pagamento_service.dto.PagamentoProcessadoEvent;
import com.tcc.pagamento_service.service.PagamentoProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PagamentoProducerRabbit {
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();

    @Value("${app.rabbitmq.exchange.pagamento}")
    private String exchange;

    @Value("${app.rabbitmq.routing-key.pagamento}")
    private String routingKey;

    public PagamentoProducerRabbit(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void enviar(PagamentoProcessadoEvent event) {
        try {
            String payload = mapper.writeValueAsString(event);
            rabbitTemplate.convertAndSend(exchange, routingKey, payload);
            System.out.println("📤 Pagamento enviado (Rabbit): " + payload);
        } catch (Exception e) {
            System.err.println("❌ Erro ao enviar pagamento: " + e.getMessage());
        }
    }
}

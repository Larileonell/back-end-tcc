package com.tcc.pagamento_service.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tcc.pagamento_service.dto.PagamentoProcessadoEvent;
import com.tcc.pagamento_service.service.PagamentoProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class PagamentoProducerRabbit {
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper mapper = new ObjectMapper();

    public PagamentoProducerRabbit(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }
    public void enviar(PagamentoProcessadoEvent event) throws JsonProcessingException {
        rabbitTemplate.convertAndSend("pagamento.exchange", "pagamento.processado"
                ,mapper.writeValueAsString(event));
    }
}

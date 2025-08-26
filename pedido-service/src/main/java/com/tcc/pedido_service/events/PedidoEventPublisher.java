package com.tcc.pedido_service.events;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tcc.pedido_service.model.Pedido;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PedidoEventPublisher {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();

    @Value("${app.kafka.topic.pedido-criado}")
    private String pedidoTopic;

    @Value("${app.rabbitmq.exchange}")
    private String exchange;

    @Value("${app.rabbitmq.routing-key}")
    private String routingKey;

    public void publishPedidoCriado(Pedido pedido) {
        try {
            var ev = new PedidoCriadoEvent(
                    (long) pedido.getId(),
                    pedido.getUserId(),
                    pedido.getProdutoId(),
                    pedido.getQuantidade(),
                    pedido.getValorTotal(),
                    pedido.getDataCriacao()
            );
            String payload = mapper.writeValueAsString(ev);

            if (kafkaTemplate != null) {
                kafkaTemplate.send(pedidoTopic, String.valueOf(pedido.getId()), payload);
            }
            if (rabbitTemplate != null) {
                rabbitTemplate.convertAndSend(exchange, routingKey, payload);
            }
        } catch (Exception e) {
            System.err.println("Falha ao publicar evento: " + e.getMessage());
        }
    }
}

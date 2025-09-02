package com.tcc.notification_service.consumer;
import com.tcc.notification_service.dto.PedidoCriadoEvent;
import com.tcc.notification_service.service.NotificationService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "app.consumers.kafka.enabled", havingValue = "true", matchIfMissing = true)
public class PedidoCriadoKafkaConsumer {
    private final NotificationService service;

    public PedidoCriadoKafkaConsumer(NotificationService service) {
        this.service = service;
    }

    @KafkaListener(topics = "${app.kafka.topic.pedido-criado}", groupId = "${spring.kafka.consumer.group-id}")
    public void consumir(PedidoCriadoEvent event) {
        service.notificarPedidoCriado(event, "KAFKA");
    }
}

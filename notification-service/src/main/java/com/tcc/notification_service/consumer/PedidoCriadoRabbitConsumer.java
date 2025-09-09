package com.tcc.notification_service.consumer;

import com.tcc.notification_service.dto.PedidoCriadoEvent;
import com.tcc.notification_service.service.NotificationService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "app.consumers.rabbitmq.enabled", havingValue = "true", matchIfMissing = true)
public class PedidoCriadoRabbitConsumer {
    private final NotificationService service;

    public PedidoCriadoRabbitConsumer(NotificationService service) {
        this.service = service;
    }

    @RabbitListener(queues = "${app.rabbitmq.queue.pedido-criado}")
    public void consumir(PedidoCriadoEvent event) {
        service.notificarPedidoCriado(event, "RABBIT");
    }
}

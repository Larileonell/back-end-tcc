package com.tcc.notification_service.consumer;
import com.tcc.notification_service.dto.PagamentoProcessadoEvent;
import com.tcc.notification_service.model.Notification;
import com.tcc.notification_service.service.NotificationService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "app.consumers.rabbitmq.enabled", havingValue = "true", matchIfMissing = true)
public class PagamentoRabbitConsumer {
    private final NotificationService service;

    public PagamentoRabbitConsumer(NotificationService service) {
        this.service = service;
    }

    @RabbitListener(queues = "${app.rabbitmq.queue.pagamento-processado}")
    public void consumir(PagamentoProcessadoEvent event) {
        service.notificarPagamento(event, "RABBIT");
    }
}

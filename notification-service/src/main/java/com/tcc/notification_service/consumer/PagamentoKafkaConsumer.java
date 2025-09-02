package com.tcc.notification_service.consumer;
import com.tcc.notification_service.dto.PagamentoProcessadoEvent;
import com.tcc.notification_service.model.Notification;
import com.tcc.notification_service.service.NotificationService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "app.consumers.kafka.enabled", havingValue = "true", matchIfMissing = true)
public class PagamentoKafkaConsumer {
    private final NotificationService service;

    public PagamentoKafkaConsumer(NotificationService service) {
        this.service = service;
    }

    @KafkaListener(topics = "${app.kafka.topic.pagamento-processado}", groupId = "${spring.kafka.consumer.group-id}")
    public void consumir(PagamentoProcessadoEvent event) {
        service.notificarPagamento(event, "KAFKA");
    }

}

package com.tcc.pedido_service.consumer;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "app.consumers.kafka.enabled", havingValue = "true", matchIfMissing = true)
public class PedidoKafkaConsumer {

    private final Counter pedidosRecebidosCounter;

    public PedidoKafkaConsumer(MeterRegistry registry) {
        this.pedidosRecebidosCounter = Counter.builder("pedidos.recebidos.kafka")
                .description("Pedidos recebidos via Kafka")
                .register(registry);
    }

    @KafkaListener(topics = "${app.kafka.topic.pedido-criado}", groupId = "pedido-group")
    public void consumir(String payload) {
        pedidosRecebidosCounter.increment();
        System.out.println("📥 Evento recebido Kafka: " + payload);
    }
}

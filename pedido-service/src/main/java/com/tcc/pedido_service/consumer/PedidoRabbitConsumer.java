package com.tcc.pedido_service.consumer;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "app.consumers.rabbitmq.enabled", havingValue = "true", matchIfMissing = true)
public class PedidoRabbitConsumer {

    private final Counter pedidosRecebidosCounter;

    public PedidoRabbitConsumer(MeterRegistry registry) {
        this.pedidosRecebidosCounter = Counter.builder("pedidos.recebidos.rabbitmq")
                .description("Pedidos recebidos via RabbitMQ")
                .register(registry);
    }

    @RabbitListener(queues = "${app.rabbitmq.queue.pedido-criado}")
    public void consumir(String payload) {
        pedidosRecebidosCounter.increment();
        System.out.println("📥 Evento recebido RabbitMQ: " + payload);
    }
}

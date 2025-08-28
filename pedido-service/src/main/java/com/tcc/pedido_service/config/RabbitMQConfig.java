package com.tcc.pedido_service.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class RabbitMQConfig {
    @Value("${app.rabbitmq.exchange.pedido}")
    private String exchangeName;

    @Value("${app.rabbitmq.queue.pedido-criado}")
    private String queueName;

    @Value("${app.rabbitmq.routing-key.pedido}")
    private String routingKey;

    @Bean
    public TopicExchange pedidoExchange() {
        return ExchangeBuilder.topicExchange(exchangeName).durable(true).build();
    }

    @Bean
    public Queue pedidoCriadoQueue() {
        return QueueBuilder.durable(queueName).build();
    }

    @Bean
    public Binding pedidoBinding(Queue pedidoCriadoQueue, TopicExchange pedidoExchange) {
        return BindingBuilder.bind(pedidoCriadoQueue).to(pedidoExchange).with(routingKey);
    }
}

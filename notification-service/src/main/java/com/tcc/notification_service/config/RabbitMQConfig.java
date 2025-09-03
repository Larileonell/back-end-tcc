package com.tcc.notification_service.config;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

@Configuration
@EnableRabbit
@ConditionalOnProperty(name = "app.consumers.rabbitmq.enabled", havingValue = "true", matchIfMissing = true)
public class RabbitMQConfig {

    @Value("${app.rabbitmq.exchange.pedido}")
    private String pedidoExchangeName;

    @Value("${app.rabbitmq.queue.pedido-criado}")
    private String pedidoQueueName;

    @Value("${app.rabbitmq.routing-key.pedido}")
    private String pedidoRoutingKey;

    @Value("${app.rabbitmq.exchange.pagamento}")
    private String pagamentoExchangeName;

    @Value("${app.rabbitmq.queue.pagamento-processado}")
    private String pagamentoQueueName;

    @Value("${app.rabbitmq.routing-key.pagamento}")
    private String pagamentoRoutingKey;

    @Bean
    public TopicExchange pedidoExchange() {
        return ExchangeBuilder.topicExchange(pedidoExchangeName).durable(true).build();
    }

    @Bean
    public Queue pedidoCriadoQueue() {
        return QueueBuilder.durable(pedidoQueueName).build();
    }

    @Bean
    public Binding pedidoBinding(Queue pedidoCriadoQueue, TopicExchange pedidoExchange) {
        return BindingBuilder.bind(pedidoCriadoQueue).to(pedidoExchange).with(pedidoRoutingKey);
    }

    @Bean
    public TopicExchange pagamentoExchange() {
        return ExchangeBuilder.topicExchange(pagamentoExchangeName).durable(true).build();
    }

    @Bean
    public Queue pagamentoProcessadoQueue() {
        return QueueBuilder.durable(pagamentoQueueName).build();
    }

    @Bean
    public Binding pagamentoBinding(Queue pagamentoProcessadoQueue, TopicExchange pagamentoExchange) {
        return BindingBuilder.bind(pagamentoProcessadoQueue).to(pagamentoExchange).with(pagamentoRoutingKey);
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate tpl = new RabbitTemplate(connectionFactory);
        tpl.setMessageConverter(jackson2JsonMessageConverter());
        return tpl;
    }
}

package com.tcc.notification_service.config;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

@Configuration
@EnableRabbit
@ConditionalOnProperty(name = "app.consumers.rabbitmq.enabled", havingValue = "true", matchIfMissing = true)
public class RabbitMQConfig {

    @Bean
    public TopicExchange pedidoExchange() {
        return ExchangeBuilder.topicExchange("${app.rabbitmq.exchange.pedido}").durable(true).build();
    }
    @Bean
    public Queue pedidoCriadoQueue() {
        return QueueBuilder.durable("${app.rabbitmq.queue.pedido-criado}").build();
    }
    @Bean
    public Binding pedidoBinding(Queue pedidoCriadoQueue, TopicExchange pedidoExchange) {
        return BindingBuilder.bind(pedidoCriadoQueue)
                .to(pedidoExchange)
                .with("${app.rabbitmq.routing-key.pedido}");
    }

    @Bean
    public TopicExchange pagamentoExchange() {
        return ExchangeBuilder.topicExchange("${app.rabbitmq.exchange.pagamento}").durable(true).build();
    }
    @Bean
    public Queue pagamentoProcessadoQueue() {
        return QueueBuilder.durable("${app.rabbitmq.queue.pagamento-processado}").build();
    }
    @Bean
    public Binding pagamentoBinding(Queue pagamentoProcessadoQueue, TopicExchange pagamentoExchange) {
        return BindingBuilder.bind(pagamentoProcessadoQueue)
                .to(pagamentoExchange)
                .with("${app.rabbitmq.routing-key.pagamento}");
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

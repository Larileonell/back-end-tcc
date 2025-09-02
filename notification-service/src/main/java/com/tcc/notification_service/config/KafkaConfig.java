package com.tcc.notification_service.config;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
@Configuration
@EnableKafka
@ConditionalOnProperty(name = "app.consumers.kafka.enabled", havingValue = "true", matchIfMissing = true)
public class KafkaConfig {
}

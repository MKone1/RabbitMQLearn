package com.example.rabbitconsumer.config;

import com.example.common.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 消费消息队列配置
 */
@Configuration
public class ConsumerRabbitConfig {
    @Bean
    public RabbitTemplate jsonRabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(new RabbitMQConfig());
        return rabbitTemplate;
    }
}

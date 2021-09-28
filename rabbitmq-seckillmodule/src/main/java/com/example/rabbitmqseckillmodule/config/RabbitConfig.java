package com.example.rabbitmqseckillmodule.config;

import com.example.common.config.RabbitMQConfig;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.HashMap;

@Configuration
public class RabbitConfig {
    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public RabbitTemplate jsonRabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(new RabbitMQConfig());
        return rabbitTemplate;
    }

    @Bean
    public Exchange seckillOrderExchange() {
        return new TopicExchange("seckill.order.exchange", true, false);
    }

    @Bean
    public Queue seckillOrderQueue() {
        return new Queue("seckill.order.queue", true, false, false);
    }

    @Bean
    public Binding seckillOrderQueueBinding() {
        return new Binding("seckill.order.queue", Binding.DestinationType.QUEUE,
                "seckill.order.exchange", "seckill.order.#", new HashMap<>());

    }

}

package com.example.rabbitother.config;

import com.example.common.config.RabbitMQConfig;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.HashMap;
import java.util.Map;

/**
 * 模拟仓库模块
 */
@Configuration
public class OtherRabbitConfg {
    /**
     * 使用JSON序列化机制，进行消息转换
     * @return
     */
    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public RabbitTemplate jsonRabbitTemplate(ConnectionFactory connectionFactory) {

        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(new RabbitMQConfig());
        return rabbitTemplate;
    }

    @Bean
    public Exchange stockEventExchange() {
        return new TopicExchange("stock-event-exchange", true, false);
    }

    @Bean
    public Queue stockReleaseStockQueue() {
        return new Queue("stock.release.stock.queue", true, false, false);
    }

    @Bean
    public Queue stockDelayQueue() {
        // String name, boolean durable, boolean exclusive, boolean autoDelete,
        //			@Nullable Map<String, Object> arguments
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-dead-letter-exchange", "stock-event-exchange");
        arguments.put("x-dead-letter-routing-key", "stock.release");
        arguments.put("x-message-ttl", 10000);
        return new Queue("stock.delay.queue", true, false, false, arguments);
    }

    @Bean
    public Binding stockReleaseStockBinding() {
        return new Binding("stock.release.stock.queue", Binding.DestinationType.QUEUE,
                "stock-event-exchange", "stock.release.#", new HashMap<>());
    }

    @Bean
    public Binding orderLockedBinding() {
        return new Binding("stock.delay.queue", Binding.DestinationType.QUEUE,
                "stock-event-exchange", "stock.locked", new HashMap<>());
    }
}

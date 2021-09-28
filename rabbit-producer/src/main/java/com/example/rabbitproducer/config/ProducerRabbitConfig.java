package com.example.rabbitproducer.config;

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
import java.util.Map;

/**
 * 生产服务进行消息配置以及绑定
 */
@Configuration
public class ProducerRabbitConfig {
    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public RabbitTemplate jsonRabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setMessageConverter(new RabbitMQConfig());
        return rabbitTemplate;
    }

    /**
     * 容器中的Binding
     * 创建一个死信队列
     *
     * @return
     */
    @Bean
    public Queue orderDelayQueue() {
        // String name, boolean durable, boolean exclusive, boolean autoDelete,
        //			@Nullable Map<String, Object> arguments
        Map<String, Object> arguments = new HashMap<>();
        //当消息被拒绝或者消息过期，消息重新发送到的交换机（Exchange）的可选名称。
        arguments.put("x-dead-letter-exchange", "order-event-exchange");
        //当消息被拒绝或者消息过期，消息重新发送到的交换机绑定的Route key的名称，如果没有设置则使用之前的Route key。
        arguments.put("x-dead-letter-routing-key", "order.release.order");
        //x-message-ttl：一个消息推送到队列中的存活时间。设置的值之后还没消费就会被删除。
        arguments.put("x-message-ttl", 20000);

        return new Queue("order.delay.queue", true, false, false, arguments);
    }


    @Bean
    public Queue orderReleaseOrderQueue() {
        return new Queue("order.release.order.queue", true, false, false);
    }

    @Bean
    public Exchange orderEventExchange() {
        // String name, boolean durable, boolean autoDelete, Map<String, Object> arguments
        return new TopicExchange("order-event-exchange", true, false);
    }

    @Bean
    public Binding orderCreateOrderBinding() {
        return new Binding("order.delay.queue", Binding.DestinationType.QUEUE,
                "order-event-exchange", "order.create.order", new HashMap<>());
    }

    @Bean
    public Binding orderReleaseOrderBinding() {
        return new Binding("order.release.order.queue", Binding.DestinationType.QUEUE,
                "order-event-exchange", "order.release.order", new HashMap<>());
    }

    @Bean
    public Binding orderReleaseOtherBinding() {
        return new Binding("stock.release.stock.queue", Binding.DestinationType.QUEUE,
                "order-event-exchange", "order.release.other.#", new HashMap<>());
    }

    /**
     * 支付成功给用户加积分
     *
     * @return
     */
    @Bean
    public Queue orderPaySuccessUserQueue() {
        return new Queue("order.pay.success.user.queue", true, false, false);
    }

    @Bean
    public Binding orderPaySuccessUserBinding() {
        return new Binding("order.pay.success.user.queue", Binding.DestinationType.QUEUE,
                "order-event-exchange", "order-finish.#", new HashMap<>());
    }

    /**
     * 支付成功订单拆单
     *
     * @return
     */
    @Bean
    public Queue orderPaySuccessOrderQueue() {
        return new Queue("order.pay.success.order.queue", true, false, false);
    }

    @Bean
    public Binding orderPaySuccessOrderBinding() {
        return new Binding("order.pay.success.order.queue", Binding.DestinationType.QUEUE,
                "order-event-exchange", "order-finish.#", new HashMap<>());
    }

    /**
     * 创建一个秒杀订单队列
     *
     * @return
     */
    @Bean
    public Queue orderSeckillOrderQueue() {
        return new Queue("order.seckill.order.queue", true, false, false);
    }

    /**
     * 对秒杀订单队列进行绑定
     *
     * @return
     */
    @Bean
    public Binding orderSeckillOrderQueueBinding() {
        return new Binding("order.seckill.order.queue",
                Binding.DestinationType.QUEUE,
                "order-event-exchange",
                "order.seckill.order",
                new HashMap<>());
    }
}

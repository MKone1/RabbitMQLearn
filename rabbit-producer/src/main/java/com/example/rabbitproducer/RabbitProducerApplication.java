package com.example.rabbitproducer;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@EnableRabbit
@SpringBootApplication
/**
 * 使用Rabbit MQ
 * 引入AMQP POM Rabbit Auto Configuration自动生效
 * 自动配置了
 * RabbitTemplate,AmqpAdmin,CachingConnectionFactory
 *
 */
public class RabbitProducerApplication {

    public static void main(String[] args) {
        SpringApplication.run(RabbitProducerApplication.class, args);
    }

}

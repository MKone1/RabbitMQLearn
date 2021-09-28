package com.example.rabbitmqseckillmodule;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@EnableRabbit
@SpringBootApplication
public class RabbitmqSeckillmoduleApplication {
    public static void main(String[] args) {
        SpringApplication.run(RabbitmqSeckillmoduleApplication.class, args);
    }

}

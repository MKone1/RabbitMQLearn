package com.example.rabbitother;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@EnableRabbit
@SpringBootApplication
public class RabbitOtherApplication {

    public static void main(String[] args) {
        SpringApplication.run(RabbitOtherApplication.class, args);
    }

}

package com.example.rabbitproducer.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;

import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderEntity implements Serializable {
    private Long OrderId;
    private Long ProductId;
    private Long UserId;
    private String ProductName;
}

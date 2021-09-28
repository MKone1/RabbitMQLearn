package com.example.rabbitother.service;

import org.springframework.stereotype.Service;


public interface OtherRabbitService {
    String cancelOrder(Long orderId);

    String payOrder(Long orderId);
}

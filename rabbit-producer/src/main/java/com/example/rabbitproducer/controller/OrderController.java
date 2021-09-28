package com.example.rabbitproducer.controller;

import com.example.rabbitproducer.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 模拟Rabbit在订单系统中的作用
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    @RequestMapping("/submit/{pId}")
    /**
     * 订单下单：下单，创建订单，验证令牌，验价，锁库存。。。。。。。
     */
    public String OrderSubmitController(@PathVariable("pId") Long pId) {
        Boolean b = orderService.OrderSubmit(pId);
        String s = b ? "success" : "fail";
        return s;

    }
}

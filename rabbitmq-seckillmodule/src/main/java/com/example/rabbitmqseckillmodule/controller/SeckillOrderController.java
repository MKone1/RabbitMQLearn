package com.example.rabbitmqseckillmodule.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.rabbitmqseckillmodule.service.SeckillOrderService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RequestMapping("/sekill")
@RestController
public class SeckillOrderController {

    @Autowired
    SeckillOrderService seckillOrderService;
    /**
     * 大量的秒杀请求进入系统
     * @param jsonObject
     * @return
     */
    @RequestMapping("/order")
    public String seckillOrder(@RequestBody JSONObject jsonObject){
     String s =    seckillOrderService.sendMQ(jsonObject);

        return s ;
    }
}

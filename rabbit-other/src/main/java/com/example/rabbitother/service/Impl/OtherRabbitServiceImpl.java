package com.example.rabbitother.service.Impl;

import com.alibaba.fastjson.JSONObject;
import com.example.common.entity.OrderEntity;
import com.example.rabbitother.service.OtherRabbitService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class OtherRabbitServiceImpl implements OtherRabbitService {
    @Autowired
    RabbitTemplate rabbitTemplate;
    @Override
    public String cancelOrder(Long orderId) {

        //获取消息队列中的订单信息
//        OrderEntity orderEntity = rabbitTemplate.receiveAndConvert("order.delay.queue", new ParameterizedTypeReference<OrderEntity>() {
//        });
        Object o = rabbitTemplate.receiveAndConvert("order.delay.queue");
        //用户主动取消订单
        rabbitTemplate.convertAndSend("order-event-exchange","order.release.order",o);

        System.out.println(o);
        return "fail";
    }

    @Override
    public String payOrder(Long orderId) {
        Object o  =  rabbitTemplate.receiveAndConvert("order.delay.queue");
        int x=(int)(Math.random()*100+1);
        //用户支付服务
        if ((x%2)==0){
            //支付成功
            System.out.println("支付成功" + o);
            rabbitTemplate.convertAndSend("order-event-exchange", "order-finish.#", o);
            Object orderEntity = rabbitTemplate.receiveAndConvert("stock.delay.queue");
            System.out.println("从库存中消费订单" + orderEntity+"可以扣库存了");
            return "success";
        }
        //支付失败
        System.out.println("支付失败" + o);
        rabbitTemplate.convertAndSend("order-event-exchange","order.release.order",o);
        return "fail";
    }
}

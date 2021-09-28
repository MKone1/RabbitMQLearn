package com.example.rabbitconsumer.service;

import com.alibaba.fastjson.JSONObject;
import com.example.common.entity.OrderEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestSendMsgService{
    private final Logger logger = LoggerFactory.getLogger(TestSendMsgService.class);
    @Autowired
    RabbitTemplate rabbitTemplate;


    public String send() {
        OrderEntity order = new OrderEntity(1L,1L,1L,"MAC Air M1");
        rabbitTemplate.convertAndSend("stock-event-exchange","stock.locked",order);
        return "success";
    }

    public void getMsg (){
        try {
            Object o = rabbitTemplate.receiveAndConvert("seckill.order.queue");
            logger.info("秒杀活动消息"+o);
        }catch (Exception e){
            e.printStackTrace();
        }


    }


}

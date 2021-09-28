package com.example.rabbitmqseckillmodule.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.example.rabbitmqseckillmodule.service.SeckillOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SeckillOrderServiceImpl implements SeckillOrderService {
    private Logger logger = LoggerFactory.getLogger(SeckillOrderServiceImpl.class);
    @Autowired
    RabbitTemplate rabbitTemplate;

    @Override
    public String sendMQ(JSONObject jsonObject) {
        rabbitTemplate.convertAndSend("seckill.order.exchange", "seckill.order.#", jsonObject);
//        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
//            /**
//             *
//             * @param correlationData 配置信息
//             * @param ack   交换机是否成功收到消息 ack 为true nack 为 false
//             * @param cause 错误信息  ack 为 null
//             */
//            @Override
//            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
//                if (ack) {
//                    logger.info("消息已经成功发送");
//                } else {
//                    logger.info("消息发送失败");
//                    logger.info("错误原因" + cause);
//                }
//            }
//        });
//        //回退模式
//        rabbitTemplate.setReturnsCallback(new RabbitTemplate.ReturnsCallback() {
//            @Override
//            public void returnedMessage(ReturnedMessage returned) {
//                logger.info("执行了回退模式");
//
//            }
//        });
        return "success";
    }
}

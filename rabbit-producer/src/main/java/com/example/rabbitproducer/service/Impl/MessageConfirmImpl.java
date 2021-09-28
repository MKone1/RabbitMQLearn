package com.example.rabbitproducer.service.Impl;

import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * 手动确认ACK机制
 */
@Component
public class MessageConfirmImpl implements RabbitTemplate.ConfirmCallback,RabbitTemplate.ReturnsCallback {
    /**
     *
     * @param correlationData 配置信息
     * @param ack   交换机是否成功收到消息 ack 为true nack 为 false
     * @param cause 错误信息  ack 为 null
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if (ack) {

            System.out.println("消息已经成功发送");
        } else {
            System.out.println("消息发送失败");
            System.out.println("错误原因" + cause);
        }
    }
    //回退模式：当消息发送给Exchange后，exchange路由到queue失败，才会执行ReturnCallback
    /**
     * 步骤：
     * 1，开启回退模式：publisher-return=“true"
     * 2,设置ReturnCallBack
     * 3，设置Exchange处理消息的模式  rabbitTemplate.setMandatory(true);
     *  a.消息没有路由到queue，直接丢弃消息
     *  b.消息没有路由到queue，返回到消息发送方的ReturnCallBack
     */
    @Override
    public void returnedMessage(ReturnedMessage returned) {
        System.out.println("执行了回退模式");
    }
}

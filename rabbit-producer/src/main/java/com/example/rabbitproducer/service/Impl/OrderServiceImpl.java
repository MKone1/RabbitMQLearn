package com.example.rabbitproducer.service.Impl;

import com.example.common.entity.WareHouseEntity;
import com.example.common.vo.OrderWareHouseVo;
import com.example.common.entity.OrderEntity;
import com.example.rabbitproducer.service.OrderService;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.impl.AMQBasicProperties;
import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 测试异常：Only one ConfirmCallback is supported by each RabbitTemplate：每个RabbitTemplate对象只支持一个ConfirmCallback手动签收方式的回调
 * 解析：由于spring的Bean默认都是单例的，这个RabbitTemplate也不例外，既然每个RabbitTemplate对象只支持一个回调，那我就在该Bean放入spring容器把该RabbitTemplate
 * 解决方法：
 * 在Rabbit Template定义成为多例模式：
 *
 * @Bean
 * @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE) public RabbitTemplate jsonRabbitTemplate(ConnectionFactory connectionFactory) {}
 */
@Service
public class OrderServiceImpl implements OrderService {
    private Logger logger = LoggerFactory.getLogger(RabbitTemplate.class);
    @Autowired
     RabbitTemplate rabbitTemplate;
    @Autowired
    MessageConfirmImpl messageConfirm;



    /**
     * @param pId
     * @return
     */
    @Override
    public Boolean OrderSubmit(Long pId) {
        if (pId != 3) {
            return false;
        }
        //模拟创建订单成功
        for (int i = 0; i < 10; i++) {
            OrderEntity order = new OrderEntity((long) i, pId, (long) i, "MAC Air M1");
            WareHouseEntity wareHouse = new WareHouseEntity((long) i, "仓库" + i, "成都");
            OrderWareHouseVo orderWareHouseVo = new OrderWareHouseVo(order, wareHouse);
            //发送消息
            rabbitTemplate.convertAndSend("order-event-exchange", "order.create.order", order);
            rabbitTemplate.convertAndSend("stock-event-exchange", "stock.locked", orderWareHouseVo);
        }
        //手动确认ACK机制
        //设置生产者发送消息后的回调方法
        rabbitTemplate.setConfirmCallback(messageConfirm);
        //发送消息时设置强制标志； 仅当提供了returnCallback时才适用。

        //回退模式：当消息发送给Exchange后，exchange路由到queue失败，才会执行ReturnCallback
        /**
         * 步骤：
         * 1，开启回退模式：publisher-return=“true"
         * 2,设置ReturnCallBack
         * 3，设置Exchange处理消息的模式  rabbitTemplate.setMandatory(true);
         *  a.消息没有路由到queue，直接丢弃消息
         *  b.消息没有路由到queue，返回到消息发送方的ReturnCallBack
         */
        rabbitTemplate.setReturnsCallback(messageConfirm);
        return true;
    }


}

package com.example.rabbitother.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.common.entity.OrderEntity;
import com.example.rabbitother.service.OtherRabbitService;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.MessageProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RequestMapping("/other")
@RestController
public class OtherRabbitController {
    public static ExecutorService executorService = Executors.newFixedThreadPool(10);
    private Logger logger = LoggerFactory.getLogger(OtherRabbitController.class);
    private static final String CONSUMER_FAIL_COUNT = "consumer_fail_count";
    @Autowired
    RabbitTemplate rabbitTemplate;
    @Autowired
    OtherRabbitService otherRabbitService;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @RequestMapping("/Cancel/{orderId}")
    public String cancelOrder(@PathVariable("orderId") Long orderId) {
        String result = otherRabbitService.cancelOrder(orderId);
        return null;
    }

    @RequestMapping("/Pay/{orderId}")
    public String payOrder(@PathVariable("orderId") Long orderId) {
        String result = otherRabbitService.payOrder(orderId);
        return null;
    }

    @Component
    public class WorkReceiveListener {
        @RabbitListener(queues = "order.release.order.queue")
        public void receiveMessage(String msg, Channel channel, Message message) throws IOException {
            // 只包含发送的消息
            System.out.println("未支付订单：" + msg);
            OrderEntity order = JSONObject.parseObject(msg, OrderEntity.class);
            //像仓库服务发送订单消息
            rabbitTemplate.convertAndSend("order-event-exchange", "order.release.other.#", order);
            System.out.println("执行释放订单");
            //返还优惠卷
            System.out.println("优惠卷返还");
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            // channel 通道信息
            // message 附加的参数信息
        }

        @RabbitListener(queues = "order.delay.queue")
        public void receiveMessage2(String msg, Channel channel, Message message) throws IOException {
            // 包含所有的信息
            System.out.println("监听到订单信息" + msg);
            //执行支付业务
            OrderEntity order = JSONObject.parseObject(msg, OrderEntity.class);
            try {

                //支付成功
                Object orderEntity = rabbitTemplate.receiveAndConvert("stock.delay.queue");
                rabbitTemplate.convertAndSend("order-event-exchange", "order-finish.#", order);
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
                System.out.println("从库存中消费订单" + orderEntity);
            } catch (Exception e) {
                //ack返回false，并重新回到队列，
//                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
                //拒绝接受该消息
//                  channel.basicReject(message.getMessageProperties().getDeliveryTag(),true);

                /**
                 * 手动应答，再将该消息删除，并且将该消息重新发送到队列尾部，则不会影响正常的消息消费
                 */

                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
                //重新发送消息
                channel.basicPublish(message.getMessageProperties().getReceivedExchange(),
                        message.getMessageProperties().getReceivedRoutingKey(), MessageProperties.PERSISTENT_TEXT_PLAIN,
                        JSON.toJSONBytes(order));
//                if (stringRedisTemplate.hasKey(CONSUMER_FAIL_COUNT)){
//                    String value = stringRedisTemplate.opsForValue().get(CONSUMER_FAIL_COUNT);
//                    Integer integer = Integer.getInteger(value);
//                    if (integer<3){
//                        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
//                        //重新发送消息
//                        channel.basicPublish(message.getMessageProperties().getReceivedExchange(),
//                                message.getMessageProperties().getReceivedRoutingKey(), MessageProperties.PERSISTENT_TEXT_PLAIN,
//                                JSON.toJSONBytes(order));
//                        Long increment = stringRedisTemplate.opsForValue().increment(CONSUMER_FAIL_COUNT);
//                        System.out.println("重发次数"+integer);
//
//                    }else {
//                        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
//                    }
//                }

            }

        }

        @RabbitListener(queues = "order.pay.success.user.queue")
        public void userAddIntegral(String msg, Channel channel, Message message) {
            System.out.println("用户服务添加积分");
        }

        @RabbitListener(queues = "order.pay.success.order.queue")
        public void orderSplit(String msg, Channel channel, Message message) {
            System.out.println("订单进行拆单");
        }


        @RabbitListener(queues = "seckill.order.queue")
        public void seckillGetOrderInfo(String msg, Channel channel, Message message) throws IOException {
            CompletableFuture.runAsync(() -> {
                logger.info("秒杀信息001" + msg + UUID.randomUUID());
                try {
                    Thread.sleep(3000);
                    logger.info("休息3S");
                    logger.info("线程ID"+Thread.currentThread().getId());
                    channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
                    logger.info("秒杀信息" + msg);
                } catch (Exception e) {
                    //抛出异常，回滚消息
                    try {
                        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
                        channel.basicPublish(message.getMessageProperties().getReceivedExchange(),
                                message.getMessageProperties().getReceivedRoutingKey(), MessageProperties.PERSISTENT_TEXT_PLAIN,
                                JSON.toJSONBytes(msg));
                    }catch (Exception exception){
                        e.printStackTrace();
                    }
                }
            },executorService);



        }

    }
}

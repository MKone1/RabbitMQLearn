package com.example.rabbitproducer.service;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.nio.charset.StandardCharsets;

/**
 * 生产消息
 */
public class Producer {
    private final static  String QUEUE_NAME="hello";

//    public static void main(String[] args) {
//        //创建链接工厂
//        ConnectionFactory connectionFactory = new ConnectionFactory();
//        connectionFactory.setHost("192.168.0.100");
//        connectionFactory.setPort(5672);
//        connectionFactory.setUsername("admin");
//        connectionFactory.setPassword("admin");
//        connectionFactory.setVirtualHost("my_vhost");
//        try{
//            //从连接工厂中获取一个链接
//            Connection connection = connectionFactory.newConnection();
//            Channel channel = connection.createChannel();
//            //channel 可以绑定一个exchange 交换机
//            //channel实现了自动Close接口，自动关闭，不需要显示关闭,
//            /**
//             * 参数：
//             * queue – 队列的名称
//             * 持久 - 如果我们声明一个持久队列，则为真（该队列将在服务器重启后继续存在）
//             * 独占 – 如果我们声明独占队列（仅限于此连接），则为 true
//             * autoDelete – 如果我们声明一个自动删除队列，则为 true（服务器将在不再使用时将其删除）
//             * 参数 - 队列的其他属性（构造参数）
//             * 返回：
//             * 用于指示队列已成功声明的声明确认方法
//             * 抛出：
//             * IOException - 如果遇到错误
//             * 也可以看看：
//             * AMQP.Queue.Declare , AMQP.Queue.DeclareOk
//             */
//            channel.queueDeclare(QUEUE_NAME,false,false,false,null);
//            String message = "hello word";
//            /**
//             * 发布消息。 发布到不存在的交换将导致通道级协议异常，从而关闭通道。
//             * 如果资源驱动的警报有效，则Channel#basicPublish调用最终将被阻止。
//             */
//            channel.basicPublish("",QUEUE_NAME,null,message.getBytes(StandardCharsets.UTF_8));
//            System.out.println("消息发送完毕");
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//    }
}

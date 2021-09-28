package com.example.rabbitproducer.service;

import com.rabbitmq.client.*;

/**
 * 消费消息
 * TODO:
 *  debug:  connection error; protocol method: #method<connection.close>(reply-code=530, reply-text=NOT_ALLOWED
 *  查看管理界面的虚拟机的host进行配置
 */
public class Consumer {
    private final static String QUEUE_NAME = "hello";

//    public static void main(String[] args) {
//        //创建链接工厂
//        ConnectionFactory connectionFactory = new ConnectionFactory();
//        connectionFactory.setHost("192.168.0.100");
//        connectionFactory.setPort(5672);
//        connectionFactory.setUsername("admin");
//        connectionFactory.setPassword("admin");
//        connectionFactory.setVirtualHost("my_vhost");
//        try {
//            Connection connection = connectionFactory.newConnection();
//            Channel channel = connection.createChannel();
//            System.out.println("等待接受消息");
//            //消费的回调方法
//            // 消息送达时通知的回调接口。 如果您不需要实现所有应用程序回调，则更喜欢它而不是Consumer的面向 lambda 的语法
//            DeliverCallback deliverCallback = ((consumerTag, message) -> {
//                String msg = new String(message.getBody());
//                System.out.println("消息是" + msg);
//            });
//            //接受消息中断的回调方法
//            // 要通知消费者取消的回调接口。 如果您不需要实现所有应用程序回调，则更喜欢它而不是Consumer的面向 lambda 的语法
//            CancelCallback cancelCallback = (consumerTag -> {
//                System.out.println("接受消息被中断");
//            });
//            /**
//             * 消费消息
//             * 使用服务器生成的 consumerTag 启动非本地、非排他的消费者。
//             * 仅提供对basic.deliver和basic.cancel AMQP 方法的访问（这对于大多数情况来说已经足够了）。
//             * 查看带有Consumer参数的方法以访问所有应用程序回调。
//             *参数：
//             * queue – 队列的名称
//             * autoAck – 如果服务器应该考虑消息一旦发送就确认为真； 如果服务器应该期待明确的确认，则为 false
//             * DeliverCallback – 传递消息时的回调
//             * cancelCallback – 消费者取消时的回调
//             * 返回：
//             * 服务器生成的consumerTag
//             */
//            channel.basicConsume(QUEUE_NAME, true, deliverCallback, cancelCallback);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}

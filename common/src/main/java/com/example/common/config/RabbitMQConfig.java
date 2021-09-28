package com.example.common.config;

import com.alibaba.fastjson.JSON;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.AbstractMessageConverter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Configuration;

public class RabbitMQConfig extends AbstractMessageConverter {
    @Override
    protected Message createMessage(Object object, MessageProperties messageProperties) {
        messageProperties.setContentType("application/json");
        return new Message(JSON.toJSONBytes(object), messageProperties);
    }

    @Override
    public Object fromMessage(Message message) throws MessageConversionException {
        return JSON.parse(message.getBody());
    }
}


package com.example.rabbitmqseckillmodule.service;

import com.alibaba.fastjson.JSONObject;

public interface SeckillOrderService {
    String sendMQ(JSONObject jsonObject);
}

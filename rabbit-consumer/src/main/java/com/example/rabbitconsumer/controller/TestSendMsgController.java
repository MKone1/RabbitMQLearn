package com.example.rabbitconsumer.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.rabbitconsumer.service.TestSendMsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestSendMsgController {
    @Autowired
    TestSendMsgService testSendMsgService;
    @RequestMapping("/sendMsg")
    public String sendMsg(){
       String s =  testSendMsgService.send();
       return s;
    }
    @RequestMapping("/getMsg")
    public String getMsg(){
        testSendMsgService.getMsg();
        return "success";
    }

}

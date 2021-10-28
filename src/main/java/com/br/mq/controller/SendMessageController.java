package com.br.mq.controller;

import com.alibaba.fastjson.JSONObject;
import com.br.mq.config.DelayQueueConfig;
import com.br.mq.utils.RmqSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.UUID;

/**
 * @author jesse.zhang
 * created on 2021-10-26
 */
@RestController
@RequestMapping("/api")
@Slf4j
public class SendMessageController {

    @Autowired
    private RmqSender rmqSender;

    @Autowired
    private RabbitTemplate rabbitTemplate;


    @GetMapping("/send/message")
    public void sendMessage(String message, String expiration) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("message", message);
        String exchange = "delayExchange";
        String routeKey = "delay.queue.route";
        log.info("发送消息 : {}", message);
        rmqSender.sendDelayMessage(exchange, routeKey, JSONObject.toJSONString(hashMap), expiration);
    }

    @GetMapping("/delay/message")
    public void delayMessage(String message, String expiration) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("message", message);
        String exchange = DelayQueueConfig.DELAY_EXCHANGE;
        String routeKey = DelayQueueConfig.DELAY_ROUTING_KEY;
        log.info("发送delay消息 : {}", message);
        rmqSender.sendDelayMessage(exchange, routeKey, JSONObject.toJSONString(hashMap), expiration);
//        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
//        rabbitTemplate.convertAndSend(DelayQueueConfig.DELAY_EXCHANGE,
//                DelayQueueConfig.DELAY_ROUTING_KEY,
//                JSONObject.toJSONString(hashMap), (message1) -> {
//                    // 设置延迟的毫秒数
//                    message1.getMessageProperties().setDelay(Integer.valueOf(expiration));
//                    log.info("Now : {}", ZonedDateTime.now());
//                    return message1;
//                }, correlationData);
    }
}

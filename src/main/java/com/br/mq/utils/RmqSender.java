package com.br.mq.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * @author jesse.zhang
 * created on 2021-10-26
 */
@Component
@Slf4j
public class RmqSender implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnsCallback {

    @Resource
    private RabbitTemplate rabbitTemplate;


    @PostConstruct
    public void init() {
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnsCallback(this);
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if (ack) {
            log.info("消息发送成功:{}", correlationData);
        } else {
            log.error("消息消费失败:{},{}", correlationData, cause);
        }
    }

    @Override
    public void returnedMessage(ReturnedMessage returnedMessage) {
        Message message = returnedMessage.getMessage();
        int replyCode = returnedMessage.getReplyCode();
        String exchange = returnedMessage.getExchange();
        String routingKey = returnedMessage.getRoutingKey();
        String replyText = returnedMessage.getReplyText();
        log.info("接收返回消息:exchange:{},routingKey:{},replyCode:{},replyTest:{},CorrelationId:{}",
                exchange, routingKey, replyCode, replyText,
                message.getMessageProperties().getCorrelationId());
    }

    public void send(String exchange, String routingKey, String message) {
        CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());
        log.info("发送消息 : {}", message);
        rabbitTemplate.convertAndSend(exchange, routingKey, message, correlationId);
        log.info("结束发送消息 : {}", message);
    }

    public void sendAndReceive(String exchange, String routingKey, Object message) {
        CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());
        log.info("发送消息 : {}", message);
        Object response = rabbitTemplate.convertSendAndReceive(exchange, routingKey, message, correlationId);
        log.info("结束发送消息 : {}", message);
        if (null != response) {
            log.info("消息响应 : {}", response);
        }
    }

    public void sendDelayMessage(String exchange, String routingKey, String message, String expiration) {
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setExpiration(expiration);
        String correlationId = UUID.randomUUID().toString();
        messageProperties.setCorrelationId(correlationId);
        Message msg = new Message(message.getBytes(StandardCharsets.UTF_8), messageProperties);
        CorrelationData correlationData = new CorrelationData(correlationId);
        rabbitTemplate.convertAndSend(exchange, routingKey, msg, correlationData);
    }
}

package com.br.mq.receiver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author jesse.zhang
 * created on 2021-10-26
 */
@Component
@Slf4j
public class DelayMessageReceiver {

    @RabbitListener(queues = "${mq.delay.queue}")
    public void processMessage(String msg) {
        log.info("接收消息: {}", msg);
    }

    @RabbitListener(queues = "${mq.xdelay.xqeue}")
    public void delayMessage(String message) {
        log.info("接收消息delayMessage: {}", message);
    }
}

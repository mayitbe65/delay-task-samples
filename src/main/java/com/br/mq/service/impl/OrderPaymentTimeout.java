package com.br.mq.service.impl;

import com.br.mq.service.RedisDelayQueueHandle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author jesse.zhang
 * created on 2021-10-27
 */
@Slf4j
@Service
public class OrderPaymentTimeout implements RedisDelayQueueHandle<Map> {

    @Override
    public void execute(Map map) {
        log.info("(收到订单支付超时延迟消息) {}", map);
    }
}

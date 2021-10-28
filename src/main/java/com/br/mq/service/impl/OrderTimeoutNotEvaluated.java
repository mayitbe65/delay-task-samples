package com.br.mq.service.impl;

import com.br.mq.service.RedisDelayQueueHandle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author jesse.zhang
 * created on 2021-10-27
 */
@Service
@Slf4j
public class OrderTimeoutNotEvaluated implements RedisDelayQueueHandle<Map> {

    @Override
    public void execute(Map map) {
        log.info("(收到订单超时未评价延迟消息) {}", map);
    }
}

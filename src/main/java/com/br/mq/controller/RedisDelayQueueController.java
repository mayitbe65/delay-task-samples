package com.br.mq.controller;

import com.br.mq.common.enums.RedisDelayQueueEnum;
import com.br.mq.utils.RedisDelayQueueUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author jesse.zhang
 * created on 2021-10-27
 */
@RestController
public class RedisDelayQueueController {

    @Autowired
    private RedisDelayQueueUtil redisDelayQueueUtil;


    @GetMapping("/addQueue")
    public void addQueue() {
        Map<String, String> map1 = new HashMap<>();
        map1.put("orderId", "100");
        map1.put("remark", "订单支付超时，自动取消订单");

        Map<String, String> map2 = new HashMap<>();
        map2.put("orderId", "200");
        map2.put("remark", "订单超时未评价，系统默认好评");

        // 添加订单支付超时，自动取消订单延迟队列。为了测试效果，延迟10秒钟
        redisDelayQueueUtil.addDelayQueue(map1, 20, TimeUnit.SECONDS, RedisDelayQueueEnum.ORDER_PAYMENT_TIMEOUT.getCode());

        // 订单超时未评价，系统默认好评。为了测试效果，延迟20秒钟
        redisDelayQueueUtil.addDelayQueue(map2, 10, TimeUnit.SECONDS, RedisDelayQueueEnum.ORDER_TIMEOUT_NOT_EVALUATED.getCode());
    }
}

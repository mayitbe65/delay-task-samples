package com.br.mq.service;

import com.br.mq.common.enums.RedisDelayQueueEnum;
import com.br.mq.utils.RedisDelayQueueUtil;
import com.br.mq.utils.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

/**
 * @author jesse.zhang
 * created on 2021-10-27
 */
@Slf4j
@Service
public class RedisDelayQueueRunner implements CommandLineRunner {

    @Autowired
    private RedisDelayQueueUtil redisDelayQueueUtil;

    @Override
    public void run(String... args) throws Exception {
        new Thread(() -> {
            while (true){
                try {
                    RedisDelayQueueEnum[] queueEnums = RedisDelayQueueEnum.values();
                    for (RedisDelayQueueEnum queueEnum : queueEnums) {
                        Object value = redisDelayQueueUtil.getDelayQueue(queueEnum.getCode());
                        if (value != null) {
                            RedisDelayQueueHandle redisDelayQueueHandle = (RedisDelayQueueHandle) SpringUtil.getBean(queueEnum.getBeanId());
                            redisDelayQueueHandle.execute(value);
                        }
                    }
                } catch (InterruptedException e) {
                    log.error("(Redis延迟队列异常中断) {}", e.getMessage());
                }
            }
        }).start();
        log.info("(Redis延迟队列启动成功)");
    }
}

package com.br.mq.service;

/**
 * @author jesse.zhang
 * created on 2021-10-27
 */
public interface RedisDelayQueueHandle<T> {

    void execute(T t);
}

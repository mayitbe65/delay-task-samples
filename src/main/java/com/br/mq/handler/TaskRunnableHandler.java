package com.br.mq.handler;

import lombok.extern.slf4j.Slf4j;

/**
 * @author jesse.zhang
 * created on 2021-11-02
 */
@Slf4j
public class TaskRunnableHandler implements Runnable {

    private String name;

    public TaskRunnableHandler(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        log.info("任务开始执行: {}, 时间: {}", name, System.currentTimeMillis());
    }
}

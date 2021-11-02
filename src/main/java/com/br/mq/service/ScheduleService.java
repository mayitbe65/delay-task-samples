package com.br.mq.service;

import com.br.mq.handler.TaskRunnableHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

/**
 * @author jesse.zhang
 * created on 2021-11-02
 */
@Service
@Slf4j
public class ScheduleService {

    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;

    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
        return new ThreadPoolTaskScheduler();
    }

    private Map<String, ScheduledFuture<?>> taskMap = new HashMap<>();

    public void addTask(String taskName, String cron) {
        if (null != taskMap.get(taskName)) {
            log.info("任务已经在执行: {}", taskName);
            return;
        }
        ScheduledFuture<?> schedule = threadPoolTaskScheduler.schedule(new TaskRunnableHandler(taskName), new CronTrigger(cron));
        taskMap.put(taskName, schedule);
    }

    public void stopTask(String taskName) {
        if (null == taskMap.get(taskName)) {
            log.info("未找到对应任务: {}", taskName);
            return;
        }
        ScheduledFuture<?> scheduledFuture = taskMap.get(taskName);
        scheduledFuture.cancel(true);
        taskMap.remove(taskName);
    }
}

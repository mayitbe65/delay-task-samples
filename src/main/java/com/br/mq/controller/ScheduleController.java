package com.br.mq.controller;

import com.br.mq.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jesse.zhang
 * created on 2021-11-02
 */
@RestController
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @GetMapping("/task/add")
    public void addTask(String taskName, String cron) {
        scheduleService.addTask(taskName, cron);
    }

    @GetMapping("/task/stop")
    public void stopTask(String taskName) {
        scheduleService.stopTask(taskName);
    }
}

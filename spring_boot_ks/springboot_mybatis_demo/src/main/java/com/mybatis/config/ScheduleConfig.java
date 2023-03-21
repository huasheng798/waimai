package com.mybatis.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.stereotype.Controller;

import java.util.concurrent.Executors;

/**
 * @Author:luosheng
 * @Date:2023/3/9 15:33
 * @Description:
 */
@Configuration
public class ScheduleConfig implements SchedulingConfigurer {
    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        //所有的定时任务给他分配三个线程
        taskRegistrar.setScheduler(Executors.newScheduledThreadPool(3));
    }
}

package com.angular.admin.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.IntervalTask;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.Date;

/**
 * spring SchedulingConfigurer 配置
 * 设置线程池大小
 * <p>
 * Created by licy13 on 2016/12/14.
 */
@Configuration
public class SchedulingConfigurerConfiguration implements SchedulingConfigurer {


    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setTaskScheduler(poolScheduler());
        taskRegistrar.addFixedRateTask(new IntervalTask(new Runnable() {
            @Override
            public void run() {
//                System.out.println("Job @ fixed rate " + new Date() + ", Thread name is " + Thread.currentThread().getName());
            }
        }, 1000, 0));
    }


    @Bean
    public TaskScheduler poolScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setThreadNamePrefix("poolScheduler-");
        scheduler.setPoolSize(1);
        return scheduler;
    }

}
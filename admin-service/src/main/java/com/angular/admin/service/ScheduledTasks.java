package com.angular.admin.service;

import com.angular.admin.service.impl.SentryRedisConnImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * spring  job 配置
 * Created by licy13 on 2016/12/14.
 */
@Service
public class ScheduledTasks {
    private static final Logger LOGGER = LogManager.getLogger(SentryRedisConnImpl.class.getName());


    private AtomicInteger counter = new AtomicInteger(0);

    @Scheduled(fixedRate = 2000)
    public void fixedRateJob() {
        int jobId = counter.incrementAndGet();
        System.out.println("Job @ fixed rate " + new Date() + ", jobId: " + jobId);
    }


}
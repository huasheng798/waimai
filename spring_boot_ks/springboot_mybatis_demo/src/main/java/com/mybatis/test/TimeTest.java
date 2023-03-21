package com.mybatis.test;


import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @Author:luosheng
 * @Date:2023/3/9 14:57
 * @Description:
 */
//@Component
@Slf4j
public class TimeTest {

    @Scheduled(cron = "0/5 * * * * ?" )
    public void execute() throws InterruptedException {
      //  System.out.println("当前定时任务"+new Date());
        long id = Thread.currentThread().getId();
        Thread.sleep (5000);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        log.info("2.当前定时任务{},id:{}",sdf.format(new Date()),id);

    }

    @Scheduled(cron = "0/5 * * * * ?" )
    public void a(){

        long id = Thread.currentThread().getId();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        log.info("1.当前定时任务{},id:{}",sdf.format(new Date()),id);


    }

}

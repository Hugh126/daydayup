package com.example.myspring;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * 验证重复执行场景
 *
 * [结论：] 默认会从线程池拿线程重复执行，除非线程耗尽
 * [解决：] @DisallowConcurrentExecution
 */
//@DisallowConcurrentExecution
public class XJob extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        System.out.println("[" + Thread.currentThread().getName() + "--Start--] " + LocalDateTime.now().toString());
        try {
            TimeUnit.SECONDS.sleep(15L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("[" + Thread.currentThread().getName() + "--End--] " + LocalDateTime.now().toString());
    }

}

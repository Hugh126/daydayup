package multiThread.delaytask;

import cn.hutool.core.thread.NamedThreadFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.text.DateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.*;

/**
 * 实现延时任务
 *
 * 1、线程循环
 * 2、定时任务 spring task
 * 3、定时任务 Quartz
 * 4、ScheduledExecutorService
 * 5、DelayQueue
 * 6、
 */


@Slf4j
public class DelayTaskTestTest {

    @Test
    public void test1() throws InterruptedException {
        log.warn("start");
        ScheduledExecutorService executor =  new ScheduledThreadPoolExecutor(1, new NamedThreadFactory("scheduled-test-", false));
        executor.scheduleWithFixedDelay(() -> {
            log.warn("reached");
        }, 10, 5, TimeUnit.SECONDS);

        Callable<Integer> call = () -> {
            log.warn("reached target2");
            return 2;
        };
        ScheduledFuture<Integer> result = executor.schedule( call, 5, TimeUnit.SECONDS);
        try {
            log.warn("schedule call = " + result.get());
        } catch (Exception e) {
            e.printStackTrace();
        }

        TimeUnit.SECONDS.sleep(30L);
    }

    @Test
    public void test2() throws InterruptedException {
        DelayQueue<DelayElement> delayQueue = new DelayQueue();
        // 添加延迟任务
        delayQueue.put(new DelayElement("TASK1",1));
        delayQueue.put(new DelayElement("TASK2",3));
        delayQueue.put(new DelayElement("TASK3",5));
        log.warn("Start");
        while (!delayQueue.isEmpty()){
            // 执行延迟任务
            DelayElement take = delayQueue.take();
           take.run();
        }
        log.warn("End");
    }





}
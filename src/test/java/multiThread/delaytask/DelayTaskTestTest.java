package multiThread.delaytask;

import cn.hutool.core.thread.NamedThreadFactory;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.junit.Test;

import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

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

    ScheduledExecutorService executor =  new ScheduledThreadPoolExecutor(3, new NamedThreadFactory("scheduled-test-", false));

    @Test
    public void test1() throws InterruptedException {

        AtomicInteger ato = new AtomicInteger();

        Map<Integer, Future> taskMap = new ConcurrentHashMap<>();

        Task t = new Task(taskMap, ato);
        log.warn("===========延迟任务方式一  按频次执行");
        ScheduledFuture<?> future = executor.scheduleWithFixedDelay(t, 5, 2, TimeUnit.SECONDS);

        log.warn("===========延迟任务方式二  只执行一次");
        Callable<Integer> call = () -> {
            return 2;
        };
        ScheduledFuture<Integer> result = executor.schedule( call, 3, TimeUnit.SECONDS);
        try {
            log.warn("schedule call = " + result.get());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(executor.isTerminated());
        executor.awaitTermination(30L, TimeUnit.SECONDS);
    }

    @Test
    public void test2() throws InterruptedException {
        log.warn("===========延迟任务方式三");
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

    @Test
    public void scheduleAtFixedRateTest() throws InterruptedException {
        System.out.println("--begin--" + LocalTime.now().toString());
        executor.scheduleWithFixedDelay(() -> {
            System.out.println("hello__ fixed task :: " + LocalTime.now().toString());
        }, 5, 3, TimeUnit.SECONDS);
        executor.awaitTermination(1L, TimeUnit.HOURS);
    }

    static class Task implements Runnable {
        private Map<Integer, Future> map ;
        private AtomicInteger ato;

        public Task(Map map, AtomicInteger ato) {
            this.map = map;
            this.ato = ato;
            map.put(ato.get(), map);
        }

        @Override
        public void run() {
            int num = ato.incrementAndGet();
            log.warn("执行任务" + num);
            if (num == 5) {
                map.get(num).cancel(true);
            }
        }

    }

}
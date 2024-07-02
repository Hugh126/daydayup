package com.example.myspring.util;

import org.junit.Test;

import java.time.LocalDateTime;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static org.junit.Assert.*;

public class PausableThreadPoolExecutorTest {

    @Test
    public void pause() throws InterruptedException {
        PausableThreadPoolExecutor executorService = new PausableThreadPoolExecutor(8, 10, 0, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(10));
        IntStream.range(1,10).forEach(x -> {
            executorService.execute(() -> {

                synchronized (this) {
                    if (x==3) {
                        executorService.pause();
                        try {
                            TimeUnit.SECONDS.sleep(60L);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        executorService.resume();
                    }

                    System.out.println("number=" + x + " time=" + LocalDateTime.now().toString());
                    try {
                        TimeUnit.SECONDS.sleep(5L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }


            });
        });
        while (executorService.getCompletedTaskCount() < executorService.getTaskCount()) {
            System.out.println("=== pool running===");
            TimeUnit.SECONDS.sleep(10L);
        }
    }
}
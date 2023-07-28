package java8.concurrent;

import org.junit.Test;

import java.util.Random;
import java.util.concurrent.*;
import java.util.stream.IntStream;

/**
 * 循环屏障
 * 1. CountDownLatch 是计数减到0触发
 * 2. CyclicBarrier 是需要线程数达到计数条件
 *
 *
 * CyclicBarrier还是基于AQS实现的，内部维护parties记录总线程数，count⽤于计数，最开始
 * count=parties，调⽤await()之后count原⼦递减，当count为0之后，再次将parties赋值给count，这就
 * 是复⽤的原理。
 * 1. 当⼦线程调⽤await()⽅法时，获取独占锁，同时对count递减，进⼊阻塞队列，然后释放锁
 * 2. 当第⼀个线程被阻塞同时释放锁之后，其他⼦线程竞争获取锁，操作同1
 * 3. 直到最后count为0，执⾏CyclicBarrier构造函数中的任务，执⾏完毕之后⼦线程继续向下执⾏
 *
 */
public class CyclicBarrierTest {
    private static ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(3);
    CyclicBarrier cyclicBarrier = new CyclicBarrier(3, () -> {
        System.out.println("---all ready---");
    });

    Runnable lashi = () -> {
        String tn = Thread.currentThread().getName();
        System.out.println( tn+ " lashi--ing");
        try {
            TimeUnit.SECONDS.sleep(new Random().nextInt(5));
            cyclicBarrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
        System.out.println(tn + " done");
    };

    @Test
    public void test1() {
        IntStream.range(1,4).forEach(t->executor.submit(lashi));
        while (executor.getCompletedTaskCount() < executor.getTaskCount()){ }
        executor.shutdown();
    }

    /**
     * 可重用
     * @throws InterruptedException
     */
    @Test
    public void test2() {
        IntStream.range(1,4).forEach(t->executor.submit(lashi2));
        while (executor.getCompletedTaskCount() < executor.getTaskCount()){ }
        executor.shutdown();
    }

    Runnable lashi2 = () -> {
        String tn = Thread.currentThread().getName();
        System.out.println( tn+ " lashi--ing");
        try {
            TimeUnit.SECONDS.sleep(new Random().nextInt(5));
            cyclicBarrier.await();
            System.out.println(tn + " working...");
            cyclicBarrier.await();
            System.out.println(tn + " moyu...");
            cyclicBarrier.await();
            System.out.println(tn + " 下班了--");
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }

    };

}

package multiThread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;

@Slf4j
public class BatchRunNoResult {

    public static void main(String[] args) throws InterruptedException {

        int doneNum = 10;

        CountDownLatch start = new CountDownLatch(1);
        CountDownLatch done = new CountDownLatch(doneNum);

        for (int i = 0; i < doneNum; i++) {
            new Thread(() -> {
                try {
                    start.await();
                    log.warn(String.format("Thread %s done.", Thread.currentThread().getName()));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    done.countDown();
                }
            }).start();
        }

        System.out.println("通知所有线程开始执行……");
        start.countDown();

        System.out.println("等待所有线程执行完毕……");
        done.await();

        System.out.println("所有线程已经执行完毕!");
    }
}
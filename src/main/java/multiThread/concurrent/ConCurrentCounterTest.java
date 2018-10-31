package multiThread.concurrent;

import java.util.concurrent.CountDownLatch;

/**
 * 方式1：通过for循环new Thread
 */
public class ConCurrentCounterTest {

    public static void main(String[] args) throws InterruptedException {

        int doneNum = 10;

        CountDownLatch start = new CountDownLatch(1);
        CountDownLatch done = new CountDownLatch(doneNum);

        for(int i= 0; i< doneNum; i++) {
            new Thread(new Worker(start, done)).start();  // create and start threads
        }

        doSomeThing();   // don't let run yet

        System.out.println("通知所有线程开始执行……");
        start.countDown(); // let all threads proceed

        doSomeThing();

        System.out.println("等待所有线程执行完毕……");
        done.await(); // wait for all to finish

        System.out.println("所有线程已经执行完毕!");
    }

    private static void doSomeThing() {

    }

}

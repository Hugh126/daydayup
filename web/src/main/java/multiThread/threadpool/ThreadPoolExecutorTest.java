package multiThread.threadpool;

import org.springframework.context.annotation.Import;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 4种常见线程池
 */

public class ThreadPoolExecutorTest {

    /**
     * 优点：高效；缺点：线程不会被回收
     */
    public void newFixedThreadPool() {
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);
        int taskNum = 20;
        for(int i = 0;i < taskNum; i++) {
            fixedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    doSomeThing(0);
                }
            });
        }
        //
        fixedThreadPool.shutdown();
    }

    public void newCachedThreadPool() throws InterruptedException {
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        for (int i = 0; i < 10; i++) {
            final int index = i;
            cachedThreadPool.execute(new Runnable() {
                public void run() {
                    System.out.println(Thread.currentThread().getName() + " || index = " + index);

                    // 中断线程，启用新的线程执行


                    Thread.interrupted();

                    // 尝试使用wait/notify 来释放线程锁
                    /*
                    int temp = 0;
                    synchronized (this) {
                        while(temp < 5) {
                            doSomeThing(temp);
                            try {
                                System.out.println("1 " + Thread.currentThread().getName() + Thread.holdsLock(this));
                                // 释放锁
                                this.wait();
                                System.out.println("2 " + Thread.currentThread().getName()+ Thread.holdsLock(this));
                            } catch (InterruptedException e) {
                                System.out.println("Interrupted");
                                e.printStackTrace();
                            }
                        }
                        this.notify();
                    }
                    */

                }
            });
        }

        //
        cachedThreadPool.shutdown();
    }

    public void doSomeThing(int i) {
        try {
            i++;
            System.out.println(Thread.currentThread().getName());
            Thread.interrupted();
            Thread.sleep(200);
            System.out.println(Thread.currentThread().getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws InterruptedException {
        ThreadPoolExecutorTest executorTest = new ThreadPoolExecutorTest();
//        executorTest.newCachedThreadPool();

        executorTest.newFixedThreadPool();
    }
}

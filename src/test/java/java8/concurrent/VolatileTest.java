package java8.concurrent;

import java.util.concurrent.TimeUnit;

public class VolatileTest {

    /**
     * 使用volatile，1秒钟后程序会停止
     * 不使用volatile，则不会停止
     *
     * ===> volatile 阻止了指令重排
     *
     * JMM内存模型  Happens-Before原则
     * https://blog.csdn.net/zwx900102/article/details/106306915
     *
     */
    static  boolean flag = true;
//    static volatile boolean flag = true;

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " start");
            while (flag) {

            }
            System.out.println("over");
        }, "t1").start();

        TimeUnit.SECONDS.sleep(1);

        new Thread(() -> {
            flag = false;
        }, "t2").start();
    }


}

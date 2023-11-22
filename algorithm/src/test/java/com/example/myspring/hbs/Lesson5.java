package com.example.myspring.hbs;

import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * 有 3 个独立的线程，一个只会输出 A，一个只会输出 L，一个只会输出 I。
 * 在三个线程同时启动的情况下，请用合理的方式让他们按顺序打印 ALIALI。
 * 三个线程开始正常输出后，主线程若检测到用户任意的输入则停止三个打印线程的工
 * 作，整体退出。
 *
 *
 * ===遇到问题===========
 * 1、输出流太快，控制台输入流根本读取不了。必须在打印线程的时候让Sleep一下
 *
 */
public class Lesson5 {
    static Thread t1;
    static Thread t2;
    static Thread t3;

    volatile static String input = null;


    public static void main(String[] args) {
        CountDownLatch downLatch = new CountDownLatch(1);
        t1 = new Thread(() -> {
            try {
                downLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            while (input == null) {
                System.out.println("A");
                LockSupport.unpark(t2);
                LockSupport.park();
                try {
                    TimeUnit.MILLISECONDS.sleep(200L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        t2 = new Thread(() -> {
            try {
                downLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            while (input == null) {
                LockSupport.park();
                System.out.println("B");
                LockSupport.unpark(t3);
                try {
                    TimeUnit.MILLISECONDS.sleep(200L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        t3 = new Thread(() -> {
            try {
                downLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            while (input == null) {
                LockSupport.park();
                System.out.println("C");
                LockSupport.unpark(t1);
                try {
                    TimeUnit.MILLISECONDS.sleep(200L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        Scanner scanner = new Scanner(System.in);
        t1.start();
        t2.start();
        t3.start();
        downLatch.countDown();
        while (true) {
            String next = scanner.next();
            if (next != null) {
                input = next;
                System.out.println("==end==" + next);
                break;
//                System.exit(0);
            }
        }
    }

}

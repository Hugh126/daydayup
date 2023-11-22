package com.example.myspring.hbs;



import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;


/**
 BolckingQueue 实现
 take 阻塞
 put
 */
public class Lesson5_3 {
    static Thread t1;
    static Thread t2;
    static Thread t3;

    volatile static String input = null;


    public static void main(String[] args) throws InterruptedException {
        CountDownLatch downLatch = new CountDownLatch(1);
        BlockingQueue<Integer> q1 = new ArrayBlockingQueue(1);
        BlockingQueue<Integer> q2 = new ArrayBlockingQueue(1);
        BlockingQueue<Integer> q3 = new ArrayBlockingQueue(1);
        t1 = new Thread(() -> {
            try {
                downLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            while (input == null) {

                try {
                    q1.take();
                    System.out.println("A");
                    q2.put(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


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

                try {
                    q2.take();
                    System.out.println("B");
                    q3.put(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

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

                try {
                    q3.take();
                    System.out.println("C");
                    q1.put(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

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
        q1.put(0);
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

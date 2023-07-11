package hbs;

import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 ReentrantLock 实现
 通过Condition，实现类似Object的Notify 和wait
 *
 */
public class Lesson5_2 {
    static Thread t1;
    static Thread t2;
    static Thread t3;

    volatile static String input = null;


    public static void main(String[] args) {
        CountDownLatch downLatch = new CountDownLatch(1);
        Lock lock = new ReentrantLock();
        Condition c1 = lock.newCondition();
        Condition c2 = lock.newCondition();
        Condition c3 = lock.newCondition();
        t1 = new Thread(() -> {
            try {
                downLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock.lock();
            while (input == null) {
                System.out.println("A");
                c2.signal();
                try {
                    c1.await();
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
            lock.lock();
            while (input == null) {
                System.out.println("B");
                c3.signal();
                try {
                    c2.await();
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
            lock.lock();
            while (input == null) {
                System.out.println("C");
                c1.signal();
                try {
                    c3.await();
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

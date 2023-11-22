package interview;

import lombok.val;

import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 两个线程交替完成输出
 * A1B2....
 */
public class ThreadAlternate {
    static Thread t1 = null;
    static Thread t2 = null;
    static Thread t3 = null;
    static char[] arrChar = {'A', 'B', 'C', 'D', 'E'};
    static int[] arrNum = {1,2,3,4,5};

    /**
     * 使用 LockSupport
     */
    public static void fun1() {
        t1 = new Thread(() -> {
            for (char x : arrChar) {
                System.out.println(x);
                LockSupport.unpark(t2);
                LockSupport.park();
            }
        }, "t1");

        t2 = new Thread(() -> {
            for (int y : arrNum) {
                LockSupport.park();
                System.out.println(y);
                LockSupport.unpark(t1);
            }
        }, "t2");

        t1.start();
        t2.start();
    }

    /**
     * TransferQueue
     *
     * t1 -- t2 交换内容输出
     */
    public static void fun2() {
        TransferQueue queue = new LinkedTransferQueue();
        t1 = new Thread( () -> {
            try{
                for (char x : arrChar) {
                    queue.transfer(x);
                    System.out.println(Thread.currentThread().getName() + "  " + queue.take());
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        }, "t1");

        t2 = new Thread( () -> {
            try {
                for (int y : arrNum) {
                    System.out.println(Thread.currentThread().getName() + "  " + queue.take());
                    queue.transfer(y);
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        }, "t2");
        t1.start();
        t2.start();
    }

    /**
     * BlockingQueue
     */
    public static void fun3() {
        BlockingQueue<Character> queue1 = new ArrayBlockingQueue(1);
        BlockingQueue<Integer> queue2 = new ArrayBlockingQueue(1);
        t1 = new Thread(() -> {
            try {
                for (char x : arrChar) {
                    System.out.println(x);
                    queue1.put('k');
                    queue2.take();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } , "t1");
        t2 = new Thread(() -> {
            try {
                for (int y : arrNum) {
                    queue1.take();
                    System.out.println(y);
                    queue2.put(2);
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        }, "t2");
        t1.start();
        t2.start();
    }

    /**
     * synchronized
     */
    public static void fun4() {
        Object obj = new Object();
        t1 = new Thread(() -> {
            synchronized (obj) {
                try {
                    for (char x : arrChar) {
                        System.out.println(x);
                        obj.notify();
                        obj.wait();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                obj.notifyAll();
            }
        } , "t1");
        t2 = new Thread(() -> {
            synchronized (obj) {
                try {
                    for (int y : arrNum) {
                        System.out.println(y);
                        obj.notify();
                        obj.wait();
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }, "t2");
        t1.start();
        t2.start();
    }


    /**
     * ReentrantLock
     */
    public static void fun5() {
        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();

        t1 = new Thread( () -> {
            lock.lock();
            try {
                for (char x : arrChar) {
                    System.out.println(x);
                    condition.signal();
                    condition.await();
                }
                condition.signal();
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        });

        t2 = new Thread( () -> {
            lock.lock();
            try {
                for (int y : arrNum) {
                    System.out.println(y);
                    condition.signal();
                    condition.await();
                }
//                condition.signal();
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        });
        t1.start();
        t2.start();
    }


    /**
     * ReentrantLock
     * @see <a href="https://zhuanlan.zhihu.com/p/517749666">深入浅出的重入锁</a>
     * two condition
     *
     */
    public static void fun6() {
        Lock lock = new ReentrantLock();
        Condition condition1 = lock.newCondition();
        Condition condition2 = lock.newCondition();

        t1 = new Thread( () -> {
            lock.lock();
            try {
                for (char x : arrChar) {
                    System.out.println(x);
                    condition2.signal();
                    condition1.await();
                }
                condition2.signal();
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        });

        t2 = new Thread( () -> {
            lock.lock();
            try {
                for (int y : arrNum) {
                    System.out.println(y);
                    condition1.signal();
                    condition2.await();
                }
//                condition.signal();
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        });
        t1.start();
        t2.start();
    }


    /**
     * ReentrantLock
     * 3 condition
     *
     */
    public static void fun7() {

        final char[] ohterArr = {'a', 'b', 'c', 'd', 'e'};

        Lock lock = new ReentrantLock();
        Condition condition1 = lock.newCondition();
        Condition condition2 = lock.newCondition();
        Condition condition3 = lock.newCondition();


        t1 = new Thread( () -> {
            lock.lock();
            try {
                while (true) {
                    System.out.println("A");
                    condition2.signal();
                    condition1.await();
                }
//                for (char x : arrChar) {
//                    System.out.println(x);
//                    condition2.signal();
//                    condition1.await();
//                }
//                condition2.signal();
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        });

        t2 = new Thread( () -> {
            lock.lock();
            try {
                while (true) {
                    System.out.println("B");
                    condition3.signal();
                    condition2.await();
                }
//                for (int y : arrNum) {
//                    System.out.println(y);
//                    condition3.signal();
//                    condition2.await();
//                }
//                condition3.signal();
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        });

        Thread t3 = new Thread( () -> {
            lock.lock();
            try {
                while (true) {
                    System.out.println("C");
                    condition1.signal();
                    condition3.await();
                }
//                for (char z : ohterArr) {
//                    System.out.println(z);
//                    condition1.signal();
//                    condition3.await();
//                }
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        });


        t1.start();
        t2.start();
        t3.start();
    }



    public static void main(String[] args) throws InterruptedException {
//        fun1();
//        fun2();
//        fun3();
//        fun4();
//        fun5();
        fun6();
//        fun7();

 
    }

}

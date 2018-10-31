package multiThread.concurrent;

import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatch 提供了闭锁，可以实现真正的并行
 *
 */
class Worker implements Runnable {

        private final CountDownLatch startSignal ;
        private final CountDownLatch doneSignal ;

        public Worker(CountDownLatch startSignal, CountDownLatch doneSignal) {
            this.startSignal = startSignal;
            this.doneSignal = doneSignal;
        }

        @Override
        public void run() {
            try {
                //等待主线程执行完毕，获得开始执行信号
                startSignal.await();

                doWork();

                //完成预期工作，发出完成信号
                doneSignal.countDown();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        private void doWork() {

            String name = Thread.currentThread().getName();

            System.out.println(String.format("thread %s工作开始……", name));

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(String.format("thread %s工作完成……", name));
        }
    }
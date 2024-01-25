package multiThread;

import org.junit.Test;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class CountDownLatchTest {

    public static class RandomSleep implements Runnable {
        private Integer index;
        private CountDownLatch latch;
        public RandomSleep(Integer index, CountDownLatch latch) {
            this.index = index;
            this.latch = latch;
        }
        @Override
        public void run() {
            try {
                TimeUnit.SECONDS.sleep(new Random().nextInt(5));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                System.out.println(this.index + " Done");
                latch.countDown();
            }
        }
    }


    @Test
    public void test1() throws InterruptedException {
        ExecutorService threadPool = Executors.newFixedThreadPool(3);
        CountDownLatch downLatch =  new CountDownLatch(10);
        IntStream.range(0,10).forEach(t -> {
            threadPool.execute(new RandomSleep(t,downLatch));
        });
        while (true) {
            if (downLatch.getCount() == 0) {
                System.out.println("Task Done");
                break;
            }else {
                System.out.println("Task processing ...");
                TimeUnit.MILLISECONDS.sleep(200);
            }
        }
    }


}

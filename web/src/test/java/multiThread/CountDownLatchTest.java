package multiThread;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class CountDownLatchTest {

    public static class RandomSleep implements Runnable {
        public static AtomicInteger doneTask = new AtomicInteger(0);
        private Integer index;
        private String name;

        public void setName(String name) {
            this.name = name;
        }

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
                doneTask.incrementAndGet();
                System.out.println("Name=" + name +  " TaskNum =" + this.index + " Done");
                latch.countDown();
            }
        }
    }

    private static Map<String, CountDownLatch> deployLatchMap = new HashMap<>();
    ThreadPoolExecutor threadPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(3);

    private CountDownLatch initLatch(String name, int size) throws IllegalAccessException {
        CountDownLatch latch = deployLatchMap.get(name);
        if (latch == null) {
            latch = new CountDownLatch(size);
            deployLatchMap.put(name, latch);
        }else {
            if (latch.getCount() > 0) {
                throw new IllegalAccessException(String.format("该任务%s仍在执行中，请稍后再试", name));
            }
        }
        return latch;
    }

    private void batchRunTask(String name, int size) throws IllegalAccessException {
        CountDownLatch latch = initLatch(name, size);
        IntStream.range(0,size).forEach(t -> {
            RandomSleep randomSleep = new RandomSleep(t, latch);
            randomSleep.setName(name);
            threadPool.execute(randomSleep);
        });
    }

    @Test
    public void test2() throws InterruptedException, IllegalAccessException {
        batchRunTask("t3", 10);
        try {
            batchRunTask("t3", 5);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        TimeUnit.SECONDS.sleep(15L);

        batchRunTask("t3", 5);

        while (threadPool.getCompletedTaskCount() < threadPool.getTaskCount()) {
            System.out.println("--running--");
            TimeUnit.SECONDS.sleep(3L);
        }
        System.out.println("[END task count sum =]" + RandomSleep.doneTask.get());
    }


}

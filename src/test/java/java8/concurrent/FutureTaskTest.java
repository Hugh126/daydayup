package java8.concurrent;

import cn.hutool.core.thread.NamedThreadFactory;
import com.baomidou.mybatisplus.extension.api.R;
import javafx.concurrent.Task;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.junit.Test;
import org.springframework.util.StopWatch;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

@Slf4j
public class FutureTaskTest {


    @Test
    public void test1() {
        FutureTask task = new FutureTask(() -> {
            log.warn("start");
            TimeUnit.SECONDS.sleep(5);
            return "ok";
        });
//        new Thread(task).start();
        Executors.newFixedThreadPool(1).submit(task);
        try {
            log.warn(task.get().toString());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    final ExecutorService executor = Executors.newFixedThreadPool(3, new NamedThreadFactory("xx", false));
    List<Callable<String>> taskList = new ArrayList<>();
    List<String> resultList = new ArrayList<>();

    private void addTask() {
        IntStream.range(1,10).forEach(i -> taskList.add( () -> {
            Random random = new Random();
            int x = random.nextInt(10);
            TimeUnit.SECONDS.sleep(x);
            return String.format("task%d, result=%d", i,x);
        } ));
    }

    private void collectTask() throws InterruptedException {
        List<Future<String>> futures = executor.invokeAll(taskList);
        futures.forEach(future -> {
            try {
                // 阻塞
                resultList.add(future.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        });
        executor.shutdown();
    }


    /**
     * 批量提交任务并收集结果
     * @throws InterruptedException
     */
    @Test
    public void batchSubmitAndReturn() throws InterruptedException {
        addTask();
        StopWatch stopWatch = new StopWatch(this.getClass().getName());
        stopWatch.start();
        collectTask();
        while (!executor.awaitTermination(100L, TimeUnit.SECONDS)) {

        }
        stopWatch.stop();
        int sum = resultList.stream().mapToInt(t -> Integer.valueOf(t.substring(t.length()-1))).sum();
        System.out.println("[task done] sum= " + sum  + " Actual : " + stopWatch.getTotalTimeMillis() + "ms");
        resultList.stream().forEach(System.out::println);
    }

    static AtomicInteger cnt = new AtomicInteger();

    /**
     * 怎么知道线程池任务执行完成
     * 1、线程内外通信
     *
     * @throws InterruptedException
     */
    @Test
    public void batchAddThenGetTasks() throws InterruptedException {
        int taskCnt = 10;
        CountDownLatch countDownLatch = new CountDownLatch(taskCnt);
        for (int i = 0; i < taskCnt; i++) {
            executor.execute(() -> {
                try {
                    TimeUnit.SECONDS.sleep(1L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                cnt.incrementAndGet();
                countDownLatch.countDown();
            });
        }

        while (cnt.get() < taskCnt) {
            System.out.println("complated tasks " + cnt.get());
            TimeUnit.SECONDS.sleep(2);
        }
        // 外层阻塞
        countDownLatch.await();
        System.out.println("[END] complated tasks " + cnt.get());
        System.out.println("task done");
    }


}

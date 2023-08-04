package multiThread;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 *
 */
@Slf4j
public class BatchRunWithResult {


    public static void main(String[] args) throws InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(5);

        int doneNum = 10;

        CountDownLatch start = new CountDownLatch(1);
        CountDownLatch done = new CountDownLatch(doneNum);


        List<Future<Integer>> futureList = IntStream.range(0, doneNum).mapToObj(i -> executorService.submit(() -> {
            try {
                start.await();
                log.warn(String.format("Thread %s done.", Thread.currentThread().getName()));
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                done.countDown();
            }
            return new Random().nextInt(5);
        })).collect(Collectors.toList());

        System.out.println("通知所有线程开始执行……");
        start.countDown();

        System.out.println("等待所有线程执行完毕……");
        done.await();

        System.out.println("所有线程已经执行完毕!");
        List<Integer> result = futureList.stream().map(x -> {
            try {
                return x.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            return null;
        }).collect(Collectors.toList());
        System.out.println("[result]=" + result.stream().map(String::valueOf).collect(Collectors.joining(",")));
    }



}

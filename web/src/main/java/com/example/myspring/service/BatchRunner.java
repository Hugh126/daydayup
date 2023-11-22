package com.example.myspring.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class BatchRunner<T,R> {

    private final  static ExecutorService executorService = Executors.newFixedThreadPool(5);

    private Integer taskNum;
    List<T> paraList;
    private Function<T,R> task;
    private List<R> result;

    private boolean concurrent = false;

    public boolean isSuccess() {
        if (result == null) {
            return false;
        }
        return result.stream().filter(x -> x == null ).count() == 0;
    }

    public BatchRunner(List<T> paraList, Function<T,R> task) {
        this.taskNum = paraList.size();
        this.paraList = paraList;
        this.task = task;
    }

    public BatchRunner(List<T> paraList, Function<T,R> task, boolean concurrent) {
        this.taskNum = paraList.size();
        this.paraList = paraList;
        this.task = task;
        this.concurrent = concurrent;
    }


    public List<R> getResult() {
        return result;
    }

    public void run() {
        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch threadsLatch = new CountDownLatch(taskNum);
        List<Future> futureList = new ArrayList<>(taskNum);
        paraList.forEach( p -> {
            Future<R> future = executorService.submit(() -> {
                try{
                    if (concurrent) {
                        startLatch.await();
                    }
                    R apply = task.apply(p);
                    return apply;
                }catch (Exception e){
                    e.printStackTrace();
                    return null;
                }finally {
                    threadsLatch.countDown();
                }

            });
            futureList.add(future);
        });
        startLatch.countDown();

        try {
            threadsLatch.await(10L, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.result = futureList.stream().map(f -> {
            try {
                return (R)f.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            return null;
        }).collect(Collectors.toList());
    }

}

package hbs;

import java8.concurrent.CompletableFutureTest;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * 已知一个业务查询操作涉及 3 个 RPC 服务调用 : query1, query2, query3, 其中
 * query1 耗时约 1 秒， query2 耗时约 0.5 秒，query3 耗时约 0.6 秒，且 query3
 * 查询条件依赖 query2 的查询结果，
 * 请编写代码，使该业务查询总体耗时最小。
 *
 *
 * jdk8中CompletableFutureTest ，或者 reactor框架可轻松实现
 */
@Slf4j
public class Lesson7 {

    private String query1() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.warn("query1 --");
        return "query1";
    }

    private String query2()  {
        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.warn("query2 --");
        return "query2";
    }

    private String query3(String params)  {
        try {
            TimeUnit.MILLISECONDS.sleep(600);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.warn("query3 --, params={}", params);
        return params + "_query3";
    }

    public void run() throws InterruptedException, ExecutionException, TimeoutException {
        log.warn("start--");
        CompletableFuture<String> task1 = CompletableFuture.supplyAsync(() -> query1());
        CompletableFuture<String> task2 = CompletableFuture.supplyAsync(() -> query2()).thenApplyAsync(params -> query3(params));
        CompletableFuture.allOf(task1, task2).whenComplete((res, ex) -> {
            log.warn("done");
        }).get(1200, TimeUnit.MILLISECONDS);
        System.out.println("task1=" + task1.get());
        System.out.println("task2=" + task2.get());
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {
        Lesson7 demo = new Lesson7();
        demo.run();
    }

}

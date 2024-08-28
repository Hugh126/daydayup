package java8.concurrent.completeFuture;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

/**
 * 将两队任务组合
 *
 * task1组完成后，再完成task2，组装成1个 CompletableFuture
 */
@Slf4j
public class CompletableFutureTest2 {

    List<String> task1ResultList = new ArrayList<>();

    CompletableFuture<String> runTask1(String name) {
        return CompletableFuture.supplyAsync(() -> {
            log.info("taskName={}", name);
            return name + "_Result_" + Thread.currentThread().getId();
        }).handle((res, e) -> {
            // 不侵入任务本身 收集结果
            if (e == null) {
                task1ResultList.add(res);
            } else {
                task1ResultList.add(e.getMessage());
            }
            return res;
        });
    }

    CompletableFuture<String> runTask2(CompletableFuture preTask, String name) {
        return preTask.thenRunAsync(() -> {
            log.info("taskName={}", name);
            // 利用task1的结果 process
            log.warn("preResult=" + task1ResultList.stream().collect(Collectors.joining(",")));
        });
    }

    @Test
    void test3() {
        // 1
        List<CompletableFuture> list1 = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            CompletableFuture cf1 = runTask1("task1");
            list1.add(cf1);
        }
        CompletableFuture[] arr1 = list1.toArray(new CompletableFuture[list1.size()]);
        CompletableFuture<Void> f11 = CompletableFuture.allOf(arr1);


        // 2
        List<CompletableFuture> list2 = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            CompletableFuture cf2 = runTask2(f11, "task2");
            list1.add(cf2);
        }
        CompletableFuture[] arr2 = list1.toArray(new CompletableFuture[list2.size()]);
        CompletableFuture.allOf(arr2).join();
    }


    /**
     * 已知一个业务查询操作涉及 3 个 RPC 服务调用 : query1, query2, query3, 其中
     * query1 耗时约 1 秒， query2 耗时约 0.5 秒，query3 耗时约 0.6 秒，且 query3查询条件依赖 query2 的查询结果，
     * 请编写代码，使该业务查询总体耗时最小
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    void testJoinAndGet() throws ExecutionException, InterruptedException {
        long startTime = System.currentTimeMillis();

        // 模拟异步调用 query1
        CompletableFuture<String> query1Future = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000); // 模拟 query1 耗时 1 秒
                System.out.println("query1 completed");
                return "query1 result";
            } catch (InterruptedException e) {
                e.printStackTrace();
                return null;
            }
        });

        // 模拟异步调用 query2
        CompletableFuture<String> query2Future = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(500); // 模拟 query2 耗时 0.5 秒
                System.out.println("query2 completed");
                return "query2 result";
            } catch (InterruptedException e) {
                e.printStackTrace();
                return null;
            }
        });

        // query3 依赖于 query2 的结果
        CompletableFuture<String> query3Future = query2Future.thenApply(result -> {
            try {
                Thread.sleep(600); // 模拟 query3 耗时 0.6 秒
                System.out.println("query3 completed using " + result);
                return "query3 result";
            } catch (InterruptedException e) {
                e.printStackTrace();
                return null;
            }
        });

        // 等待 query1 和 query3 都完成，并获取结果
        CompletableFuture<Void> allOf = CompletableFuture.allOf(query1Future, query3Future);
        allOf.get();

        // 获取各个查询的结果
        String result1 = query1Future.get();
        String result3 = query3Future.get();

        long endTime = System.currentTimeMillis();
        System.out.println("Total time: " + (endTime - startTime) + " ms");
        System.out.println("Final results: query1=" + result1 + ", query3=" + result3);
    }
}
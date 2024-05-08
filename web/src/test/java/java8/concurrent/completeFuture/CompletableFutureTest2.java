package java8.concurrent.completeFuture;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
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

}
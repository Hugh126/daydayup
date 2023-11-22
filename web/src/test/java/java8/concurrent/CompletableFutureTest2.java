package java8.concurrent;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * 将两队任务组合
 *
 * task1组完成后，再完成task2，组装成1个 CompletableFuture
 */
@Slf4j
public class CompletableFutureTest2 {

    CompletableFuture<String> foo(String name, String id) {
        return CompletableFuture.supplyAsync(() -> {
            log.info("name={}-{}-",name,id);
            return  id;
        } );
    }

    CompletableFuture<String> fun(CompletableFuture f, String name) {
        return f.thenApplyAsync(t -> {
            log.info("name={},t={}",name, t);
            return t+" 1";
        });
    }

    @Test
    void test3() throws ExecutionException, InterruptedException {
        // 1
        List<CompletableFuture> list1 = new ArrayList<>();
        for(int i=0;i<10;i++) {
            CompletableFuture cf1 = foo("task1","aa" + i);
            list1.add(cf1);
        }
        CompletableFuture[] arr = list1.toArray(new CompletableFuture[list1.size()]);
        CompletableFuture<Void> f11 = CompletableFuture.allOf(arr);


        // 2
        List<CompletableFuture> list2 = new ArrayList<>();
        for(int i=0;i<10;i++) {
            CompletableFuture cf2 = fun( f11 , "task2");
            list1.add(cf2);
        }
        CompletableFuture[] arr2 = list1.toArray(new CompletableFuture[list2.size()]);
        CompletableFuture.allOf(arr2).join();

    }


}

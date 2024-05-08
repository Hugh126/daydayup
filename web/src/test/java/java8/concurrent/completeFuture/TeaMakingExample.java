package java8.concurrent.completeFuture;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import java.util.stream.Collectors;


/**
 * 任务支线1  洗水壶 --> 烧开水
 * 任务支线2  洗茶壶 --> 洗茶杯 --> 取茶叶
 * 任务汇总   泡茶
 */
@Slf4j
public class TeaMakingExample {

    public static void randomSleep() {
        try {
            TimeUnit.SECONDS.sleep(new Random().nextInt(5));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    final static List<String> resList = new ArrayList<>();

    static CompletableFuture buildSupplier(Supplier<String> function) {
        return CompletableFuture.supplyAsync(function).handle((res, ex) -> {
            resList.add(res);
            return res;
        });
    }

    public static void main(String[] args) {

        CompletableFuture<String> task1 = CompletableFuture.supplyAsync(() -> {
            log.info("任务11：洗水壶");
            randomSleep();
            return "干净的水壶";
        }).thenApplyAsync( x -> {
            log.info("任务12：烧开水");
//            log.info("参数=" + x);
            randomSleep();
            return "开水";
        }).handle((res, ex) -> {
            resList.add(res);
            return res;
        });

        CompletableFuture<String> task21 = buildSupplier(() -> {
            log.info("任务21: 洗茶壶");
            randomSleep();
            return "干净的茶壶";
        });

        CompletableFuture<String> task22 = buildSupplier(() -> {
            log.info("任务22: 洗茶杯");
            randomSleep();
            return "干净的茶杯";
        });

        CompletableFuture<String> task23 = buildSupplier(() -> {
            log.info("任务23: 取茶叶");
            randomSleep();
            return "干净的茶叶";
        });

        // 组装任务
        CompletableFuture<Void> allTasks = CompletableFuture.allOf(task1, task21, task22, task23).thenRunAsync( () -> {
            log.warn("---所有材料准备齐全---");
            log.warn("[{}]", resList.stream().collect(Collectors.joining(",")));
            log.warn("===最终步骤：：泡茶===");
        });

        // 阻塞直到所有 CompletableFuture 完成
        allTasks.join();

    }


}

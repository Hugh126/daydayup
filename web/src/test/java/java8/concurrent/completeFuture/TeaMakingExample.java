package java8.concurrent.completeFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class TeaMakingExample {

    public static void main(String[] args) {
        // 创建 CompletableFuture 任务
        CompletableFuture<Void> task1 = CompletableFuture.runAsync(() -> {
            System.out.println("任务1：洗水壶、烧开水");
            // 模拟任务执行时间
            sleep(2000);
        });

        CompletableFuture<Void> task2 = CompletableFuture.runAsync(() -> {
            System.out.println("任务2：洗茶壶、洗茶杯、拿茶叶");
            // 模拟任务执行时间
            sleep(1500);
        });

        CompletableFuture<Void> task3 = CompletableFuture.runAsync(() -> {
            // 等待任务1和任务2完成
            CompletableFuture.allOf(task1, task2).join();
            System.out.println("任务3：泡茶");
        });

        // 等待所有任务完成
        CompletableFuture<Void> allTasks = CompletableFuture.allOf(task1, task2, task3);
        try {
            allTasks.get(); // 阻塞等待所有任务完成
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

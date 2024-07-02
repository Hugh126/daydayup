package multiThread.threadpool;



import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalTime;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolParamsTest {

     public static ThreadPoolExecutor pool = new ThreadPoolExecutor(3, 5,
                                      0L,TimeUnit.MILLISECONDS,
                                      new LinkedBlockingQueue<Runnable>());

    public static String poolStatus(ThreadPoolExecutor executor){
        BlockingQueue<Runnable> queue = executor.getQueue();
        return String.join(" ", "核心线程数:" + executor.getCorePoolSize(),
                "活动线程数："+executor.getActiveCount(),
                "最大线程数："+executor.getMaximumPoolSize(),
                "线程池活跃度：" + divide(executor.getActiveCount(),executor.getMaximumPoolSize()),
                "任务完成数："+executor.getCompletedTaskCount(),
                "队列大小："+(queue.size()+queue.remainingCapacity()),
                "当前排队线程数："+queue.size(),
                "队列剩余大小："+queue.remainingCapacity(),
                "队列使用度："+divide(queue.size(),queue.size()+queue.remainingCapacity()));
    }

    private static String divide(int num1,int num2){
        return BigDecimal.valueOf(num1).divide(BigDecimal.valueOf(num2), 2, RoundingMode.HALF_UP).toString();
    }

    public static void main(String[] args) throws InterruptedException {
        for (int i=0;i<10;i++) {
            int finalI = i;
            pool.submit(() -> {
                System.out.println(String.format("任务%d开始", finalI));
                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(String.format("任务%d完成", finalI));
            });
            System.out.println(poolStatus(pool));
        }
        int i= 10;
        while (i--> 0){
            if (pool.getActiveCount() == 0 && pool.getQueue().size() ==0) {
                System.out.println("任务都完成了");
                break;
            }
            TimeUnit.SECONDS.sleep(5);
        }
        pool.shutdown();
        System.out.println("线程池将关闭 " + LocalTime.now().toString());
        if (pool.awaitTermination(2L, TimeUnit.SECONDS)) {
            System.out.println("线程池已经关闭 " + LocalTime.now().toString());
        }


    }
}

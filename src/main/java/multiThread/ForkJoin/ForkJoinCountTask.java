package multiThread.ForkJoin;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

/**
 * @author hugh
 * @Title: ForkJoinCountTask
 * @ProjectName springboottest
 * @Description: TODO
 * @date 2018/10/1521:27
 */
public class ForkJoinCountTask extends RecursiveTask {

    public static final int THRESHOLD = 2;

    public int start;

    public int end;

    public ForkJoinCountTask(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer  compute() {
        int sum = 0;

        // 判断任务是否足够小
        if ((end - start) <= THRESHOLD ) {
            for (int i = start;i <= end; i++) {
                sum += i;
            }
        }else {
            // 拆分任务
            int middle = (end + start) / 2;
            ForkJoinCountTask leftTask = new ForkJoinCountTask(start, middle);
            ForkJoinCountTask rightTask = new ForkJoinCountTask(middle + 1, end);
            // 异步执行子任务
            leftTask.fork();
            rightTask.fork();
            // 获取子任务结果
            int leftResult = (int) leftTask.join();
            int rightResult = (int) rightTask.join();
            // 合并任务结果
            sum = leftResult + rightResult;
        }

        return sum;
    }

    public static void main(String[] args) {
        // 需要通过ForkJoinPool 来执行
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinCountTask forkJoinCountTask = new ForkJoinCountTask(1, 100);
        // 执行一个ForkJoinTask 任务
        Future<Integer>  result = forkJoinPool.submit(forkJoinCountTask);

        try {
            // 获取任务结果
            System.out.println(result.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }finally {
            // 关闭线程池
            forkJoinPool.shutdown();
        }
    }
}

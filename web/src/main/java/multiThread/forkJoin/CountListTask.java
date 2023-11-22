package multiThread.forkJoin;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

/**
 * @author hugh
 * @date 2018/10/1522:07
 * @Description: 利用ForkJoin框架求一个数组的和
 */

public class CountListTask  extends RecursiveTask {

    public static final int THRESHOLD = 2;


    int startIndex;
    int endIndex;
    List<Integer> numList;

    public CountListTask(List<Integer> numList) {
        this.numList = numList;
        startIndex = 0;
        endIndex = numList.size();
    }

    @Override
    protected Integer compute() {
        System.out.println(Thread.currentThread().getName() + " | " + Thread.currentThread().getId());
        int sum = 0;
        // 判断是否达到阀值
        if (numList.size() <= THRESHOLD) {
            for (Integer a : numList) {
                sum += a;
            }
        }else {
            // 继续细分task
            int middle = (startIndex + endIndex) / 2;
            List<Integer> left = numList.subList(startIndex, middle); // subList 前闭后开区间[)
            List<Integer> right = numList.subList(middle, numList.size());
            CountListTask leftTask = new CountListTask(left);
            CountListTask rightTask = new CountListTask(right);
            // 异步执行子task
            leftTask.fork();
            rightTask.fork();
            // 等待完成并获取结果
            Integer leftResult = (Integer) leftTask.join();
            Integer rightResult = (Integer) rightTask.join();
            // 合并
            sum = leftResult + rightResult;
        }

        return sum;
    }

    public static void main(String[] args) {
        List<Integer> ori = new ArrayList(){{
            add(11);
            add(33);
            add(22);
            add(98);
            add(60);
            add(69);
            add(77);
            add(88);
            add(33);
            add(28);
        }};

//        {11, 33, 22, 98, 60, 69, 77, 88, 33, 28};

        // 通过ForkJoinPool来执行
        ForkJoinPool pool = new ForkJoinPool();
        // 执行子任务
        Future result = pool.submit(new CountListTask(ori));
        // 获取结果
        try {
            System.out.println(result.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        // 关闭线程池
        pool.shutdown();

    }
}

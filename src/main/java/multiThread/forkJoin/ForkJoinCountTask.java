package multiThread.forkJoin;



import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

/**
 * @author hugh
 * @Title: ForkJoinCountTask
 * @ProjectName springboottest
 * @Description:
 *
 *  对于简单的任务，直接循环更简单有效
 *
 * @date 2018/10/1521:27
 */
public class ForkJoinCountTask extends RecursiveTask {

    public static final int THRESHOLD = 30000000;

    public int start;

    public int end;

    public ForkJoinCountTask(int start, int end) {
        this.start = start;
        this.end = end;
    }

    private static int getInt(){
        Random random = new Random();
        return random.nextInt(3);
    }

    private  TSum subCalc(int start, int end) {
        TSum summary = new TSum();
        for(int i=start;i<end;i++) {
            int a = getInt();
            switch (a){
                case 0:
                    summary.getCnt0().incrementAndGet();
                    break;
                case 1:
                    summary.getCnt1().incrementAndGet();
                    break;
                case 2:
                    summary.getCnt2().incrementAndGet();
                    break;
            }
        }
        return summary;
    }

    @Override
    protected TSum  compute() {


        // 判断任务是否足够小
        if ((end - start) <= THRESHOLD ) {
//            for (int i = start;i <= end; i++) {
//                sum += i;
//            }
                return subCalc(start, end);

        }else {
            // 拆分任务
            int middle = (end + start) / 2;
            ForkJoinCountTask leftTask = new ForkJoinCountTask(start, middle);
            ForkJoinCountTask rightTask = new ForkJoinCountTask(middle + 1, end);
            // 异步执行子任务
            leftTask.fork();
            rightTask.fork();
            // 获取子任务结果
            TSum leftResult = (TSum) leftTask.join();
            TSum rightResult = (TSum) rightTask.join();
            // 合并任务结果
            TSum total = new TSum();
            total.getCnt0().set(leftResult.getCnt0().get() + rightResult.getCnt0().get());
            total.getCnt1().set(leftResult.getCnt1().get() + rightResult.getCnt1().get());
            total.getCnt2().set(leftResult.getCnt2().get() + rightResult.getCnt2().get());
            return total;
        }

    }

    public static void main(String[] args) {
        // 需要通过ForkJoinPool 来执行
        long t1 = System.currentTimeMillis();
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinCountTask forkJoinCountTask = new ForkJoinCountTask(1, 300000000);
        // 执行一个ForkJoinTask 任务
        Future<TSum>  result = forkJoinPool.submit(forkJoinCountTask);
        try {
            // 获取任务结果
            System.out.println(result.get().toString());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }finally {
            // 关闭线程池
            forkJoinPool.shutdown();
        }

        System.out.println("耗时=" + ((System.currentTimeMillis()-t1)));
    }
}

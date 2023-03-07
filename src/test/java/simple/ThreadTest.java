package simple;

import org.junit.Test;

import java.util.concurrent.*;

public class ThreadTest {

    ThreadPoolExecutor instance = new ThreadPoolExecutor(8, 8,
            60L, TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>(1024),
            new ThreadPoolExecutor.CallerRunsPolicy());

    @Test
    public void test1() {
        Future<Integer> future = instance.submit(() -> {
            Integer a = 0;
            return 1/a;
        });
        try {
            System.out.println(future.get());
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("---submit error--------");
        }
        try {
            TimeUnit.SECONDS.sleep(3L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("--end--");
        instance.shutdown();
    }

    @Test
    public void test2() {
        try {
            instance.execute(() -> {
                Integer a = 0;
                System.out.println(1/a);;
            });
        }catch (Exception e) {
            System.out.println("---execute error--------");
        }
        System.out.println("--end--");
        try {
            TimeUnit.SECONDS.sleep(3L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        instance.shutdown();
    }

    @Test
    public void test3() {
        System.out.println(System.currentTimeMillis());
    }
}

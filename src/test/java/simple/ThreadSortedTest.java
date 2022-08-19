package simple;

import cn.hutool.json.JSONUtil;
import org.assertj.core.util.Lists;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ThreadSortedTest {

    @Test
    public void test1() throws InterruptedException {
        Thread a = new Thread(new ThreadX(), "a");
        Thread b = new Thread(new ThreadX(), "b");
        Thread c = new Thread(new ThreadX(), "c");
        a.start();
        b.join();
        c.join();

    }

    @Test
    public void test2() {
        List<Integer> list = Stream.of(Lists.list(1, 2), Lists.list(4, 5, 6), Lists.list(8, 9 , 11)).reduce((a, b) -> {
            a.addAll(b);
            return a;
        }).get();
        System.out.println(JSONUtil.toJsonStr(list));
    }

    public class ThreadX implements Runnable{
        @Override
        public void run() {
            System.out.println("current=" + Thread.currentThread().getName());
            try {
                TimeUnit.MICROSECONDS.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

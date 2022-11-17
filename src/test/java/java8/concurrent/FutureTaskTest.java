package java8.concurrent;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

@Slf4j
public class FutureTaskTest {


    @Test
    public void test1() {
        FutureTask task = new FutureTask(() -> {
            log.warn("start");
            TimeUnit.SECONDS.sleep(5);
            return "ok";
        });
//        new Thread(task).start();
        Executors.newFixedThreadPool(1).submit(task);
        try {
            log.warn(task.get().toString());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }


}

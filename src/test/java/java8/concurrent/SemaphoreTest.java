package java8.concurrent;


import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * 可以理解为一个停车场控制器，有空位则进，没空位则阻塞; 抢占式
 *
 * 更适合抢红包场景
 *
 * AbstractQueuedSynchronizer sync = new NonfairSync(permits);
 */
@Slf4j
public class SemaphoreTest {

    Semaphore semaphore = new Semaphore(3);

    class parkCar implements Runnable{

        @Override
        public void run() {
            parkCar();
        }

        @SneakyThrows
        void parkCar() {
            String name = Thread.currentThread().getName();
            boolean lock = semaphore.tryAcquire(500, TimeUnit.MILLISECONDS);
            if (!lock) {
                parkCar();
            }else {
                log.warn(name + "已停车" );
                try {
                    int num = new Random().nextInt(1000);
                    TimeUnit.MILLISECONDS.sleep(1000 + num);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                semaphore.release();
                log.warn(name + "出去了");
            }
        }
    }



    @Test
    public void test1() throws InterruptedException {
        IntStream.range(1,10).forEach(t -> {
            new Thread(new parkCar(), "t" + t).start();
        });
        TimeUnit.SECONDS.sleep(20L);
    }


}

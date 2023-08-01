package java8.concurrent;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * https://juejin.cn/post/7006895386103119908
 *
 * AbstractQueuedSynchronizer Demo中定义了一个互斥锁的实现
 */
@Slf4j
public class AQSTest  {

    @Test
    public void test1() throws InterruptedException {
        Mutex lock = new Mutex();
        Runnable r = () -> {
            lock.lock();
            log.warn("get Lock");
            try {
                log.warn("do something...");
                TimeUnit.SECONDS.sleep(1L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock.unlock();
            log.warn("release Lock");
        };
        new Thread(r, "t1").start();
        new Thread(r, "t2").start();
        // wait to down
        TimeUnit.SECONDS.sleep(3L);
    }


}

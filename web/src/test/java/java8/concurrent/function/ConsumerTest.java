package java8.concurrent.function;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.time.LocalTime;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
@Slf4j
public class ConsumerTest  {

    @Test
    public void test1() {

        Consumer<String> c1 = s -> System.out.println("accept1 =" + s);

        Consumer<String> c2 = s -> System.out.println("accept2 =" + s);

        c1.andThen(c2).accept("hello");

    }

    @Test
    public void test2() {
        AtomicInteger atomicInteger = new AtomicInteger(0);
        while (true) {
            int minute = LocalTime.now().getMinute();
            if(minute%2 == 0) {
                log.warn("now:{}  index: {}", LocalTime.now().toString(), atomicInteger.incrementAndGet());
//                TimeUnit.SECONDS.sleep(118);
            }
        }
    }
}

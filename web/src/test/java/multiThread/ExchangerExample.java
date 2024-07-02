package multiThread;

import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * 线程可以配对和交换元素
 */
public class ExchangerExample {

    public static void main(String[] args) throws InterruptedException {
        Exchanger<String> exchanger = new Exchanger<>();

        IntStream.range(1,5).parallel().forEach(x -> {
            new Thread(()-> {
                try {
                    String name = Thread.currentThread().getName();
                    System.out.println("x=" + x + " local=" + name + " exchanger= " + exchanger.exchange(name));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        });

        TimeUnit.SECONDS.sleep(10);
    }
}

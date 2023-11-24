package interview;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;


@Slf4j
public class BlockQueue {

    private final static BlockingQueue<String> queue = new ArrayBlockingQueue<>(10);

    public static void main(String[] args) {
        java.util.function.Consumer<BlockingQueue<String>> consumer = queue -> {
            try {
                while (!queue.isEmpty()) {
                    log.warn("消费 " + queue.take());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        AtomicInteger apple = new AtomicInteger(0);
        Supplier<BlockingQueue> provider = () -> {
            try {
                log.warn("生产");
                queue.put("provider apple" + (apple.incrementAndGet()));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return queue;
        };
        provider.get();
        provider.get();
        consumer.accept(queue);
    }

}

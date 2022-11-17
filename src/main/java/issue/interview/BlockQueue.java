package issue.interview;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.function.Supplier;

@Slf4j
public class BlockQueue {

    private final static BlockingQueue<String> queue = new ArrayBlockingQueue<>(10);

    public static void main(String[] args) {
        java.util.function.Consumer<BlockingQueue> consumer = queue -> {
            try {
                log.warn("消费");
                System.out.println(queue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };


        Supplier<BlockingQueue> provider = () -> {
            try {
                log.warn("生产");
                queue.put(LocalDateTime.now().toString());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return queue;
        };

        BlockingQueue queue2 = provider.get();
        consumer.accept(queue2);

    }

}

package multiThread.threadpool;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class ThreadPoolExecutorTestTest {

    Task task1 = new Task("t1");
    Task task2 = new Task("ex");
    Task task3 = new Task("t3");

    /**
     * Test无法接受System.in流
     */
    @Test
    void singleThreadPollException() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(task1);
        executor.execute(task2);
        executor.execute(task3);
//        List<Task> collect = IntStream.range(1, 100).mapToObj(item -> new Task("task" + item)).collect(Collectors.toList());
//        collect.forEach(x -> executor.execute(x));
        Scanner scanner = new Scanner(System.in);
    }


    @Slf4j
    static class Task implements Runnable {
        private String name;

        public Task() {
        }

        public Task(String name) {
            this.name = name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            log.warn("current Nmae = " + this.name);
            if (name.equals("ex")) {
                throw new IllegalArgumentException(name);
            }
            try {
                TimeUnit.MILLISECONDS.sleep(500L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}
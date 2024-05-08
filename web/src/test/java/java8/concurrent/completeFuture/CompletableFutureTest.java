package java8.concurrent.completeFuture;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.util.StopWatch;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Slf4j
public class CompletableFutureTest {

    /**
     * 多任务并发执行，获取正确或错误结果
     */
    @Test
    void multiTaskRunAndReturn() {
        //记录开始时间
        Long start = System.currentTimeMillis();
        //任务
        final List<Integer> taskList = Arrays.asList(1, 2, 3, 4, 5);
        List<String> resultList = new ArrayList<>();
        Map<String, String> errorList = new HashMap<>();
        log.warn("start");
        Stream<CompletableFuture<String>> completableFutureStream = taskList.stream()
                .map(num -> {
                            return CompletableFuture
                                    .supplyAsync(() -> doubleInteger(num))
                                    .handle((res, th) -> {
                                        if (th == null) {
                                            log.info("任务" + num + "完成! result=" + res + ", " + LocalTime.now().toString());
                                            resultList.add(res.toString());
                                        } else {
                                            log.error("任务" + num + "异常! e=" + th + ", " +  LocalTime.now().toString());
                                            errorList.put(num.toString(), th.getMessage());
                                        }
                                        return "";
                                    });
                        }
                );
        CompletableFuture[] completableFutures = completableFutureStream.toArray(CompletableFuture[]::new);
        CompletableFuture.allOf(completableFutures)
                .whenComplete((v, th) -> {
                    log.warn("所有任务执行完成触发\n resultList=" + resultList + "\n errorList=" + errorList+ "\n耗时=" + (System.currentTimeMillis() - start));
                }).join();
        log.warn("end");

    }


    //根据数字判断线程休眠的时间
    public static Integer doubleInteger(Integer i) {
        try {
            log.warn("任务" + i + " 开始 ...");
            TimeUnit.SECONDS.sleep(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (i ==3) {
            throw new IllegalArgumentException("");
        }
        return 2 * i;
    }


    static class TT{
        private int cnt = 0;

        public int getCnt() {
            return cnt;
        }

        public void setCnt(int cnt) {
            this.cnt = cnt;
        }
    }


    /**
     * CompletableFuture c1 c2  -> all of
     * @throws InterruptedException
     */
    @Test
    public void test2() throws InterruptedException {
        TT tt = new TT();
        AtomicInteger ato = new AtomicInteger();
        List<Integer> collect = IntStream.range(0, 10000).mapToObj(Integer::new).collect(Collectors.toList());
        Supplier<List<Integer>> task = () -> {
            List<Integer> success = new ArrayList<>();
            collect.forEach(item -> {
                ato.getAndAdd(1);
                tt.setCnt(tt.getCnt() + 1);
                if (item%100==0) {
                    success.add(item);
                }
            });
            return success;
        };

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        final List<Integer> end = new ArrayList<>();
        Consumer consumer = (success) -> {
            List<Integer> list = (List<Integer>) success;
            log.warn("listSize=" + list.size());
            end.addAll(list);
        };
        try {
            CompletableFuture<List<Integer>> c1 = CompletableFuture.supplyAsync(task).thenAccept(consumer);
            CompletableFuture<List<Integer>> c2 = CompletableFuture.supplyAsync(task).thenAccept(consumer);
            CompletableFuture.allOf(c1, c2).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        stopWatch.stop();
        log.warn(stopWatch.prettyPrint());
        System.out.println("END=" + ato.get());
        System.out.println("TT = " + tt.getCnt());
        log.warn(end.stream().map(String::valueOf).collect(Collectors.joining(",")));
    }

}

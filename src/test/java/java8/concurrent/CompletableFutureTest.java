package java8.concurrent;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;
import java.util.stream.Stream;

@Slf4j
public class CompletableFutureTest {

    @Test
    void test1() {

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
                                    .supplyAsync(() -> {
                                                return doubleInteger(num);
                                            })
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
            log.warn("任务" + i);
            TimeUnit.SECONDS.sleep(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (i ==3) {
            throw new IllegalArgumentException("");
        }
        return 2 * i;
    }




}

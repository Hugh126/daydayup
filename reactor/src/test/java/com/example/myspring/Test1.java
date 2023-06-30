package com.example.myspring;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * 官方翻译文档
 * https://htmlpreview.github.io/?https://github.com/get-set/reactor-core/blob/master-zh/src/docs/index.html#mono
 *
 */
@Slf4j
public class Test1 {

    @Test
    public void test1() {
        Flux.range(1, 3).subscribe(System.out::println);
    }

    @Test
    public void test2() {
        String[] arr = {"foo", "bar", "foobar"};
        Flux<String> seq = Flux.just(arr);
//        Flux<String> seq = Flux.fromIterable(Arrays.asList(arr));
        seq.subscribe(System.out::println);
    }

    /**
     * 错误和完成信号都是终止信号，并且二者只会出现其中之一
     */
    @Test
    public void test3() {
        Flux.range(1,4).map(i -> {
            if (i!=3) return i;
            throw new IllegalArgumentException("Error input" + i);
        }).subscribe(System.out::println,
                error -> System.err.println(error)
                ,() -> {System.out.println("Done");});
    }

    /**
     * BaseSubscriber子类复写
     *
     * 建议你同时重写 hookOnError、hookOnCancel，以及 hookOnComplete 方法。 你最好也重写 hookFinally 方法
     *
     */
    @Test
    public void test4() {
        SampleSubscriber<Integer> ss = new SampleSubscriber<Integer>();
        Flux<Integer> ints = Flux.range(1, 4);
        ints.subscribe(i -> System.out.println("v_"+i),
                error -> System.err.println("Error " + error),
                () -> {System.out.println("Done");},
                s -> ss.request(10));
        ints.subscribe(ss);
    }


    @Test
    public void test5() {
        Flux.just("a1", "b2", "c3").map(String::toUpperCase)
                .subscribe(new BaseSubscriber<String>() {
                    @Override
                    protected void hookOnSubscribe(Subscription subscription) {
//                        super.hookOnSubscribe(subscription);
                        request(1);
                    }
                    @Override
                    protected void hookOnNext(String value) {
//                        super.hookOnNext(value);
                        System.out.println("[V]" + value);
                        request(1);
                    }
                });
    }


    @Test
    public void test6() {
        List<String> words = Arrays.asList(
                "the", "quick", "brown", "fox",
                "jumped", "over", "the", "lazy", "dog");
        Flux<String> flux = Flux.fromIterable(words);
        flux.flatMap(word -> Flux.fromArray(word.split("")))
                .concatWith(Mono.just("s")).distinct().sort()
                .zipWith(Flux.range(1, Integer.MAX_VALUE),
                        (string, count) ->
                                String.format("%2d. %s", count, string)
                )
                .subscribe(System.out::println);
    }


    @Test
    public void name() throws InterruptedException {
        Flux.interval(Duration.ofMillis(100L)).take(10).log().doOnComplete(()->{
            System.out.println("done");
        }).subscribe(System.out::println);
        TimeUnit.SECONDS.sleep(2L);
    }
}

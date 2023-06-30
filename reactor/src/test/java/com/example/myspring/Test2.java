package com.example.myspring;

import org.junit.Test;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Test2 {

    /**
     * 这是一种 同步地， 逐个地 产生值的方法
     */
    @Test
    public void generate() {
        Flux.generate(()-> 1, (state, sink) -> {
            sink.next("[Changed]" + state);
            if (state==5) {
                sink.complete();
            }
            return state+1;
        }, state -> {
            System.out.println("[V=]" + state);
        }).subscribe();
    }


    BaseEventProcessor myEventProcessor = new BaseEventProcessor();

    /**
     * create 方法的生成方式既可以是同步， 也可以是异步的
     * 可以将现有的 API 转为响应式，比如监听器的异步方法
     */
    @Test
    public void create() throws InterruptedException {
        Flux.create(sink -> {
            myEventProcessor.register(
                    new MyEventListener<String>() {
                        @Override
                        public void onDataChunk(List<String> chunk) {
                            for(String s : chunk) {
                                sink.next(s);
                            }
                        }

                        @Override
                        public void processComplete() {
                            sink.complete();
                            myEventProcessor.shutdown();
                        }

                        @Override
                        public void processError(Throwable e) {
                            sink.error(e);
                            myEventProcessor.shutdown();
                        }
                    });
        }).log().subscribe();
        myEventProcessor.fireEvents(IntStream.range(1,20).mapToObj(t -> "Obj" + t).toArray());
        System.out.println("main thread exit");
        if (myEventProcessor.checkDone()) {
            System.out.println("TASK DONE.");
        }
    }

    /**
     * create 的一个变体是 push，适合生成事件流
     * @throws InterruptedException
     */
    @Test
    public void push() throws InterruptedException {
        Flux.push(sink -> {
            myEventProcessor.register(
                    new MyEventListener<String>() {
                        @Override
                        public void onDataChunk(List<String> chunk) {
                            for(String s : chunk) {
                                sink.next(s);
                                if (s.contains("19")) {
                                    throw new IllegalArgumentException("Input= " + s);
                                }
                            }
                        }

                        @Override
                        public void processComplete() {
                            sink.complete();
                            myEventProcessor.shutdown();
                        }

                        @Override
                        public void processError(Throwable e) {
                            sink.error(e);
                        }
                    });
        }).log().subscribe();
        myEventProcessor.fireEvents(IntStream.range(1,20).mapToObj(t -> "Obj" + t).toArray());
        System.out.println("main thread exit");
        if (myEventProcessor.checkDone()) {
            System.out.println("TASK DONE.");
        }
    }
}

package com.example.myspring;

import java.util.Arrays;
import java.util.concurrent.*;

public class BaseEventProcessor<T> implements MyEventProcessor<T> {

    private MyEventListener myEventListener;
    private ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    @Override
    public void register(MyEventListener eventListener) {
        this.myEventListener = eventListener;
    }

    @Override
    public void fireEvents(T... events) {
        executorService.schedule(()-> {
            myEventListener.onDataChunk(Arrays.asList(events));
            myEventListener.processComplete();
        }, 500L, TimeUnit.MILLISECONDS);
    }


    @Override
    public void shutdown() {
        executorService.shutdown();
    }

    public boolean checkDone() throws InterruptedException {
       while (!this.executorService.awaitTermination(200L, TimeUnit.MILLISECONDS)) {
           System.out.println("...");
       }
        return this.executorService.isShutdown();
    }
}

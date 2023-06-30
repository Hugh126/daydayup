package com.example.myspring;

public interface MyEventProcessor<T> {

    void register(MyEventListener eventListener);

    void fireEvents(T... events);

    void shutdown();

}

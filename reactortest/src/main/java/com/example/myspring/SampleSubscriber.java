package com.example.myspring;

import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;

public class SampleSubscriber<T> extends BaseSubscriber<T> {

    @Override
    public void hookOnSubscribe(Subscription subscription) {
        System.out.println("Subscribed");
        request(1);
    }

    @Override
    public void hookOnNext(T value) {
        System.out.println("hookOnNext" + value);
        request(1);
    }

}

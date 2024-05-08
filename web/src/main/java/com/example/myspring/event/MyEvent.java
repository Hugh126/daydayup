package com.example.myspring.event;

import org.springframework.context.ApplicationEvent;

public class MyEvent extends ApplicationEvent {
    private String name;

    public MyEvent(Object source, String name) {
        super(source);
        this.name = name;
    }

}
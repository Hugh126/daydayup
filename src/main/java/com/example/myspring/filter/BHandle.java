package com.example.myspring.filter;

import org.springframework.stereotype.Component;

@Component
public class BHandle implements AbstractHandle<String>{

    @Override
    public String handle(String s) {
        return String.format("[%s]", this.getClass().getSimpleName()) + s;
    }
}

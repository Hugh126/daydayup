package com.example.myspring.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AHandle implements AbstractHandle<String>{

    @Override
    public String handle(String s) {
        return String.format("[%s]", this.getClass().getSimpleName()) + s;
    }
}

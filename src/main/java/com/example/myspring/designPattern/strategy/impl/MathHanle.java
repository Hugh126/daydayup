package com.example.myspring.designPattern.strategy.impl;

import com.example.myspring.designPattern.strategy.StudyEnum;
import org.springframework.stereotype.Component;

@Component
public class MathHanle extends AbstractHandle {
    @Override
    public StudyEnum getType() {
        return StudyEnum.mathematics;
    }
}

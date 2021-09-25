package com.example.myspring.designPattern.strategy.entity;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class StuEntity {

    public static String SCHOOL_NAME = "SN";

    private int num;
    private String name;

}

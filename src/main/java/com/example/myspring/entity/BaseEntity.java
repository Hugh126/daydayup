package com.example.myspring.entity;

public  abstract class BaseEntity {


    public String getTaskName(){
        return this.getClass().getName();
    }

    public void ttt(){
        System.out.println("test-" +getTaskName());
    }
}

package com.example.myspring;

import cn.hutool.http.HttpUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class TestControllerTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void concurrent3Threads() {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        while (true){
            executorService.execute(()-> {
                System.out.println("req thread=" + Thread.currentThread().getName() + " resp= " + HttpUtil.get("http://localhost:8001/foo"));
            });
        }
    }





}
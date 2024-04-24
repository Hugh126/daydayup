package com.example.myspring;

import cn.hutool.http.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;



@RestController
public class TestController {

    @Autowired
    private CommonService service;

    @RequestMapping("/hello1")
    public String hello1() {
        service.commonService1();
        return "[hello1]";
    }

    @RequestMapping("/hello2")
    public String hello2() {
        service.commonService1();
        return "[hello2]";
    }

    public void  fallBackHandle() {
        System.out.println("--fallBackHandle--");
    }



    /**
     * 如果没有流量访问，客户端不会执行初始化，sentinel控制台上就看不到该节点
     * 先手动访问一次接口
     */
    @RequestMapping("/foo")
    public String foo() {
        return "[foo]";
    }

    /**
     * 测试控制台新增流控规则
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {


        ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
        service.scheduleAtFixedRate(() -> {
            new Thread(() -> {
                IntStream.range(1,100).forEach(i -> {
                    System.out.println(LocalTime.now().toString()+" || req index=" + i + "req=hello1, " + " resp= " + HttpUtil.get("http://localhost:8001/hello1"));
                });
            }).start();

        }, 0, 1, TimeUnit.SECONDS);


//        service.awaitTermination(3L,TimeUnit.SECONDS);
//        service.shutdown();
    }

}

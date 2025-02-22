package com.example.myspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
@RestController
public class ServerApp {

    private final ServletWebServerApplicationContext context;

    public ServerApp(ServletWebServerApplicationContext context) {
        this.context = context;
    }

    public static void main(String[] args) {
        SpringApplication.run(ServerApp.class, args);
    }

    /**
     * 为客户端提供可调用的接口
     */
    @RequestMapping("/call/{name}")
    public String call(@PathVariable String name) {
        // 测试超时
        try {
            TimeUnit.SECONDS.sleep(5L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String msg = LocalDateTime.now().toString() + "[Provider]" + context.getWebServer().getPort() + " 客户端请求参数 : " + name;
        System.out.println(msg);
        return  msg;
    }



}
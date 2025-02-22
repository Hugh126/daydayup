package com.example.myspring.controller;


import com.example.myspring.service.FeignClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConsumerController {

    @Autowired
    private FeignClientService providerClient; // 加载 openfeign client
    
    @GetMapping("/consumer")
    public String consumer(@RequestParam String name) {
        // 向调用本地方法一样，调用 openfeign client 中的方法
        return providerClient.call(name);
    }

}
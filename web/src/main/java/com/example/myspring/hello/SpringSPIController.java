package com.example.myspring.hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.wuhan.hugh.LearnCourse;

/**
 *
 * spring SPI 简易实现
 *
 * 1、依赖自定义mystarter
 * 2、在mystarter中，利用spring SPI ，
 *     1) 读取当前类配置
 *     2）注入LearnCourse的实现
 *
 * https://www.jianshu.com/p/6aa2da5f65d1
 *
 */
@RequestMapping("/test")
@RestController
public class SpringSPIController {

    @Autowired
    private LearnCourse learnCourse;

    @RequestMapping("/hi")
    String sayHi(@RequestParam("name") String name) {
        learnCourse.sayHi(name);
        return "Done";
    }

}

package com.example.myspring.hello;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/hello")
public class HelloController implements DisposableBean {

    /**
     * 使用PathVariable 接受参数
     */
    @RequestMapping("/{name}")
    String sayHello(@PathVariable("name") String name) {
        return "Hello  " + name;
    }

    /**
     * 使用RequestParam获取参数
     */
    @RequestMapping("/hehe")
    String sayHello2(@RequestParam("name") String name) {
        return "hehe  " + name;
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("---------DisposableBean destroy-----");
    }


}

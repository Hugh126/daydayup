package com.example.myspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
public class App 
{
    @RequestMapping("/hi")
    String home() {
        return "Hi World!";
    }

    /**
     * 使用PathVariable 接受参数
     * @param userName
     * @return
     */
    @RequestMapping("/hello/{username}")
    String sayHello(@PathVariable("username") String userName) {
        return "Hello  " + userName;
    }

    /**
     * 使用RequestParam获取参数
     * 对于URL请求方式为http://localhost:8080/hehe?username=qq
     * @param userName
     * @return
     */
    @RequestMapping("/hehe")
    String sayHello2(@RequestParam("username") String userName) {
        return "hehe  " + userName;
    }

    public static void main( String[] args )
    {
        SpringApplication.run(App.class, args);
    }
}

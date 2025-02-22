package com.example.myspring.service;

import feign.Contract;
import feign.Logger;
import feign.auth.BasicAuthRequestInterceptor;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyFeignClientsConfiguration  {

    /***
     *     NONE, 没日志（默认）。
     *
     *     BASIC, 只记录请求方法和URL以及响应状态代码和执行时间。
     *
     *     HEADERS, 记录基本信息以及请求和响应头。
     *
     *     FULL, 记录请求和响应的header、正文和元数据。
     *
     * @return
     */
    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }


//    @Bean
//    public BasicAuthRequestInterceptor basicAuthRequestInterceptor() {
//        return new BasicAuthRequestInterceptor("user", "password");
//    }

}

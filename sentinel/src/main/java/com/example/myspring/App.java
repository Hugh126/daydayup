package com.example.myspring;

import com.alibaba.csp.sentinel.adapter.servlet.CommonFilter;
import com.alibaba.csp.sentinel.annotation.aspectj.SentinelResourceAspect;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;


/**
 * 自己选择dependency有一堆，直接依赖
 * SpringCloud版本号有较大差异，不熟悉的参考： https://zhuanlan.zhihu.com/p/643508351
 *
 * 如果偷懒，想直接体验改造后dashboard @link{https://github.com/Hugh126/Sentinel/tree/Branch_1.8.6}
 *
 *
 * 推荐直接使用 https://start.aliyun.com/ 新建项目，避免版本号踩坑
 */
@SpringBootApplication
public class App 
{
    public static void main( String[] args )
    {
        SpringApplication.run(App.class, args);
    }

    @Bean
    public FilterRegistrationBean sentinelFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new CommonFilter());
        registration.addUrlPatterns("/*");
        // 入口资源关闭聚合
        registration.addInitParameter(CommonFilter.WEB_CONTEXT_UNIFY, "false");
        registration.setName("sentinelFilter");
        registration.setOrder(1);
        return registration;
    }

}

package com.example.myspring;

import cn.hutool.core.lang.Assert;
import com.example.myspring.extend.SpringContextUtils;
import common.ExtendConfigTest;
import multiThread.threadpool.ThreadPoolExecutorTest;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

@Import(ThreadPoolExecutorTest.class)
@SpringBootApplication
@MapperScan("com.example.myspring.service")
@EnableScheduling
public class App 
{
    public static void main( String[] args )
    {
        SpringApplication.run(App.class, args);
        // SPI引入IOC
        Assert.notNull(SpringContextUtils.getBean(ExtendConfigTest.class));
        // @Import 导入IOC
        Assert.notNull(SpringContextUtils.getBean(ThreadPoolExecutorTest.class));

    }

}

package com.example.myspring;


import com.example.myspring.extend.SpringContextUtils;
import common.ExtendConfigTest;
import lombok.extern.slf4j.Slf4j;
import multiThread.threadpool.ThreadPoolExecutorTest;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.util.Assert;

@Slf4j
@Import(ThreadPoolExecutorTest.class)
@MapperScan("com.example.myspring.service")
@EnableScheduling
@SpringBootApplication
public class DayDayStart
{
    public static void main( String[] args )
    {
        SpringApplication.run(DayDayStart.class, args);
        // SPI引入IOC
        Assert.notNull(SpringContextUtils.getBean(ExtendConfigTest.class));
        // @Import 导入IOC
        Assert.notNull(SpringContextUtils.getBean(ThreadPoolExecutorTest.class));

    }

}



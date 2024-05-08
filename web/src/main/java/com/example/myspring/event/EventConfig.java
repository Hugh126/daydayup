package com.example.myspring.event;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class EventConfig {
    @Bean
    SimpleApplicationEventMulticaster applicationEventMulticaster() {
        SimpleApplicationEventMulticaster multicaster = new SimpleApplicationEventMulticaster();
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //核心线程池数量
        executor.setCorePoolSize(Runtime.getRuntime().availableProcessors());
        //最大线程数量
        executor.setMaxPoolSize(Runtime.getRuntime().availableProcessors() * 5);
        //线程池的队列容量
        executor.setQueueCapacity(Runtime.getRuntime().availableProcessors() * 2);
        //线程名称的前缀
        executor.setThreadNamePrefix("springEvent-executor-");
        executor.initialize();
        multicaster.setTaskExecutor(executor);
        return multicaster;
    }
}

package com.example.myspring.extend;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * 打开方式123
 *
 * 在启动类中用springApplication.addInitializers(new TestApplicationContextInitializer())语句加入
 *
 * 配置文件配置context.initializer.classes=com.example.demo.TestApplicationContextInitializer
 *
 * Spring SPI扩展，在spring.factories中加入org.springframework.context.ApplicationContextInitializer=com.example.demo.TestApplicationContextInitializer
 *
 */

@Slf4j
public class MySpringContextInit implements ApplicationContextInitializer {
    @Override
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
      log.warn("此时spring还未初始化，这里我强势插入!");
    }
}

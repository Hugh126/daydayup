package com.example.myspring.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;


/**
 * 事件监听
 *
 * 调用 ==>
 * @see com.example.myspring.DayDayStart
 */
@Component
@Slf4j
public class MyEventListener implements ApplicationListener<MyEvent> {
    @Override
    public void onApplicationEvent(MyEvent event) {
        log.warn("myEvent={}",event);
    }
}
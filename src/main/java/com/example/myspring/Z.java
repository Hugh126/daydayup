package com.example.myspring;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @Description
 * @Date 2023/10/7 19:37
 * @Created by hugh
 */
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class Z {

    @Scheduled(cron = "0/10 * * * * ?")
    public void run() {
        System.out.println("----zzz---");
    }
}

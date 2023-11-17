//package com.example.myspring;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import java.util.Random;
//
///**
// * @Description
// * @Date 2023/10/7 19:37
// * @Created by hugh
// */
//@Component
//@Slf4j
//public class Z {
//
//    @Scheduled(cron = "0/90 * * * * ?")
//    public void run() {
//      log.warn("--this Class--{}", this.getClass().getName());
//        Random r = new Random();
//        int rx = r.nextInt(10);
//        if (rx %3 == 0) {
//            log.warn("Error this is 333 = {}", rx);
//        }
//
//    }
//}

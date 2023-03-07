package com.example.myspring.hello;

/**
 * JDK和SpringSPI
 * https://juejin.cn/post/6844903890173837326
 *
 */

import java.util.ServiceLoader;

public class JDKSPI {

    public static void main(String[] args) {
        ServiceLoader<common.JDKSPI> spiList = ServiceLoader.load(common.JDKSPI.class);
        spiList.forEach(item -> item.test());
    }
}

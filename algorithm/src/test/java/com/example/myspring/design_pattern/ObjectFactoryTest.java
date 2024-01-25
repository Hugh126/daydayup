package com.example.myspring.design_pattern;


import com.example.myspring.entity.Stu;
import design_pattern.factory.ObjectFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description
 * @Date 2023/9/1 16:32
 * @Created by hugh
 */

@Slf4j
class ObjectFactoryTest {

    static class OFI implements ObjectFactory<String, Stu>{
        private Map<String, Stu> container = new HashMap<>();

        private Stu doCreate(String name){
            log.warn("[do create]");
            Stu city = new Stu();
            city.setName(name);
            return city;
        }

        @Override
        public Stu getObject(String s) {
            return container.computeIfAbsent(s, k -> doCreate(s));
        }
    }


    @Test
    public void test1() {
        OFI ofi = new OFI();
        System.out.println(ofi.getObject("a"));
        System.out.println(ofi.getObject("b"));
        System.out.println(ofi.getObject("a"));
    }
}
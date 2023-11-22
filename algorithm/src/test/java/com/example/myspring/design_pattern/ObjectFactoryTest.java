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

    private Map<String, Stu> container = new HashMap<>();

    private Stu getOrCreate(String name, ObjectFactory<Stu> factory) {
        return container.computeIfAbsent(name, k -> doCreate(k));
    }

    private Stu doCreate(String name){
        log.warn("[do create]");
        Stu city = new Stu();
        city.setName(name);
        return city;
    }

    @Test
    public void test1() {
        String aName = "a";
        System.out.println(getOrCreate(aName, () -> doCreate(aName)).hashCode());
        System.out.println(getOrCreate(aName, () -> doCreate(aName)).hashCode());
    }
}
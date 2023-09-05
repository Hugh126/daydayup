package com.example.myspring.designPattern.factory;

import com.example.myspring.entity.ErpCity;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Description
 * @Date 2023/9/1 16:32
 * @Created by hugh
 */

@Slf4j
class ObjectFactoryTest {

    private Map<String, ErpCity> container = new HashMap<>();

    private ErpCity getOrCreate(String name, ObjectFactory<ErpCity> factory) {
        return container.computeIfAbsent(name, k -> doCreate(k));
    }

    private ErpCity doCreate(String name){
        log.warn("[do create]");
        ErpCity city = new ErpCity();
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
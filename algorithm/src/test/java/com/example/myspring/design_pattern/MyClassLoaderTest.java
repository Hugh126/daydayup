package com.example.myspring.design_pattern;

import cn.hutool.core.lang.Assert;
import cn.hutool.json.JSONUtil;
import com.example.myspring.entity.Stu;
import org.junit.jupiter.api.Test;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.util.stream.Stream;

class MyClassLoaderTest {


    @Test
    void classForName() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Stu stu = (Stu) Class.forName("com.example.myspring.entity.Stu").newInstance();
        System.out.println(stu.getName());
    }

    @Test
    void classForNameWithLoader() throws ClassNotFoundException {
        Class<?> stuClass = Class.forName("com.example.myspring.entity.Stu", true, ClassLoader.getSystemClassLoader());
        Assert.isTrue(stuClass.equals(Stu.class));
    }

    private void foo() {
        String[] objects = Stream.of("a", "b").toArray(String[]::new);
        System.out.println(objects);
    }


    @Test
    void test3() {
        MemoryMXBean memorymbean = ManagementFactory.getMemoryMXBean();
        MemoryUsage usage = memorymbean.getHeapMemoryUsage();
        MemoryUsage nonHeapMemoryUsage = memorymbean.getNonHeapMemoryUsage();
        System.out.println(JSONUtil.toJsonPrettyStr(usage));
    }
}
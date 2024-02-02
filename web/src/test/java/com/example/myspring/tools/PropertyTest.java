package com.example.myspring.tools;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.json.JSONUtil;
import com.google.common.collect.ImmutableMap;
import lombok.Data;
import org.junit.Test;
import org.springframework.beans.BeanUtils;

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Objects;

public class PropertyTest {

    @Data
    public static class TT{
        private Integer num;
        private String secondName;
    }

    @Test
    public void test1() {
        TT tt = BeanUtil.mapToBean(ImmutableMap.of("num",111,"secondName", "aaa"), TT.class, true);
        System.out.println(JSONUtil.toJsonPrettyStr(tt));
    }

    @Test
    public void test2() {
        TT tt = ReflectUtil.newInstance(TT.class);
        ImmutableMap<String, ? extends Serializable> map = ImmutableMap.of("num", 111, "secondName", "aaa");
        for (Field field : ReflectUtil.getFields(TT.class)) {
            ReflectUtil.setFieldValue(tt, field, map.get(field.getName()));
        }
        System.out.println(JSONUtil.toJsonPrettyStr(tt));
    }
}

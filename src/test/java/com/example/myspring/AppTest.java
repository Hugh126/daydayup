package com.example.myspring;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    class GetSum{
        Integer sum = 0;
        List<Integer> x;

        public GetSum(List<Integer> x) {
            this.x = x;
        }
    }


    /**
     * lambal表达式Test
     */
    @Test
    public void printAny() {

        List<Integer> ori = new ArrayList(){{
            add(11);
            add(33);
            add(22);
            add(98);
            add(60);
            add(69);
            add(77);
            add(88);
            add(33);
            add(28);
        }};

        List temp = ori.subList(0, 2);
        List temp2 = ori.subList(2, 10);
        temp.forEach(t -> System.out.println(t));
        System.out.println("---");
        temp2.forEach(t -> System.out.println(t));
    }


    public static final int xx = 0;
    public static final Integer xxInt = 0;
    public static final Object o1 = new Object();

    /**
     * final 修饰变量：
     * 1. 对基础类型及其封装类型，值不可被更改
     * 2. 对于引用类型，引用指向对象不可被更改
     */
    private void foo() {
//        xx = 1;
//        xxInt = 1;
        Object o2 = new Object();
//        o1 = o2;
    }
}

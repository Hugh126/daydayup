package com.example.myspring.designPattern.proxy;

/**
 * @author hugh
 * @Title: CalcSimpleImpl
 * @ProjectName springboottest
 * @Description: TODO
 * @date 2018/10/2615:21
 */
public class CalcSimpleImpl implements Calc {
    @Override
    public int add(int a, int b) {
        return a + b;
    }
}

package com.example.myspring.designPattern.proxy;

/**
 * @author hugh
 * @Title: StaticProxyDemo
 * @ProjectName springboottest
 * @Description: 静态代理
 * @date 2018/10/2615:19
 */
public class StaticProxyDemo  implements Calc{

    private Calc calc = new CalcSimpleImpl();

    public StaticProxyDemo(Calc calc) {
        this.calc = calc;
    }

    private void doBefore() {};

    private void doAfter() {};

    @Override
    public int add(int a, int b) {

        doBefore();

        int result = calc.add(a, b);

        doAfter();

        return result;

    }

    /**
     * 静态代理的使用
     * @param args
     */
    public static void main(String[] args) {
        Calc calcImpl = new CalcSimpleImpl();

        Calc calc = new StaticProxyDemo(calcImpl);

        System.out.println("StaticProxyDemo " + calc.add(1, 2));
    }
}

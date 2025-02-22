package design_pattern.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author hugh
 * @Title: DynamicProxyDemo
 * @ProjectName springboottest
 * @Description: 动态代理模式使用
 * @date 2018/10/2615:24
 */
public class DynamicProxyDemo implements InvocationHandler {

    Object obj;

    public DynamicProxyDemo(Object obj) {
        this.obj = obj;
    }

    private  void doBefore() {
        System.out.println("--doBefore--");
    };

    private  void doAfter() {
        System.out.println("--doAfter--");
    };

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        doBefore();

        System.out.println("--invoke--");
        Object dynamicObject = method.invoke(obj, args);

        doAfter();

        return dynamicObject;
    }

    /**
     * 动态代理的使用
     * @param args
     */
    public static void main(String[] args) {
        Calc calcImpl = new CalcSimpleImpl();
        DynamicProxyDemo dynamicProxyDemo = new DynamicProxyDemo(calcImpl);

        Calc proxy = (Calc) Proxy.newProxyInstance(calcImpl.getClass().getClassLoader(), calcImpl.getClass().getInterfaces(), dynamicProxyDemo);

        System.out.println("result = " + proxy.add(1, 2));

    }

}



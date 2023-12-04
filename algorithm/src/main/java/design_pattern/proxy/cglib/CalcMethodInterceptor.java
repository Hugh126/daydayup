package design_pattern.proxy.cglib;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @Description
 * @Date 2023/12/4 14:58
 * @Created by hugh
 */
public class CalcMethodInterceptor implements MethodInterceptor {
    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        doBefore();
        Object result = methodProxy.invokeSuper(obj, args);
        doAfter();
        return result;
    }

    private  void doBefore() {
        System.out.println("--doBefore--");
    };

    private  void doAfter() {
        System.out.println("--doAfter--");
    };
}

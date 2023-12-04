package design_pattern.proxy;

import design_pattern.proxy.cglib.CalcMethodInterceptor;
import net.sf.cglib.proxy.Enhancer;


public class CGDynamicProxyDemo {


     public Object getProxy(Class<?> clazz){
          Enhancer enhancer = new Enhancer();
          // 设置类加载
          enhancer.setClassLoader(clazz.getClassLoader());
          // 设置被代理类
          enhancer.setSuperclass(clazz);
          // 设置方法拦截器
          enhancer.setCallback(new CalcMethodInterceptor());
          // 创建代理类
          Object proxyImpl = enhancer.create();

          return proxyImpl;
     }

     public static void main(String[] args) {
          CGDynamicProxyDemo cg = new CGDynamicProxyDemo();
          /**
           * 生成目标类的子类，拦截目标对象的执行方法来实现代理
           * 如果传入是接口，那么`NoSuchMethodError`
           */
          Calc proxyImpl = (CalcSimpleImpl) cg.getProxy(CalcSimpleImpl.class);
          int result = proxyImpl.add(1, 2);
//          proxyImpl.sayHi();
          System.out.println("result= " + result);
     }



}

package design_pattern.factory;

/**
 * @Description
 * @Date 2023/9/1 16:28
 * @Created by hugh
 *
 * 学习 getSingleton(String beanName, ObjectFactory<?> singletonFactory)
 */

@FunctionalInterface
public interface ObjectFactory<T, R> {

    R getObject(T t);
}

package annotation;

import java.lang.reflect.Method;

/**
 * @author hugh
 * @Title: Child
 * @ProjectName springboottest
 * @Description: TODO
 * @date 2018/10/1411:32
 */

@Desc(description = "hello child")
public class Child implements Person{


    @Override
    @PersonAnno(name="Tom")
    public String getName() {
        return null;
    }

    @Override
    @PersonAnno(name = "", age = 10)
    public int getAge() {
        return 0;
    }

    public static void main(String[] args) {
        /**
         * 解析注解
         * - 类上的注解
         * - 方法上的注解
         */
        try {
            // 1. 使用类加载器加载类
            Class childClass = Class.forName("annotation.Child");
            // 2. 找到类上面的注解
            boolean isDescExist = childClass.isAnnotationPresent(Desc.class);
            if (isDescExist) {
                // 3. 拿到注解实例
                Desc desc = (Desc)childClass.getAnnotation(Desc.class);
                System.out.println(desc.description());
            }

            Method[] methods = childClass.getMethods();
            for (Method method : methods) {
                if (method.isAnnotationPresent(PersonAnno.class)) {
                    PersonAnno personAnno = method.getAnnotation(PersonAnno.class);
                    System.out.println(personAnno.name() + " | " + personAnno.age());
                }
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }


}

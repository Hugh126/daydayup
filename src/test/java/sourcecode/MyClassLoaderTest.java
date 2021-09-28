package sourcecode;

import cn.hutool.core.lang.Assert;
import com.example.myspring.entity.Stu;
import org.junit.jupiter.api.Test;

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

}
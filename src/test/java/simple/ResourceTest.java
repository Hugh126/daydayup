package simple;

import org.junit.Test;
import util.ExceptionHolder;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.concurrent.TimeUnit;

import static util.ExceptionHolder.getExceptionInfo;

public class ResourceTest {

    @Test
    public void test2() throws IOException {
        String name = "org/springframework/util/ConcurrentReferenceHashMap.class";
        Enumeration<URL> resources = Thread.currentThread().getContextClassLoader().getResources(name);
        while (resources.hasMoreElements()) {
            URL url = resources.nextElement();
            System.out.println(url.toString());
        }

        String name2 = "META-INF/spring.factories";
        Enumeration<URL> resources2 = Thread.currentThread().getContextClassLoader().getResources(name2);
        while (resources2.hasMoreElements()) {
            URL url = resources2.nextElement();
            System.out.println(url.toString());
        }
    }

    void foo(Integer a) {
        try {
            System.out.println(1/a);
        }catch (Exception e){
            ExceptionHolder.setExceptionInfo(e);
        }
    }

    @Test
    public void test1() throws Exception {

    }
}

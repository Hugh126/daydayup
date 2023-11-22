package java8;

import org.junit.Test;
import org.openjdk.jol.info.ClassLayout;

public class ObjectTest {

    @Test
    public void test1() {
        Object obj = new Object();
        System.out.println(ClassLayout.parseInstance(obj).toPrintable());
    }


}

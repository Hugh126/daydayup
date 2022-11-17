package java8.concurrent.function;

import org.junit.Test;

import java.util.function.Supplier;

public class SupplierTest implements Supplier {
    @Override
    public Object get() {
        System.out.println("--get--");
        return 123;
    }

    @Test
    public void test1() {
        System.out.println(new SupplierTest().get());
    }

}

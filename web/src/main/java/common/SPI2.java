package common;

public class SPI2 implements JDKSPI{
    @Override
    public void test() {
        System.out.println("here is = " + this.getClass().getSimpleName());
    }
}

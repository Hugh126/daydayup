package multiThread.concurrent;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 2. 使用AtomicInteger
 */
class AutoCounter2 {

    private AtomicInteger atomicInteger = new AtomicInteger();

    public int increase() {
        return atomicInteger.incrementAndGet();
    }

    public int decrease() {
        return atomicInteger.decrementAndGet();
    }

    public static void main(String[] args) {
        AutoCounter2 counter2 = new AutoCounter2();

        System.out.println(counter2.atomicInteger.intValue());
    }
}
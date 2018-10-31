package multiThread.concurrent;

/**
 * 实现方式
 * 1. 使用sync关键字
 */
public class AutoCounter1 {


        private int counter = 0;

        public int increase() {
            synchronized (this) {
                counter = counter + 1;
                return counter;
            }
        }

        public int decrease() {
            synchronized (this) {
                counter = counter - 1;
                return counter;
            }
        }


}

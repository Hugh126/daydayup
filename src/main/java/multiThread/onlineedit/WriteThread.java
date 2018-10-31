package multiThread.onlineedit;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author hugh
 * @Title: WriteThread
 * @ProjectName springboottest
 * @Description: TODO
 * @date 2018/10/3113:17
 */
public class WriteThread implements Runnable {

    private TextData textData;

    private String appendStr;

    public WriteThread(TextData textData, String appendStr) {
        this.textData = textData;
        this.appendStr = appendStr;
    }

    @Override
    public void run() {
//        sleep();
        textData.write(appendStr);
        System.out.println(Thread.currentThread().getName() + " |write| " + appendStr);
    }

    private void sleep() {
        try {
            TimeUnit.MICROSECONDS.sleep((long) (Math.random()*500));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

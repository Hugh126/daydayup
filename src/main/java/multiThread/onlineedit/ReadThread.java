package multiThread.onlineedit;

import java.util.concurrent.TimeUnit;

/**
 * @author hugh
 * @Title: ReadThread
 * @ProjectName springboottest
 * @Description: TODO
 * @date 2018/10/3113:12
 */
public class ReadThread implements Runnable {

    private TextData textData;

    public ReadThread(TextData textData) {
        this.textData = textData;
    }

    @Override
    public void run() {
//        sleep();
        System.out.println(Thread.currentThread().getName() + " |read| " + textData.read());
    }

    private void sleep() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

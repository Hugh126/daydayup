package multiThread.delaytask;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @author hugh
 */
@Slf4j
public class DelayElement implements Delayed {

    private String name;
    private Integer delayTime;

    private long start = 0L;

    public DelayElement(String name, Integer delayTime) {
        this.name = name;
        this.delayTime = delayTime;
        this.start = System.currentTimeMillis();
    }


    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(start + this.delayTime*1000 - System.currentTimeMillis(), TimeUnit.MICROSECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        return (int) (this.getDelay(TimeUnit.MICROSECONDS) - o.getDelay(TimeUnit.MICROSECONDS));
    }


    public void run() {
        log.warn("name='" + name + " run finished.");
    }


}

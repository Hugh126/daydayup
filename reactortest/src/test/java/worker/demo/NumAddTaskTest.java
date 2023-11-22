package worker.demo;

import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;
import worker.Master;

import java.util.concurrent.atomic.AtomicLong;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Description
 * @Date 2023/11/22 15:53
 * @Created by hugh
 */
class NumAddTaskTest {

    @Test
    public void test() throws InterruptedException {
        AtomicLong totalCount = new AtomicLong(0);
        Master<NumAddTask, Long> master = new Master<>(3);
        master.submit(new NumAddTask(1, 3000), totalCount::addAndGet);
        master.submit(new NumAddTask(3001, 5000), totalCount::addAndGet);
        master.submit(new NumAddTask(5001, 7000), totalCount::addAndGet);
        master.submit(new NumAddTask(7001, 10000), totalCount::addAndGet);
        // 防止主线程退出
        Thread.sleep(3000);
        System.out.println("total Count = " + totalCount);
        Assert.isTrue(totalCount.get() == 10001*10000L/2, "计算错误");
    }


}
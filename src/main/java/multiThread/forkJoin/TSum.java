package multiThread.forkJoin;

import lombok.Data;

import java.util.StringJoiner;
import java.util.concurrent.atomic.AtomicInteger;

@Data
public class TSum {
    private AtomicInteger cnt0 = new AtomicInteger(0);
    private AtomicInteger cnt1 = new AtomicInteger(0);
    private AtomicInteger cnt2 = new AtomicInteger(0);

    @Override
    public String toString() {
        return new StringJoiner(", ", TSum.class.getSimpleName() + "[", "]")
                .add("cnt0=" + cnt0.get())
                .add("cnt1=" + cnt1.get())
                .add("cnt2=" + cnt2.get())
                .toString();
    }
}

package worker.demo;

import worker.Task;

public class NumAddTask extends Task<Long> {

    private final long begin;
    private final long end;

    public NumAddTask(long begin, long end) {
        this.begin = begin;
        this.end = end;
    }

    @Override
    protected Long exec() {
        long res = 0;
        // 每个线程累加10万
        for (long i = begin; i <= end; i++) {
            res += i;
        }
        return res;
    }
}

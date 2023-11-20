package worker;

import lombok.Setter;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public abstract class Task<R> {

    private static final AtomicInteger WORKER_ID_GENERATOR = new AtomicInteger(0);

    /**
     * 任务id
     */
    private final int taskId;

    /**
     * 执行任务的work线程Id
     */
    @Setter
    private int workerId;

    /**
     * 任务执行结果
     */
    private R result;

    /**
     * 任务执行完后，对结果的回调
     */
    private Consumer<R> resultAction;

    public Task() {
        this.taskId = WORKER_ID_GENERATOR.getAndIncrement();
    }

    /**
     * 执行任务，执行完任务后并进行回调
     */
    void execute() {
        this.result = this.exec();
        if (resultAction != null) {
            resultAction.accept(this.result);
        }
        System.out.println(toString());
    }

    /**
     * 执行任务
     *
     * @return R
     */
    protected abstract R exec();

    @Override
    public String toString() {
        return "Task{" +
                "taskId=" + taskId +
                ", workerId=" + workerId +
                ", result=" + result +
                '}';
    }

    void setResultAction(Consumer<R> resultAction) {
        this.resultAction = resultAction;
    }
}

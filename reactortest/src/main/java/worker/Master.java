package worker;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.function.Consumer;

public class Master<T extends Task<R>, R> {

    private final Map<String, Worker<R>> workerMap = new HashMap<>();

    private final ArrayBlockingQueue<Task<R>> taskQueue = new ArrayBlockingQueue<>(1024);

    private Thread masterThread = null;

    public Master(int workCount) {
        for (int i = 0; i < workCount; i++) {
            Worker<R> worker = new Worker<>();
            workerMap.put(String.format("%s-%s", "worker", i), worker);
        }
        masterThread = new Thread(this::execute);
        masterThread.start();
    }

    private void execute() {
        while (true) {
            workerMap.forEach((workerName, worker) -> {
                try {
                    Task<R> task = this.taskQueue.take();
                    worker.submit(task);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public void submit(T task, Consumer<R> resultAction) {
        task.setResultAction(resultAction);
        taskQueue.add(task);
    }
}

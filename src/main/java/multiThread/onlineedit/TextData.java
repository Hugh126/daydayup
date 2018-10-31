package multiThread.onlineedit;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author hugh
 * @Title: TextData
 * @ProjectName springboottest
 * @Description: TODO
 * @date 2018/10/3112:51
 */
public class TextData {

    private ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    private Lock readLock = readWriteLock.readLock();

    private Lock writeLock = readWriteLock.writeLock();

    private final char[] buffer;

    private int size;

    private final int MAX_SIZE = 1 << 16;

    private int index = 0;

    public TextData(int size) {
        this.size = size;
        buffer = new char[size];
    }

    /**
     * 全部读
     *
     * @return
     */
    private String doRead() {
        StringBuilder sb = new StringBuilder();
        for (char c : buffer) {
            sb.append(c);
        }
        return sb.toString();
    }

    /**
     * 尾部写
     */
    private void doWrite(String appendStr) throws Exception {
        if (appendStr == null) {
            return;
        }
        int len = appendStr.length();
        if (len == 0) {
            return;
        }
        if ((len + index) >= size) {
            throw new Exception("append chars beyond up limit.");
        }
        char[] data = appendStr.toCharArray();

        for (char c : data) {
            buffer[index++] = c;
        }

    }

    public String read() {
        readLock.lock();
        try {
            return doRead();
        } finally {
            readLock.unlock();
        }
    }

    public void write(String appendStr) {
        writeLock.lock();
        try {
            doWrite(appendStr);
        } catch (Exception e) {
            System.out.println("Write Error !");
            e.printStackTrace();
        } finally {
            writeLock.unlock();
        }

    }

    public static void main(String[] args) {
        TextData textData = new TextData(20000);

        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(new Thread(new WriteThread(textData, "I Love U,")));
        executorService.execute(new Thread(new WriteThread(textData, "baby.")));
        executorService.execute(new Thread(new ReadThread(textData)));
        executorService.execute(new Thread(new ReadThread(textData)));
        executorService.execute(new Thread(new WriteThread(textData, "I Miss U everyday and night.")));
        executorService.execute(new Thread(new ReadThread(textData)));
        executorService.execute(new Thread(new ReadThread(textData)));
        executorService.execute(new Thread(new WriteThread(textData, "But, I will fill my Heart.")));
        executorService.execute(new Thread(new ReadThread(textData)));
        // 关闭
        executorService.shutdown();
    }
}

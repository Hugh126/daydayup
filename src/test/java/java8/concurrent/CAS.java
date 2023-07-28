package java8.concurrent;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.Test;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;

public class CAS {

    @Data
    @AllArgsConstructor
    static class Account {
        private Double amount;
    }

    private volatile  AtomicReference<Account> accountRef = new AtomicReference<Account>(new Account(0.0));
    private static ThreadPoolExecutor executorService = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);

    /**
     * 在使用 compareAndSet 或 tryLock的时候要注意重试
     */
    private void doUpdate() {
        Account account = accountRef.get();
        Account account2 = new Account(account.getAmount() + 1.0);
        boolean flag = accountRef.compareAndSet(account, account2);
        if (!flag) {
            doUpdate();
        }
    }

    @Test
    public void test1() throws InterruptedException {
        IntStream.range(0, 1000).forEach(t -> {
            executorService.submit(() -> {
//                doUpdate();
                while (true) {
                    Account account = accountRef.get();
                    Account account2 = new Account(account.getAmount() + 1.0);
                    if (accountRef.compareAndSet(account, account2)){
                        break;
                    }
                }
            });
        });
        while (executorService.getCompletedTaskCount() < executorService.getTaskCount()) {
            TimeUnit.MILLISECONDS.sleep(100L);
        }

        System.out.println("--END--" + accountRef.get().getAmount());
    }

}

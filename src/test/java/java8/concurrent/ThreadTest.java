package java8.concurrent;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author hugh
 * @version 1.0
 * @description:
 * @date 2019/11/30 0030
 */
public class ThreadTest {

	@Test
	public void  test1() {
		Runnable task = () -> {
			String name = Thread.currentThread().getName();
			System.out.println("Foo" + name);
			try {
				TimeUnit.SECONDS.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("Bar" + name);
		};

		Thread thread = new Thread(task);
		thread.start();
	}


	@Test
	public void  test2() {
		ExecutorService executorService = Executors.newSingleThreadExecutor();
		executorService.submit( () -> {
			String name = Thread.currentThread().getName();
			System.out.println("hello, " + name);
		});

		try {
			System.out.println("attempt to shutdown.");
			// 关闭当前
			executorService.shutdown();
			executorService.awaitTermination(5, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			if(!executorService.isTerminated()) {
				System.out.println("existed non-finished tasks.");
			}
			// 关闭所有
			executorService.shutdownNow();
		}

	}




}

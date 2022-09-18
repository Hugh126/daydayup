package java8.concurrent;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.util.StopWatch;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author hugh
 * @version 1.0
 * @description:
 * @date 2019/11/30 0030
 */
@Slf4j
public class ThreadTest {

	@Test
	public void  test1() throws InterruptedException {
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
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		Thread thread = new Thread(task);
		thread.start();
		foo1();

		stopWatch.stop();
		log.warn(stopWatch.prettyPrint());
	}

	private void foo1() throws InterruptedException {
		TimeUnit.SECONDS.sleep(1);
		foo2();
	}

	private void foo2() throws InterruptedException {
		TimeUnit.SECONDS.sleep(2);
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

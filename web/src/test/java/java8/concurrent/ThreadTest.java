package java8.concurrent;

import cn.hutool.core.thread.ConcurrencyTester;
import cn.hutool.core.thread.ThreadUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.util.StopWatch;

import java.util.Random;
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

	static InheritableThreadLocal<String> local = new InheritableThreadLocal();
	static ThreadLocal<String> local2 = new ThreadLocal();

	@Test
	public void test3() throws InterruptedException {
		ExecutorService executor = Executors.newFixedThreadPool(3);
//		new Thread(() -> {
//			System.out.println("当前线程 "+Thread.currentThread().getName() + " 设置值");
//			local.set("a");
//			new Thread(() -> {
//				System.out.println("子线程 " + Thread.currentThread().getName() + " 取值" + local.get());
//			}).start();
//		}).start();
		TimeUnit.SECONDS.sleep(1L);
		for (int i = 0; i <10 ; i++) {
			final Integer x=i;
			executor.submit(() -> {
				if (x==0) {
					local.set("a");
				}
				System.out.println("子线程 " + Thread.currentThread().getName() + " 取值" + local.get());
			});
		}
		TimeUnit.SECONDS.sleep(10L);
	}

	static class MyThread extends Thread{
		@Override
		public void run() {
			System.out.println("线程 " + Thread.currentThread().getName() + " 取值" + local.get() + " 取值2" + local2.get()) ;
		}

		public MyThread(ThreadGroup group, String name) {
			super(group, name);
		}
	}

	@Test
	public void test4() {
		ThreadGroup threadGroup = new ThreadGroup("prefix--");
		new Thread(threadGroup, ()-> {
			local.set("aaa");
		}).start();
		local2.set("bbb");
		for (int i = 0; i <10 ; i++) {
			new MyThread(threadGroup, "xx").start();
		}
	}

	@Test
	public void hutoolThread1() {
		ConcurrencyTester concurrencyTester = ThreadUtil.concurrencyTest(10, () -> {
			String name = Thread.currentThread().getName();
			log.warn("[{}] start", name);
			try {
				TimeUnit.SECONDS.sleep(new Random().nextInt(5));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			log.warn("[{}] done", name);
		});
	}

}

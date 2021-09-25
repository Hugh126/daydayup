package java8.concurrent;

import org.junit.Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

/**
 * @author hugh
 * @version 1.0
 * @description:
 * @date 2019/11/30 0030
 */
public class IncrementTest {

	private int count = 0;
	private ReentrantLock lock = new ReentrantLock();



	private void increment(){
		count = count + 1;
//		System.out.println(Thread.currentThread().getName() + "  " + count);
	}

	@Test
	public void test1() {
		ExecutorService executorService = Executors.newFixedThreadPool(10);
//		IntStream.range(0, 10000).forEach( i -> executorService.submit(this::increment));
		IntStream.range(0, 10000).forEach( i -> executorService.submit(this::incrementSync));
		stop(executorService);
		System.out.println(count);
	}

	@Test
	public void test110(){
		final int MAX_POOL_SIZE = 1000;
		final long MAX_KEEP_ALIVE = 60L;
		ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors() + 1, MAX_POOL_SIZE, MAX_KEEP_ALIVE, TimeUnit.SECONDS, new ArrayBlockingQueue(MAX_POOL_SIZE));
		poolExecutor.execute(new Thread());
	}

	private void stop(ExecutorService executorService){
		try {
			executorService.shutdown();
			executorService.awaitTermination(5, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			executorService.shutdownNow();
			System.out.println("-----------executor stop-----------");
		}

	}

	private void incrementSync() {
		lock.lock();
		try{
			count ++;
		}finally {
			lock.unlock();
		}
	}

	@Test
	public void test222(){
		System.out.println(String.format("111%s",null));;
	}


}

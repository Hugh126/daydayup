package java8;

import org.junit.Test;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author hugh
 * @version 1.0
 * @description:
 *
 * 数据流无法复用，如代码逻辑需要，使用：
 * Supplier<Stream<String>> streamSupplier =
 *     () -> Stream.of("d2", "a2", "b1", "b3", "c")
 *             .filter(s -> s.startsWith("a"));
 *
 * streamSupplier.get() 每次get都会新创建数据流对象
 *---------------------------------------------------
 * 数据流操作，为流水线作业，
 *
 *
 *
 * @date 2019/11/23 0023
 */
public class StreamTest {


	/**
	 * 创建数据流的方式
	 *
	 * 1. List和Set -> stream() / parallelStream()
	 * 2. Stream.of
	 * 3. Arrays.stream
	 * 4. IntStream、LongStream 和 DoubleStream
	 */


	@Test
	public void test1() {

		Arrays.asList("a1", "a2", "a3")
				.stream()
				.findFirst()
				.ifPresent(System.out::println);


		Stream.of("a1", "a2", "a3")
				.findFirst()
				.ifPresent(System.out::println);

		/**
		 * java中自带特殊数据流， IntStream、LongStream 和 DoubleStream
		 * range [)
		 */
		IntStream.range(1, 4).forEach(System.out::println);

		Arrays.stream(new int[]{1, 2, 3}).map(n -> 2*n + 1).average().ifPresent(System.out::println);
	}


	/**
	 * 基本数据流 -> 对象流
	 * mapToObj
	 * 对象流 -> 数据流
	 * mapToInt()、mapToLong() 和 mapToDouble()
	 */
	@Test
	public void test2() {
		// 数据路转对象
		IntStream.range(1, 4).mapToObj(i -> 'a' + i).forEach(System.out::println);
		// 对象转数据流
		Stream.of("a1", "a2", "a3").map(item -> item.substring(1)).mapToInt(Integer::parseInt).max().ifPresent(System.out::println);
	}


	/**
	 * 求和的几种方式
	 */
	@Test
	public void test3() {

		// 数据流 -> sum
		System.out.println(IntStream.range(1, 101).map(i -> 2 * i).sum());

		System.out.println(Arrays.asList(1, 2, 3).stream().mapToInt(Integer::valueOf).sum());

		// 对象流 -> 数据流 -> sum
		System.out.println(Stream.of("a1", "a2", "a3").map(item -> item.substring(1)).mapToInt(Integer::parseInt).sum());

		System.out.println(Arrays.asList(1, 2, 3).stream().collect(Collectors.summingInt(i -> i)).intValue());


	}


}

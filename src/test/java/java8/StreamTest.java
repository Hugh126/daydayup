package java8;

import cn.hutool.json.JSONUtil;
import com.google.common.collect.Lists;
import org.junit.Assert;
import org.junit.Test;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
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


	@Test
	public void test4() {
		String[] arr = new String[] {"aa", "bb", "cc"};
		System.out.println(Arrays.stream(arr).collect(Collectors.toList()));
		System.out.println(Stream.of(arr, arr).flatMap(Arrays::stream).collect(Collectors.toList()));

		ArrayList<String> strList = Lists.newArrayList("1,2,3", "4,5,6");
		List<String> collect = strList.stream().map(str -> Arrays.asList(str.split(","))).flatMap(Collection::stream).collect(Collectors.toList());
		Assert.assertEquals(6, collect.size());
		System.out.println(JSONUtil.toJsonStr(collect));
	}

	@Test
	public void skip() {
		Date date = new Date();
		LocalDateTime localDateTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
		System.out.println("Date: " + date);
		System.out.println("LocalDateTime: " + localDateTime);

		LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		System.out.println("LocalDate: " + localDate);

//		IntStream.range(1,10).skip(5).forEach(System.out::println);
	}

	/**
	 * repeat
	 */
	@Test
	public void flatMapTest2() {
		List<String> list = Arrays.asList("1234".split(""));
		List<String> listAll = list.stream()
				.flatMap(str -> list.stream().map(str::concat))
				.flatMap(str -> list.stream().map(str::concat))
				.flatMap(str -> list.stream().map(str::concat))
				.collect(Collectors.toList());
		System.out.println(listAll.size());
		Assert.assertEquals(listAll.size(), 4*3*2*1);
//		listAll.forEach(System.out::println);
	}



	// C(m, 3)
	Set<String> contract(int m) {
		Set<String> set = new HashSet<>();
		for (int i = 1; i <= m; i++) {
			for (int j = 1; j <= m; j++) {
				if(j == i) {
					continue;
				}
				for (int k = 1; k <= m; k++) {
					if(i==k || j==k) {
						continue;
					}
					Assert.assertNotEquals(i,j);
					Assert.assertNotEquals(i,k);
					Assert.assertNotEquals(j,k);
					int[] arr = new int[]{i, j, k};
					Arrays.sort(arr);
					String sb = new StringBuffer().append(arr[0]).append(arr[1]).append(arr[2]).toString();
					if (!set.contains(sb)) {
						set.add(sb);
					}
				}
			}
		}
		return set;
	}

	// C(m, 3)
	Set<String> combination(int m, int[] numbers, int d) {
		Set<String> set = new HashSet<>();
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < m; j++) {
				if(j == i) continue;
				for (int k = 0; k < m; k++) {
					if(i==k || j==k) continue;
					int[] arr = new int[]{numbers[i], numbers[j], numbers[k]};
					Arrays.sort(arr);
					int a0 = arr[0];
					int a1 = arr[1];
					int a2 = arr[2];
					if (a2- a1 <= d && a1 - a0 <= d && a2-a0 <=d) {
						String sb = new StringBuffer().append(a0).append(' ').append(a1).append(' ').append(a2).toString();
						if (!set.contains(sb)) {
							set.add(sb);
						}
					}
				}
			}
		}
		return set;
	}

	/**
	 *  no repeat
	 */
	@Test
	public void flatMapTest3() {
//		int[] nums = {1 ,10, 20, 30, 50};
		Set<String> set = contract(5);
//		Set<String> set = combination(5, nums, 19);
		set.stream().forEach(System.out::println);
	}



}

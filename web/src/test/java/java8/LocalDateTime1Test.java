package java8;

import org.junit.Test;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;

public class LocalDateTime1Test {

	/**
	 * DateTimeFormatter  线程安全
	 */
	@Test
	public void test1(){
		// 本地时间, String -> LocalDateTime
		LocalDateTime localDateTimeInstance1 = LocalDateTime.of(2019, Month.NOVEMBER, 11, 10, 00, 00);

		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime localDateTimeInstance2 = LocalDateTime.parse("2019-11-11 10:00:00", dateTimeFormatter);

		System.out.println(localDateTimeInstance1.isEqual(localDateTimeInstance2));

		// LocalDateTime -> String
		LocalDateTime localDateTimeInstant = LocalDateTime.now();
		String dateStr = localDateTimeInstant.format(dateTimeFormatter);
		System.out.println(dateStr);
	}


	@Test
	public void test2() {
		System.out.println(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME).substring(0,5));
	}
}
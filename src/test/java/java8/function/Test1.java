package java8.function;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import common.TriConsumer;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 *

 Function	Function< T, R >	接收T对象，返回R对象
 Consumer	Consumer< T >	接收T对象，不返回值
 Supplier	Supplier< T >	提供T对象（例如工厂），不接收值

 Predicate	Predicate< T >	接收T对象并返回boolean
 * @param <T1>
 * @param <R1>
 */
public class Test1<T1, R1> {

    Function<T1, R1> processer = a -> (R1) ("return:" + a);


    @Test
    void functionTest() {
        Test1<String, String> test1 = new Test1<>();
        String apply = test1.processer.apply("hello");
        System.out.println("apply---" + apply);

        String andThen = test1.processer.andThen(x -> "[DIYFun]" + x).apply("HI");
        System.out.println("andThen---" + andThen);

        String compose = test1.processer.compose(x -> "[DIYFun]" + x).apply("HI");
        System.out.println("compose---" + compose);
    }

    @Test
    void consumerTest() {
        Supplier<String> provider = () -> "x";
        Consumer<String> consumer = s -> System.out.println(s);
        consumer.accept(provider.get());
    }

    private void templateMethod(TriConsumer triConsumer, Object... objects) {
        System.out.println("step 1");
        System.out.println("step 2");
        if (triConsumer != null && objects != null && objects.length == 3) {
            triConsumer.accept(objects[0], objects[1], objects[2]);
        }
        System.out.println("step 3");
    }

    /**
     * 自定义FunctionalInterface函数接口，解耦方法嵌入
     */
    @Test
    void consumerTest2() {
        TriConsumer<Integer, String, Object> consumer = (Integer x, String y, Object z) -> {
            System.out.println(x + "-" + y + z);
        };
        templateMethod(consumer, 1, "t2", 3.33);
        System.out.println("------------");
        templateMethod(null, null);
    }


    @Test
    void name() {
        System.out.println(Stream.of("", "ss").collect(Collectors.counting()));
    }

    @Test
    void name2() throws ExecutionException {
        Cache<String, String> dict = CacheBuilder.newBuilder().expireAfterWrite(10, TimeUnit.SECONDS).build();

        dict.put("1", "11");
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        dict.invalidate("1");
        System.out.println(dict.get("1", () -> {
            return LocalDateTime.now().toString();
        }));;
        dict.invalidate("1");
        System.out.println(dict.getIfPresent("1"));
    }


}

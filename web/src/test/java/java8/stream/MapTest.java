package java8.stream;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class MapTest {

    /**
     * computeIfAbsent
     */
    @Test
    public void test1() {
        Map<String, List<Integer>> result = new HashMap();
        IntStream.range(1, 10).forEach(a -> {
            String key = (a%2 == 0 ? "偶数" : "奇数");
            result.computeIfAbsent(key, x -> new ArrayList<>()).add(a);
        });
        System.out.println(result);
    }



}

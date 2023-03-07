package java8;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @Author hugh
 * @Description
 * @Date 2022/1/7
 */
@Slf4j
public class TreeMap1 {

    @Test
    void test1() {
        TreeMap map = new TreeMap();
        map.put(1, "1");
        map.put(2, "2");

        map.put(4, "4");
        map.put(5, "5");
        map.put(6, "6");
        SortedMap sortedMap = map.tailMap(3);
        System.out.println(JSONUtil.toJsonStr(sortedMap));
        System.out.println(JSONUtil.toJsonStr(map.tailMap(7)));
    }

    @Test
    void test2() {
        LinkedHashMap map = new LinkedHashMap(10, .75f, true);
        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);
        System.out.println(1);
        map.get("b");
        System.out.println(2);
        map.put("c", 33);
        System.out.println(3);

    }
}

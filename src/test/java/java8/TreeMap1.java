package java8;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONUtil;
import org.junit.jupiter.api.Test;

import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @Author hugh
 * @Description
 * @Date 2022/1/7
 */
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



}

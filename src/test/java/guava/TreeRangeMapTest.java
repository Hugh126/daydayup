package guava;

import com.google.common.collect.Range;
import com.google.common.collect.RangeMap;
import com.google.common.collect.TreeRangeMap;
import org.junit.jupiter.api.Test;


public class TreeRangeMapTest {

    @Test
    void test1() {
        RangeMap<SelfDate, String> rangeMap = TreeRangeMap.create();
        SelfDate s1 = new SelfDate(2021, 1, 1);
        SelfDate s2 = new SelfDate(2021, 2, 10);
        rangeMap.put(Range.closed(s1, s2), "aaa");

        SelfDate s3 = new SelfDate(2021, 2, 1);
        SelfDate s4 = new SelfDate(2021, 3, 1);
        rangeMap.put(Range.closed(s3, s4), "bbb");

        SelfDate s5 = new SelfDate(2021, 4, 1);
        SelfDate s6 = new SelfDate(2021, 4, 15);
        rangeMap.put(Range.closed(s5, s6), "ccc");

        System.out.println(rangeMap);

    }

}



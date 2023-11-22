package com.example.myspring.guava;

import cn.hutool.core.date.DateUtil;
import com.google.common.collect.Range;
import com.google.common.collect.RangeMap;
import com.google.common.collect.TreeRangeMap;
import org.junit.jupiter.api.Test;

import java.util.Date;


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


    @Test
    void test2() {
        RangeMap<Date, String> rangeMap = TreeRangeMap.create();
        rangeMap.put(Range.closed(DateUtil.parse("20210101"), DateUtil.parse("20210601")), "1");
        rangeMap.put(Range.closed(DateUtil.parse("20210501"), DateUtil.parse("20210830")), "2");
        String s = rangeMap.get(DateUtil.parse("20210510"));
        System.out.println("s=" + s);
        System.out.println(rangeMap.span());
    }

    private Date gDate(int d) {
        return DateUtil.parse(String.valueOf(d));
    }

    @Test
    void test3() {
        RangeMap<Date, String> rm = TreeRangeMap.create();
        rm.put(Range.closed(gDate(20231001), gDate(20231030)), "123");
        rm.putCoalescing(Range.closed(gDate(20231010), gDate(20231120)), "456");
        System.out.println(rm.toString());
    }
}



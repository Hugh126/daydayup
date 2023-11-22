package com.example.myspring.guava;

import com.google.common.base.Function;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSortedMap;
import com.google.common.collect.Lists;
import com.google.common.collect.MapDifference;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.google.common.collect.Ordering;
import com.google.common.collect.Table;
import com.google.common.collect.Tables;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * @Author hugh
 * @Description
 * @Date 2022/1/21
 */
@Slf4j
public class MapTest {

    @Test
    public void whenUsingSortedMap_thenKeysAreSorted() {
        ImmutableSortedMap<String, Integer> salary = new ImmutableSortedMap
                .Builder<String, Integer>(Ordering.natural())
                .put("John", 1000)
                .put("Jane", 1500)
                .put("Adam", 2000)
                .put("Tom", 2000)
                .build();
        System.out.println(salary.lastEntry());
        Assert.assertEquals("Adam", salary.firstKey());
        Assert.assertEquals(2000, salary.lastEntry().getValue().intValue());
    }

    /**
     *  it makes sure the values are unique.
     */
    @Test
    public void whenCreateBiMap_thenCreated() {
        BiMap<String, Integer> words = HashBiMap.create();
        words.put("First", 1);
        words.put("Second", 2);
//        System.out.println(words.put("Second2", 2));
        words.put("Third", 3);

        Assert.assertEquals(2, words.get("Second").intValue());
        System.out.println(words.inverse());
        Assert.assertEquals("Third", words.inverse().get(3));
    }

    @Test
    public void whenCreateMultimap_thenCreated() {
        Multimap<String, String> multimap = ArrayListMultimap.create();
        multimap.put("fruit", "apple");
        multimap.put("fruit", "banana");
        multimap.put("pet", "cat");
        multimap.put("pet", "dog");

        System.out.println(multimap.get("fruit"));
        assertThat(multimap.get("fruit"), Matchers.containsInAnyOrder("apple", "banana"));
        assertThat(multimap.get("pet"), Matchers.containsInAnyOrder("cat", "dog"));
    }

    /**
     * interface Table<R, C, V>
     */
    @Test
    public void whenCreatingTable_thenCorrect() {
        Table<String,String,Integer> distance = HashBasedTable.create();
        distance.put("London", "Paris", 340);
        distance.put("New York", "Los Angeles", 3940);
        distance.put("London", "New York", 5576);

        Assert.assertEquals(3940, distance.get("New York", "Los Angeles").intValue());
        assertThat(distance.columnKeySet(),
                Matchers.containsInAnyOrder("Paris", "New York", "Los Angeles"));
        System.out.println(distance.rowKeySet());
        System.out.println(distance.columnKeySet());
        assertThat(distance.rowKeySet(), Matchers.containsInAnyOrder("London", "New York"));
    }

    /**
     * 行列倒置
     */
    @Test
    public void whenTransposingTable_thenCorrect() {
        Table<String,String,Integer> distance = HashBasedTable.create();
        distance.put("London", "Paris", 340);
        distance.put("New York", "Los Angeles", 3940);
        distance.put("London", "New York", 5576);

        Table<String, String, Integer> transposed = Tables.transpose(distance);
        System.out.println(transposed);

        assertThat(transposed.rowKeySet(),
                Matchers.containsInAnyOrder("Paris", "New York", "Los Angeles"));
        assertThat(transposed.columnKeySet(), Matchers.containsInAnyOrder("London", "New York"));
    }

    /**
     * List, keyFuction -> Multimap
     */
    @Test
    public void whenGroupingListsUsingMultimap_thenGrouped() {
        List<String> names = Lists.newArrayList("John", "Adam", "Tom");
        Function<String,Integer> func = new Function<String,Integer>(){
            public Integer apply(String input) {
                return input.length();
            }
        };
        Multimap<Integer, String> groups = Multimaps.index(names, func);

        System.out.println(groups);
        assertThat(groups.get(3), Matchers.containsInAnyOrder("Tom"));
        assertThat(groups.get(4), Matchers.containsInAnyOrder("John", "Adam"));
    }

    @Test
    public void differenceMap() {
        Map map1 = ImmutableMap.of("aa", 1, "bb", 2, "cc", 3, "dd", 4);
        Map map2 = ImmutableMap.of("a1", 11, "bb", 22, "cc", 3, "dd", 44, "ee", 5);
        MapDifference difference = Maps.difference(map1, map2);
        // del
        Assert.assertEquals(1, difference.entriesOnlyOnLeft().size());
        // add
        Assert.assertEquals(2, difference.entriesOnlyOnRight().size());
        // update
        Assert.assertEquals(2, difference.entriesDiffering().size());
        // common
        Assert.assertEquals(1, difference.entriesInCommon().size());
    }

    @Test(expected = NullPointerException.class)
    public void HashMap_merge1() {
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        Map<String, String> map = list.stream().collect(Collectors.toMap(item -> item, item -> {
            if (item.equals("b")) {
                return null;
            }
            return item;
        }));
    }

    @Test
    public void HashMap_merge2() {
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        Map<String, String> map2 = list.stream().collect(HashMap::new, (map, item) -> map.put(item, item.equals("b") ? null : item), HashMap::putAll);
        Assert.assertEquals(map2.size(), 2);
    }


    @Test
    public void test5() throws InterruptedException {
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("longPolling-timeout-checker-%d").build();
        ScheduledExecutorService timeoutChecker = new ScheduledThreadPoolExecutor(1, threadFactory);
        log.warn(LocalDateTime.now().toString());
        timeoutChecker.schedule( () -> {
            log.warn("--task run--");
        }, 1000, TimeUnit.MILLISECONDS);
        log.warn(LocalDateTime.now().toString());
        TimeUnit.SECONDS.sleep(3);
    }

}

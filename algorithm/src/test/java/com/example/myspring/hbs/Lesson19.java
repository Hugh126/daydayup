package com.example.myspring.hbs;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 设计和编码实现一个具备 LRU 过期策略的缓存程序
 */
public class Lesson19<K, V> extends LinkedHashMap<K, V>{

    protected final int maxSize;

    public Lesson19(int initialCapacity, int maxSize) {
        super(initialCapacity);
        this.maxSize = maxSize;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return super.size() > maxSize;
    }

    public static void main(String[] args) {
        Lesson19<Integer, Integer> map = new Lesson19(1, 3);
        map.put(1,1);
        map.put(2,2);
        map.put(3,3);
        System.out.println(map.size());
        map.put(4,4);
        System.out.println(map.size());
        map.put(5,5);
        System.out.println(map.size());
    }
}

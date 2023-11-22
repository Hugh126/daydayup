package com.example.myspring.common;

import com.example.myspring.annotation.Name;
import com.example.myspring.annotation.SortOrder;
import com.google.common.collect.MapDifference;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;


/**
 * 利用guava的Map对比功能，封装一个对比两个Obj的类，返回差异List
 *
 * 支持 RowHeader、支持Sort
 */
@Data
@Slf4j
public class DiffResult {

    // origin data
    private Map<String, Object> leftMap;
    private Map<String, Object> rightMap;
    // diff data
    private Map<String, MapDifference.ValueDifference<Object>> dataMap;
    // to revert Annotation name
    private Class clazz;
    private Map<String, String> keyNameMap = new HashMap<>();
    // order
    private Comparator<String> fieldOrder = null;

    private DiffResult() {
    }

    public static DiffResult getInstance(Map<String, Object> leftMap, Map<String, Object> rightMap){
        return getInstance(leftMap, rightMap, null);
    }

    public static DiffResult getInstance(Map<String, Object> leftMap, Map<String, Object> rightMap, Class clazz) {
        DiffResult result = new DiffResult();
        result.setLeftMap(leftMap);
        result.setRightMap(rightMap);
        Map<String, MapDifference.ValueDifference<Object>> diff = diff2Map(leftMap, rightMap);
        result.setDataMap(diff);
        result.setClazz(clazz);
        Field[] fields = clazz.getDeclaredFields();
        Map<String, String> map = new HashMap<>();
        for (Field field : fields) {
            Name name = field.getAnnotation(Name.class);
            if (name != null) {
                map.put(field.getName(), name.value());
            }
        }
        Annotation clazzAnnotation = clazz.getDeclaredAnnotation(SortOrder.class);
        if (clazzAnnotation != null) {
            SortOrder sortOrder = (SortOrder) clazzAnnotation;
            List<String> fieldOrder = Arrays.asList(sortOrder.value());
            result.setFieldOrder(new FieldComparator(fieldOrder));
        }
        result.setKeyNameMap(map);
        return result;
    }


    private static Map<String, Object> buildMap(HashSet<String> keySet, Map<String, Object> map ) {
        Map<String, Object> fullMap = Maps.newHashMap();
        keySet.forEach(k -> {
            if(map.containsKey(k)) {
                fullMap.put(k,map.get(k));
            }else{
                fullMap.put(k, null);
            }
        });
        return fullMap;
    }

    public static Map<String, MapDifference.ValueDifference<Object>> diff2Map(Map<String, Object> left, Map<String, Object> right) {
        HashSet<String> keySet = Sets.newHashSet();
        keySet.addAll(left.keySet());
        keySet.addAll(right.keySet());
        Map<String, Object> mm1 = buildMap(keySet, left);
        Map<String, Object> mm2 = buildMap(keySet, right);
        return Maps.difference(mm1, mm2).entriesDiffering();
    }

    /**
     * header + data 列拼接
     * 仓库       | 原材料    |  采购价
     * 大悦城仓    | 花生     |   19.9->9.9
     * @param heads
     * @return
     */
    public List<List<Object>> showDiffsWithHeader(LinkedHashMap<String, Object> heads) {
        List<List<Object>> result = new ArrayList<>(this.dataMap.size());
        List<Object> head = new ArrayList<>(heads.size());
        List<Object> data = new ArrayList<>(this.dataMap.size());
        heads.forEach((k,v) -> {
            head.add(keyNameMap.getOrDefault(k, k));
            data.add(v);
        });
        Set<String> keySet;
        if (this.fieldOrder != null) {
            keySet = new TreeSet<>(this.fieldOrder);
            keySet.addAll(this.dataMap.keySet());
        }else {
            keySet = this.dataMap.keySet();
        }
        keySet.forEach( k -> {
            MapDifference.ValueDifference<Object> value = this.dataMap.get(k);
            head.add(keyNameMap.getOrDefault(k,k));
            data.add(value.leftValue() + "->" + value.rightValue());
        });
        result.add(head);
        result.add(data);
        return result;
    }

    private static class FieldComparator implements Comparator<String>{
        private List<String> fieldOrder;
        public FieldComparator(List<String> fieldOrder) {
            this.fieldOrder = fieldOrder;
        }

        @Override
        public int compare(java.lang.String o1, java.lang.String o2) {
            return indexOf(o1) - indexOf(o2);
        }

        @Override
        public boolean equals(Object obj) {
            return Objects.equals(this, obj);
        }
        private int indexOf(java.lang.String o) {
            int index = fieldOrder.indexOf(o);
            return index != -1 ? index : fieldOrder.size();
        }
    }

}

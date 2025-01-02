package com.example.myspring.algoTest;



import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * 归并排序，合并两个有序子集
 */
public class MergeSortTest {

    int[] arr1 = {1, 3, 5, 7, 9};
    int[] arr2 = {2, 4, 8, 10,12, 17};
    int left = arr1.length;
    int right = arr2.length;

    int[] sort() {
        int[] leftArr = Arrays.copyOf(arr1, left + 1);
        int[] rightArr = Arrays.copyOf(arr2, right + 1);
        leftArr[left] = Integer.MAX_VALUE;
        rightArr[right] = Integer.MAX_VALUE;

        int[] result = new int[left + right];
        for (int k=0, i=0, j=0; k < result.length; k ++) {
            if (leftArr[i] < rightArr[j]) {
                result[k] = leftArr[i];
                i++;
            }else {
                result[k] = rightArr[j];
                j++;
            }
        }
        return result;
    }

    @Test
    void test1() {
        int[] arr = sort();
        System.out.println(IntStream.of(arr).mapToObj(a -> "" + a).collect(Collectors.joining(",", "[", "]")));
    }

    @Test
    void wordCount0() {
        String[] split = "Hello".split("");
        System.out.println(Stream.of(split).distinct().count());
    }


    @Test
    void wordCount() {
        String[] words = new String[]{"Hello","World"};
        long count = Arrays.stream(words).map(word -> word.split("")).flatMap(Arrays::stream).count();
        System.out.println(count);
    }
}

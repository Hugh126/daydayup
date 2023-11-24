package com.example.myspring.algoTest;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * Letcode 33
 *
 * 如果有序数组是一个循环有序数组，比如 4，5，6，1，2，3。针对这种情况，如何实现一个求“值等于给定值”的二分查找算法呢
 *
 */
@Slf4j
public class BSearch {

    /**
     * 常规二分查找实现
     * @param arr
     * @param low
     * @param high
     * @param target
     * @return
     */
    public static int subSearch(int[] arr, int low, int high, int target) {
        if (low > high) {
            return -1;
        }
        // >> 优先级居然没 + 高
        int mid = low + ((high-low)>>1);
        if (arr[mid] == target) {
            return mid;
        }
        if (arr[mid] < target) {
            low = mid + 1;
            return subSearch(arr, low, high, target);
        }else {
            high = mid-1;
            return subSearch(arr, low, high, target);
        }
    }

    public static int bsearch(int[] arr, int target) {
        return subSearch(arr, 0, arr.length -1 , target);
    }

    public static int findIndex(int[] arr) {
        for(int i=0;i<arr.length - 1;i++) {
            if (arr[i] > arr[i+1]) {
                return i;
            }
        }
        return -1;
    }

    public static int specialSearch(int[] arr1, int target) {
        int index = findIndex(arr1);
        if (index < 0) {
            return bsearch(arr1, target);
        }
        int bsearch1 = bsearch(Arrays.copyOfRange(arr1, 0, index+1), target);
        int bsearch2 = bsearch(Arrays.copyOfRange(arr1, index+1, arr1.length), target);
        if (bsearch1 >= 0) {
            return bsearch1;
        }
        if (bsearch2 >= 0) {
            return bsearch2+index+1;
        }
        return -1;
    }


    @Test
    public void test1() {
        int[] arr1 = { 4,5,6,7,0,1,2};
        int target1 = 0;
        int[] arr2 = { 4,5,6,7,0,1,2};
        int target2 = 3;
        int[] arr3 = {1,2};
        int target3 = 0;
        int[] arr4 = {2, 1};
        int target4 = 1;

        System.out.println(specialSearch(arr1, target1));
        System.out.println(specialSearch(arr2, target2));
        System.out.println(specialSearch(arr3, target3));
        System.out.println(specialSearch(arr4, target4));
    }





}

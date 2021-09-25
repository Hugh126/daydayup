package algoTest;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 归并排序，合并两个有序子集
 */
public class MergeSortTest {

    int[] arr1 = {3, 5, 7, 9};
    int[] arr2 = {2, 4, 8, 10};
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
        int[] a = sort();
        for(int i=0;i<a.length;i++) {
            System.out.println(a[i]);
        }
    }

    @Test
    void test4() {
        List<Integer> financeCostTypeMenuClassIdsForByMenu = Stream.of(29, 14, 49, 47, 31, 35, 57, 33, 8, 58, 30059, 11, 12, 10, 26, 38).collect(Collectors.toList());
        String menuClassIds = financeCostTypeMenuClassIdsForByMenu.stream().map(item -> String.valueOf(item)).collect(Collectors.joining(",", "(", ")"));
        System.out.println(menuClassIds);
    }
}

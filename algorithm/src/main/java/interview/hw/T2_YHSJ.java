package interview.hw;

import cn.hutool.json.JSONUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Leetcode 118
 * 给定一个非负整数 numRows，生成「杨辉三角」的前 numRows 行。
 *
 * 示例 1:
 *
 * 输入: numRows = 5
 * 输出: [[1],[1,1],[1,2,1],[1,3,3,1],[1,4,6,4,1]]
 * 示例 2:
 *
 * 输入: numRows = 1
 * 输出: [[1]]
 */
public class T2_YHSJ {

    public static void main(String[] args) {
        System.out.println(JSONUtil.toJsonStr(generate(5)));;
        System.out.println(JSONUtil.toJsonStr(generate2(5)));
    }



    public static int[] getN(int[][] all, int n) {
        if (n == 1) {
            int[] a0 = new int[]{1};
            all[0]= a0;
        }
        if (n == 2) {
            int[] a1 = new int[]{1,1};
            all[1]= a1;
        }
        if (n <= 2) {
            return all[n-1];
        }
        int[] arr = new int[n];
        for (int i=0;i<n;i++) {
            if (i == 0 || i== n-1) {
                arr[0] = 1;
                arr[n-1] = 1;
            }else {
                int[] old = all[n-2];
                arr[i] = old[i-1] + old[i];
            }
        }
        all[n-1] = arr;
        return arr;
    }

    public static List<List<Integer>> generate(int numRows) {
        int[][] all = new int[numRows][numRows];
        for(int i=1;i<=numRows;i++) {
            getN(all, i);
        }
        int[][] f = all;
        List<List<Integer>> xx = new ArrayList<>();
        for (int i = 0; i < f.length; i++) {
            ArrayList l = new ArrayList(f[i].length);
            for (int j=0;j<f[i].length;j++){
                l.add(f[i][j]);
            }
            xx.add(l);
        }
        return xx;
    }


    /**
     * 打印第N行
     * @param numRows
     * @return
     */
    public static List<Integer> generate2(int numRows) {
        int[][] all = new int[numRows][numRows];
        for(int i=1;i<=numRows;i++) {
            getN(all, i);
        }
        return Arrays.stream(all[numRows-1]).boxed().collect(Collectors.toList());
    }



}

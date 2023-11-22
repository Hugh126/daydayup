package interview.bytecode;

import java.util.*;

/**
 * @Description
 * @Date 2023/8/21 17:13
 * @Created by hugh
 *
 * 排列组合问题解答
 * https://blog.csdn.net/Ring_k/article/details/79575533
 * @TODO: 这种递归的组合实现方式居然算法复杂度超出了，磨人啊
 *
 */
//     * 输入描述：
//         * 第一行包含空格分隔的两个数字 N和D
//         * 第二行包含N个建筑物的的位置，每个位置用一个整数（取值区间为[0, 1000000]）表示，从小到大排列（将字节跳动大街看做一条数轴）
//            * 输出描述：
//            * 一个数字，表示不同埋伏方案的数量。结果可能溢出，请对 99997867 取模
//     * 示例1
//     * 输入例子：
//            * 4 3
//            * 1 2 3 4
//            * 输出例子：
//            * 4
//            * 例子说明：
//            * 可选方案 (1, 2, 3), (1, 2, 4), (1, 3, 4), (2, 3, 4)
public class L2 {

    static int[] arr;
    static int d;

    /**
     * 递归方法，当前已抽取的小球个数与要求抽取小球个数相同时，退出递归
     * @param curnum - 当前已经抓取的小球数目
     * @param curmaxv - 当前已经抓取小球中最大的编号
     * @param maxnum - 需要抓取小球的数目
     * @param maxv - 待抓取小球中最大的编号
     */
    static int cnt = 0;
    static Stack<Integer> s = new Stack<Integer>();
    public static void kase3(int curnum, int curmaxv,  int maxnum, int maxv){
        if(curnum == maxnum){
            cnt++;
            System.out.println(s);
            return;
        }
        for(int i = curmaxv + 1; i <= maxv; i++){ // i <= maxv - maxnum + curnum + 1
            s.push(i);
            kase3(curnum + 1, i, maxnum, maxv);
            s.pop();
        }
    }

    Set<String> combination(int m, int[] numbers, int d) {
        Set<String> set = new HashSet<>();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < m; j++) {
                if(j == i) continue;
                for (int k = 1; k < m; k++) {
                    if(i==k || j==k) continue;
                    int[] arr = new int[]{numbers[i], numbers[j], numbers[k]};
                    Arrays.sort(arr);
                    int a0 = arr[0];
                    int a1 = arr[1];
                    int a2 = arr[2];
                    if (a2- a1 <= d && a1 - a0 <= d && a2-a0 <=d) {
                        String sb = new StringBuffer().append(a0).append(' ').append(a1).append(' ').append(a2).toString();
                        if (!set.contains(sb)) {
                            set.add(sb);
                        }
                    }
                }
            }
        }
        return set;
    }

    public static void kaseArr(int curnum, int curIndex,  int maxnum, int maxIndex){
        if(curnum == maxnum){
            if(s.get(1)-s.get(0)>d || s.get(2) - s.get(1) > d){

            }else {
                cnt++;
            }
            return;
        }
        for(int i = curIndex; i <= maxIndex; i++){
            s.push(arr[i]);
            kaseArr(curnum + 1, i+1, maxnum, maxIndex);
            s.pop();
        }
    }

    static void k3() {
        Stack<Integer> stack = new Stack<>();
        for (int x : arr) {
            if (stack.size() == 3) {
                System.out.println(stack);
                stack.pop();
                cnt++;
            }else {
                stack.push(x);
            }
        }
        if (stack.size() == 3) {
            System.out.println(stack);
            cnt++;
        }
    }


    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int a = in.nextInt();
        int b = in.nextInt();
        d = b;
        arr = new int[a];
        for (int i = 0; i < a; i++) {
            arr[i] = in.nextInt();
        }
        k3();
//        kaseArr( 0, 0, 3, a-1);
        System.out.println(cnt);
    }
}

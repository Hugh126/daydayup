package interview.hw;

import java.util.ArrayList;
import java.util.List;

/**
 * 根据输入的正整数输出其所有质因子，按从小到大的顺序排列，包括重复的因子
 */
public class T1 {

    public static void main(String[] args) {
        // Math.round是向正无穷收缩，正值会四舍五入
//        System.out.println(Math.round(-5.4));
        getPrimeFactors(180).stream().forEach(System.out::println);
    }

    public static List getPrimeFactors(int x) {
        List<Integer> list = new ArrayList<>();
        for(int i=2;i*i<=x ;i++) {
            while (x%i == 0) {
                list.add(i);
                x /= i;
            }
        }
        if (x > 1) {
            list.add(x);
        }
        return list;
    }
}

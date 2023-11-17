package wangzhen.other;

import cn.hutool.core.util.RandomUtil;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * @Author hugh
 * @Description 蓄水池抽样
 *
 * 不知采用总数n，从中等概率(m/m)取出m个样本
 * 算法复杂度 O(n)
 *
 * @Date 2022/1/24
 */
public class ReservoirSampling {


    static ArrayList<Integer> step(ArrayList<Integer> startList, Integer item) {
        int len = startList.size();
        int i = RandomUtil.randomInt(len);
        System.out.println("数组大小=" + len  + ", 随机数=" + i);
        startList.add(item);
        startList.remove(i+1);
        return startList;
    }


    public static void main(String[] args) {
        ArrayList<Integer> start0 = Lists.newArrayList(1, 2, 3);

        Scanner scanner = new Scanner(System.in);
        System.out.println("--start");
        int i = 0;
        while (scanner.hasNextInt()) {
            Integer num = scanner.nextInt();
            System.out.println("输入数字：" + num);
            start0 = step(start0, num);
            if (i++ >= 6) {
                break;
            }
        }
        System.out.println(start0.stream().map(a -> a + "").collect(Collectors.joining(",", "", "")));
    }
}

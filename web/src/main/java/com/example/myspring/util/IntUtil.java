package com.example.myspring.util;

/**
 * @author hugh
 * @Title: IntUtil
 * @ProjectName springboottest
 * @Description: TODO
 * @date 2018/10/811:38
 */
public class IntUtil {

    /**
     * 两个整数通过异或实现互换
     * @param a
     * @param b
     */
    public static void exchangeInt(int a, int b) {
            if(a == b) return; //防止&a，&b指向同一个地址；那样结果会错误。
            a ^= b;
            b ^= a;
            a ^= b;
    }

    /**
     * 求整数二进制表示中1的个数
     * @param n
     * @return
     */
    public static int get1LengthOfInt(int n) {
        int num = 0;
        while (n != 0) {
            n &= n-1;
            num ++;
        }
        return num;
    }
}

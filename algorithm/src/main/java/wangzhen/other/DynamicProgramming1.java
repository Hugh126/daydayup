package wangzhen.other;

/**
 * 问题描述:
 * 假设你有n个大白兔奶糖，每次最多拿走一个或者两个，你有多少种不同的方法拿完全部的大白兔奶糖？（注意求解方法的时间复杂度）
 *
 * 输入描述:
 * 大白兔奶糖的个数n (1<=n<=50)
 *
 * 输出描述:
 * 拿完全部的大白兔奶糖的方法数
 *
 * 输入样例:
 *  2
 *
 * 输出样例:
 * 2
 *
 https://zhuanlan.zhihu.com/p/365698607
 *
 */



public class DynamicProgramming1 {


    public static int calc(int n) {
        if (n==1) {
            return 1;
        }
        if (n==2) {
            return 2;
        }
        int a = 1;
        int b = 2;
        int temp = 0;
        for(int i=3;i<=n;i++) {
            temp = (a+b) % 1000000007;
            a = b;
            b= temp;
        }
        return temp;
    }



    public static void main(String[] args) {
        System.out.println(calc(50));
    }

}

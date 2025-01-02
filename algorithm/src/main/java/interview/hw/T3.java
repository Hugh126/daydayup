package interview.hw;

/**
 *
 * LEETCODE
 * 121. 买卖股票的最佳时机
 *
 * 给定一个数组 prices ，它的第 i 个元素 prices[i] 表示一支给定股票第 i 天的价格。
 *
 * 你只能选择 某一天 买入这只股票，并选择在 未来的某一个不同的日子 卖出该股票。设计一个算法来计算你所能获取的最大利润。
 *
 * 返回你可以从这笔交易中获取的最大利润。如果你不能获取任何利润，返回 0 。
 *
 *
 *
 * 示例 1：
 *
 * 输入：[7,1,5,3,6,4]
 * 输出：5
 * 解释：在第 2 天（股票价格 = 1）的时候买入，在第 5 天（股票价格 = 6）的时候卖出，最大利润 = 6-1 = 5 。
 *      注意利润不能是 7-1 = 6, 因为卖出价格需要大于买入价格；同时，你不能在买入前卖出股票。
 * 示例 2：
 *
 * 输入：prices = [7,6,4,3,1]
 * 输出：0
 * 解释：在这种情况下, 没有交易完成, 所以最大利润为 0。
 *
 */

public class T3 {

    public static void main(String[] args) {
        T3 t = new T3();
        System.out.println(t.maxProfit(new int[]{4,2,1,7}));
    }

    public int maxProfit(int[] prices) {
        int[] result = new int[prices.length + 1];
        // 如果低点没抓住，不可能获取最高收益
        int minTemp = prices[0];
        for(int i=0;i<prices.length;i++) {
            if (i==0){
                result[0] = maxI(prices, 0);
            }else {
                // 等于优化重复最低点
                if (prices[i] >= minTemp) {
                    result[i] = 0;
                }else {
                    minTemp = prices[i];
                    result[i] = Math.max(result[i-1], maxI(prices, i));
                }

            }
        }
        int t=0;
        for(int j=0;j<result.length;j++) {
            if (result[j]>t) {
                t = result[j];
            }
        }
        return t;
    }


    public int maxI(int[] arr, int i) {
        int z=0;
        for(int k=i;k<arr.length;k++) {
            int d = arr[k] - arr[i];
            if (d > z) {
                z = d;
            }
        }
        return z;
    }
}

package activity;

import java.util.Random;


/**
 * 蓄水池抽样
 */
public class ReservoirSampling {
    private static final Random random = new Random();

    // 使用蓄水池抽样算法来确定是否中奖
    private static boolean isWinner(int sampleSize, int k) {
        boolean[] selected = new boolean[sampleSize]; // 标记已选择的样本
        for (int i = 0; i < k; i++) {
            int idx = random.nextInt(sampleSize - i); // 从剩余样本中随机选择一个
            int count = 0;
            for (int j = 0; j < sampleSize; j++) {
                if (!selected[j]) {
                    count++;
                    if (count == idx + 1) {
                        selected[j] = true;
                        break;
                    }
                }
            }
        }
        return selected[random.nextInt(sampleSize)]; // 返回最后一个选中的样本是否中奖
    }

    public static void main(String[] args) {
        int sampleSize = 10000; // 样本总数
        int k = 5000; // 中奖人数
        double winProbability = (double) k / sampleSize; // 中奖概率

        int winners = 0; // 中奖人数统计

        // 从样本中动态抽取中奖人数
        for (int i = 0; i < sampleSize; i++) {
            System.out.println(String.format("第%d个样本", i+1));
            if (isWinner(sampleSize, k)) {
                winners++;
            }
        }

        System.out.println("中奖概率: " + winProbability);
        System.out.println("实际中奖人数: " + winners);
    }
}

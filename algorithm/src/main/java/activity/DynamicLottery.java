package activity;

import java.util.Random;


/**
 * 动态调整抽奖率
 */
public class DynamicLottery {

    private static final double TARGET_WIN_RATE = 0.5; // 目标中奖率

    public static void main(String[] args) {
        foo1();

        System.out.println("\n---------------------\n");

        foo2();

    }

    private static  void foo2() {
        // 设置中奖概率
        double winningProbability = 0.5;

        // 参与抽奖的人数
        int participants = 100000;

        // 计算中奖人数
        int winners = 0;
        Random random = new Random();
        for (int i = 0; i < participants; i++) {
            // 生成0到1之间的随机数，如果小于中奖概率，则中奖
            if (random.nextDouble() < winningProbability) {
                winners++;
            }
        }

        // 输出中奖人数和中奖率
        System.out.println("中奖人数: " + winners);
        System.out.println("中奖率: " + ((double) winners / participants));
    }



    private static  void foo1() {
        Random random = new Random();
        int totalParticipants = 0;
        int winnersCount = 0;

        // 模拟抽奖过程
        int totalUsers = 100000;
        while (totalUsers-- >= 0) {
            totalParticipants++;
            if (isWinner(random, winnersCount, totalParticipants)) {
                winnersCount++;
//                System.out.println("第 " + totalParticipants + " 位参与者中奖了！");
            }
//            if (winnersCount == totalParticipants / 2) {
//                System.out.println("总共参与了 " + totalParticipants + " 人，达到了50%的中奖率。");
//                break;
//            }
        }

        System.out.println("总共中奖人数=" + winnersCount);
    }


    private static boolean isWinner(Random random, int winnersCount, int totalParticipants) {
        double currentWinRate = (double) winnersCount / totalParticipants;
        double targetWinRate = TARGET_WIN_RATE;

        // 动态调整中奖概率，使得总体中奖率维持在50%附近
        double adjustment = Math.abs(targetWinRate - currentWinRate);
        if (currentWinRate < targetWinRate) {
            return random.nextDouble() < (0.5 + adjustment);
        } else {
            return random.nextDouble() < (0.5 - adjustment);
        }
    }
}

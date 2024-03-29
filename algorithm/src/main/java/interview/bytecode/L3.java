package interview.bytecode;

/**
 * @Description 雀魂启动
 * @Date 2023/8/23 11:09
 * @Created by hugh
 * 简化麻将规则，判断是否听牌，如果听牌输出和牌数字
 * mABC + nDDD + XX (m,n>=0)
 */
//根据游戏简化了一下规则发明了一种新的麻将，只留下一种花色，并且去除了一些特殊和牌方式（例如七对子等），具体的规则如下：
//
//        总共有36张牌，每张牌是1~9。每个数字4张牌。
//        你手里有其中的14张牌，如果这14张牌满足如下条件，即算作和牌
//        14张牌中有2张相同数字的牌，称为雀头。
//        除去上述2张牌，剩下12张牌可以组成4个顺子或刻子。顺子的意思是递增的连续3个数字牌（例如234,567等），刻子的意思是相同数字的3个数字牌（例如111,777）

//        输入描述：
//        输入只有一行，包含13个数字，用空格分隔，每个数字在1~9之间，数据保证同种数字最多出现4次。
//        输出描述：
//        输出同样是一行，包含1个或以上的数字。代表他再取到哪些牌可以和牌。若满足条件的有多种牌，请按从小到大的顺序输出。若没有满足条件的牌，请输出一个数字0
//        示例1
//        输入例子：
//        1 1 1 2 2 2 5 5 5 6 6 6 9
//        输出例子：
//        9
//    输入例子：
//            1 1 1 1 2 2 3 3 5 6 7 8 9
//            输出例子：
//            4 7
public class L3 {

    public static void main(String[] args) {
        
    }
}

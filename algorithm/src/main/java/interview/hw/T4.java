package interview.hw;

/**
 *

 LEETCODE 392. 判断子序列

 给定字符串 s 和 t ，判断 s 是否为 t 的子序列。

 字符串的一个子序列是原始字符串删除一些（也可以不删除）字符而不改变剩余字符相对位置形成的新字符串。（例如，"ace"是"abcde"的一个子序列，而"aec"不是）。

 进阶：

 如果有大量输入的 S，称作 S1, S2, ... , Sk 其中 k >= 10亿，你需要依次检查它们是否为 T 的子序列。在这种情况下，你会怎样改变代码？
 */
public class T4 {

    public static void main(String[] args) {
        System.out.println(isSubsequence("ace", "abcde"));;
        System.out.println(isSubsequence("aec", "abcde"));;
    }

    /**
     * 双指针
     * @param s
     * @param t
     * @return
     */
    public static boolean isSubsequence(String s, String t) {
        if(s.length()>t.length()) {
            return false;
        }
        char[] sArr = s.toCharArray();
        char[] tArr = t.toCharArray();
        int i=0, j = 0 ;
        for(;i<s.length() && j<t.length();) {
            if (sArr[i] == tArr[j]) {
                i++;
            }
            j++;
        }
        if (i==s.length()) {
            return true;
        }else {
            return false;
        }
    }

}

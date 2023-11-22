package interview.bytecode;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @Description
 * @Date 2023/8/21 15:26
 * @Created by hugh
 *
输入描述：
第一行包括一个数字N，表示本次用例包括多少个待校验的字符串。

后面跟随N行，每行为一个待校验的字符串。
输出描述：
N行，每行包括一个被修复后的字符串。
 */
public class L1 {

    private static void repair1(char[] arr, int start, int end) {
        if(end <=  (arr.length - 1)) {
            if (arr[start] == arr[start + 1] && arr[start + 1] == arr[start + 2] ) {
                arr[start]= '_';
            }
        }
    }


    private static void repair2(char[] arr, int start, int end) {
        if(end <=  (arr.length - 1)) {
            if (arr[start] == arr[start + 1] && arr[start + 2] == arr[start + 3] ) {
                arr[start + 2]= '_';
            }
        }
    }

    private static String convert(char[] chars) {
        StringBuffer sb = new StringBuffer();
        for (char x : chars) {
            if (x != '_') {
                sb.append(x);
            }
        }
        return sb.toString();
    }

    private static String repairStr(String str) {
        char[] chars = str.toCharArray();
        if (chars.length == 3) {
            repair1(chars, 0, 3);
            return convert(chars);
        }
        if (chars.length == 4) {
            repair1(chars, 0, 3);
            repair1(chars, 1, 4);
            repair2(chars, 0, 4);
            return convert(chars);
        }
        for (int i = 0; i+3 < chars.length; i++) {
            repair1(chars, i, i+2);
            repair2(chars, i, i+3);
        }
        repair1(chars, chars.length-3, chars.length-1);
        return convert(chars);
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        int a = in.nextInt();
//        System.out.println(a);
        List<String> list = new ArrayList<>(a);
        int i = 0;
        while (in.hasNext()) { // 注意 while 处理多个 case
            list.add(in.next());
            if (++i == a) {
                break;
            }
        }
        list.forEach(x -> System.out.println(repairStr(x)));
    }
}

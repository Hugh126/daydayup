package algo.other.str;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DoubleKill {

    private static int setLen = 0;


    private static int calc(int aCnt, int tCnt) {
        if (aCnt <= 0 ) {
            return tCnt;
        }
        if (setLen < 26) {
            if (aCnt <= 2) {
                return tCnt + 1;
            }else{
                setLen ++;
                aCnt = aCnt -2;
                return calc(aCnt, tCnt+1);
            }
        }else{
            return tCnt + aCnt;
        }
    }

    public static int minOperations (String str) {
        int total = 0;
        char[] arr = str.toCharArray();
        Map<Character, Integer> map = new HashMap<>();
        Set<Character> set = new HashSet<>();
        for(char x : arr) {
            set.add(x);
            map.compute(x, (m,n) -> {
                if(n==null) {
                    n = 1;
                }else{
                    n = map.get(m) + 1;
                }
                return n;
            });
        }
        setLen = set.size();
        for(Map.Entry<Character, Integer> item : map.entrySet()) {
            Integer cnt = item.getValue();
            if (set.contains(item.getKey())) {
                cnt = cnt -1;
            }
            if (cnt > 0) {
                total += calc(cnt, 0);
            }
        }
        return  total;
    }

    // 54
    public static void main(String[] args) {
        String str = "eueyeuvdeoyuveoyvyvecevveocedcddvouvudvuuuoydeucvecdycdodcdcdvecooeecdycycydecuc";
        System.out.println(minOperations(str));
    }

}

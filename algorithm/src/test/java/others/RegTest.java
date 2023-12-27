package others;

import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description
 * @Date 2023/12/11 16:23
 * @Created by hugh
 */
public class RegTest {

    @Test
    void name1() {

    }

    public static void main(String[] args) {
        Pattern regex= Pattern.compile(".*?(金额|费率|服务费|佣金|比例|补贴|实付|实收|应得|费用|基数)$");
        try (Scanner scanner = new Scanner(System.in)){
            while (true) {
                String str = scanner.nextLine();
                if (StringUtils.isBlank(str)) {
                    break;
                }
                Matcher m = regex.matcher(str);
                System.out.println("str=" + str + " m=" + m.matches());
            }
            System.out.println("---test end---");
        }
    }
}

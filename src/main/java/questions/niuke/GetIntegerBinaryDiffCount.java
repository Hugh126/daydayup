package questions.niuke;

import util.IntUtil;

/**
 * @author hugh
 * @Title: GetIntegerBinaryDiffCount
 * @ProjectName
 * @Description: XIAOMIBISHI
 * @date 2018/10/89:17
 */
public class GetIntegerBinaryDiffCount {

    private int a;
    private int b;

    public GetIntegerBinaryDiffCount(int a, int b) {
        this.a = a;
        this.b = b;
    }

    public int getBinaryDiffCount() {
        // 获取结果
        int result = a ^ b;
        // 结果中1的个数
        return IntUtil.get1LengthOfInt(result);
    }

}

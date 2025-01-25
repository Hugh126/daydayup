package activity;

import com.google.common.hash.Hashing;
import java.nio.charset.StandardCharsets;

/**
 * 短链设计
 *
 * MurmurHash算法的初步使用
 *
 */
public class MurmurHashToBase62 {
    private static final String BASE62_CHARSET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    public static void main(String[] args) {
        // 输入长链接
        String longUrl = "https://example.com/some/very/long/url/for/testing";
      
        // 计算 32 位 MurmurHash
        int hash32 = Hashing.murmur3_32().hashString(longUrl, StandardCharsets.UTF_8).asInt();
        // MurmurHash 32-bit (Decimal): -1888812516
        System.out.println("MurmurHash 32-bit (Decimal): " + hash32);

        // 转换为正数（避免负值影响进制转换）
        long unsignedHash32 = hash32 & 0xFFFFFFFFL;

        // 转换为 62 进制
        String base62Hash = convertToBase62(unsignedHash32);
        // MurmurHash 32-bit (Base62): 2cpym4
        System.out.println("MurmurHash 32-bit (Base62): " + base62Hash);
    }

    // 将数字转换为 62 进制字符串
    private static String convertToBase62(long value) {
        StringBuilder base62 = new StringBuilder();
        while (value > 0) {
            int index = (int) (value % 62);
            base62.append(BASE62_CHARSET.charAt(index));
            value /= 62;
        }
        // 返回结果（注意：需要反转顺序）
        return base62.reverse().toString();
    }

}
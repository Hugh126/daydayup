package wangzhen.a18_hashtable;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @Description
 * @Date 2023/10/30 15:02
 * @Created by hugh
 */
public class JDKHashTool {

    public static byte[] sha256(String str) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hash = md.digest(str.getBytes(StandardCharsets.UTF_8));
        return hash;
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {
        byte[] hash1 = sha256("Hello World");
        byte[] hash2 = sha256("Hello World");
        System.out.println("Hash value: " + bytesToHex(hash1));
        System.out.println(new String(hash1).equals(new String(hash2)));
    }
}

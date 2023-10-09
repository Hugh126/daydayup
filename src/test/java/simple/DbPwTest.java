package simple;

import com.alibaba.druid.filter.config.ConfigTools;
import org.junit.Test;

/**
 * @Description  Druid加密密码 解密
 * @Date 2023/9/8 11:15
 * @Created by hugh
 */
public class DbPwTest {

    @Test
    public void encode() throws Exception {
        String key = ConfigTools.encrypt("HDoeqoey272ldy2dlwu29");
        System.out.println(key);
        System.out.println(ConfigTools.decrypt(key));
    }

    @Test
    public void test1() throws Exception {
        System.out.println(ConfigTools.decrypt("MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBALEgip2oWw6i2Pn1RNqNoHCibDGhX/XFAuzDY70eBwj6OLjFPak+b6sYpdwotJnAGoPNz/Kl+jzbOVMveJJ9I/0CAwEAAQ==", "Q4rPsjlY1R03ifijmjTQQOXWvWx7sRISb3U/8R9ybk9clhGmJ1kYAPsNmh9wvnc4UFvpS7UZg0HpmHbsr5wDJw=="));
    }

    @Test
    public void test2() {
        System.out.println("http://127.0.0.1:8076/tenant/userFile/download//1697160084264046592\\运维.txt".replaceAll("//", "/").replaceAll("\\\\", "\\/"));
    }
}

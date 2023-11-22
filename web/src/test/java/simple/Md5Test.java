package simple;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.MD5;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.util.DigestUtils;

import java.io.UnsupportedEncodingException;

@Slf4j
public class Md5Test {

    @Test
    public void test1() throws UnsupportedEncodingException {
        String md5Str = DigestUtils.md5DigestAsHex("123456".getBytes());
        log.info(md5Str);

        String s = SecureUtil.md5("123456");
        log.info(s);

        MD5 md5 = new MD5();
        String s1 = md5.digestHex("123456");
        log.info(s1);


    }
}

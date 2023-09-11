package util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Description
 * @Date 2023/9/7 13:26
 * @Created by hugh
 */
class HttpClientUtilTest {

    @Test
    void get() {
        HttpClientUtil.get("https://erptest.helensbar.com/ayplothome/logo.png", null);
    }
}
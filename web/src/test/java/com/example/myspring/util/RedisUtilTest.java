package com.example.myspring.util;

import org.junit.jupiter.api.Test;
import redis.clients.jedis.Jedis;

/**
 * @Description
 * @Date 2023/9/5 16:22
 * @Created by hugh
 */
class RedisUtilTest {

    @Test
    void client0() {
        Jedis jedis = new Jedis("apn1-well-sailfish-34224.upstash.io", 34224);
        jedis.auth("9304457c9de84f47939d7f12a98261ac");
        jedis.set("foo", "bar11");
        String value = jedis.get("foo");
        System.out.println(value);
    }

}
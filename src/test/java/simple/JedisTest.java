package simple;

import cn.hutool.json.JSONUtil;
import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Pipeline;

public class JedisTest {

    JedisPool jedisPool;

    @Before
    public void init() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPool = new JedisPool(jedisPoolConfig, "localhost", 6379, 6000);
    }

    @Test
    public void test1() {
        try(Jedis jedis = jedisPool.getResource()){
            System.out.println(jedis.get("Coupon_2318"));
        }
    }

    @Test
    public void piplineTest() {
        try(Jedis redis = jedisPool.getResource()){
            redis.flushDB();//清空第0个库所有数据

            long start = System.currentTimeMillis();
            for (int i = 0; i < 100000; i++) {
                redis.set("key_" + i, i+ ""); //循环执行10000条数据插入redis
            }
            long end = System.currentTimeMillis();
            System.out.println("正常插入" + redis.dbSize() + "数据, 花费时间:" + (end - start) + " ms");

            redis.flushDB();//清空第0个库所有数据
            long startPipe = System.currentTimeMillis();
            Pipeline pipe = redis.pipelined();

            for (int i = 0; i < 100000; i++) {
                pipe.set("key_" + i, i+ ""); //将命令封装到PIPE对象，此时并未执行，还停留在客户端
            }
            pipe.sync(); //将封装后的PIPE一次性发给redis
            long endPipe = System.currentTimeMillis();
            System.out.println("使用Pipleline插入" + redis.dbSize() + "数据, 花费时间:" + (endPipe- startPipe) + " ms");
        }
    }

    @Test
    public void bitMapTest() {
        String[] arr0 = "menu".split("\\.");
        String[] arr1 = "menu.class".split("\\.");
        String[] arr = "menu.class.type".split("\\.");
        System.out.println(JSONUtil.toJsonStr(arr0));
        System.out.println(JSONUtil.toJsonStr(arr1));
        System.out.println(JSONUtil.toJsonStr(arr));

        System.out.println(StringUtils.substringAfter("menu.class.type", "."));
        System.out.println(StringUtils.substringBefore("menu", "."));
        System.out.println(StringUtils.substringBefore("menu.class.type", "."));
    }
}

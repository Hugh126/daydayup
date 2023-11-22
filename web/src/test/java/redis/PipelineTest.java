package redis;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Pipeline;

import java.util.ArrayList;
import java.util.List;

/**
 * http://www.redis.cn/topics/pipelining.html
 */
@Slf4j
public class PipelineTest {

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
        }finally {
            Jedis redis2 = jedisPool.getResource();
            redis2.flushDB();
            redis2.close();
        }
    }


    private List<Integer> getStepList(int start, int end, int step) {
        List<Integer> list = new ArrayList<>();
        int k = (end - start)/step;
        for (int i=1; i<=k;i++) {
            list.add(start + i*step);
        }
        list.add(0, start);
        list.add(end);
        return list;
    }

    @Test
    public void test3() {
        List<Integer> stepList = getStepList(80000000, 99700190, 10000);
        System.out.println(stepList.size());
    }
}

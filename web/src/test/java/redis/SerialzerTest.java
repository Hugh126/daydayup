package redis;

import org.junit.Test;
import org.springframework.data.redis.serializer.StringRedisSerializer;

public class SerialzerTest {

    @Test
    public void test1() {
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        String deserialize = stringRedisSerializer.deserialize(null);
        System.out.println(deserialize);
    }
}

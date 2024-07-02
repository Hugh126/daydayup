package spring.summary;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class CustomDeserializer implements Deserializer<Object> {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Class<?> targetType;

    public CustomDeserializer(Class<?> targetType) {
        this.targetType = targetType;
    }

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public Object deserialize(String topic, byte[] data) {
        if (data == null) {
            return null;
        }

        try {
            // 尝试反序列化为JSON对象
            return objectMapper.readValue(data, targetType);
        } catch (Exception e) {
            try {
                // 如果失败，尝试反序列化为字符串
                return new String(data, "UTF-8");
            } catch (Exception ex) {
                throw new SerializationException("Error deserializing message", ex);
            }
        }
    }

    @Override
    public void close() {
    }
}

package spring.summary;


import lombok.Data;

@Data
public class KafkaMessage<T> {
    private String messageId;
    private long timestamp;
    private String source;
    private T payload;
}

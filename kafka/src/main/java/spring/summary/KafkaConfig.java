package spring.summary;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class KafkaConfig {

    @Value("${spring.kafka.template.default-topic}")
    private String topic;

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootStrapServers;

    @Value("${spring.kafka.consumer.group-id}")
    private String groupId;

    // 不同应用的生产者ID必须不同
    @Value("${spring.kafka.producer.transactional-id}")
    private String transactionalId;

    @Value("${spring.kafka.producer.transaction-id-prefix}")
    private String transactionPrefix;
}

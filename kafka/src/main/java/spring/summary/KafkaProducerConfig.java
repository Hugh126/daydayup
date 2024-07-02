package spring.summary;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.transaction.KafkaTransactionManager;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

    @Autowired
    private KafkaConfig kafkaConfig;

    @Bean
    public ProducerFactory<String, KafkaMessage> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfig.getBootStrapServers());
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        // 启用幂等性
        configProps.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);
        // 启用事务
        configProps.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, kafkaConfig.getTransactionalId());
        return new DefaultKafkaProducerFactory<>(configProps);
    }


    @Bean
    public KafkaTemplate<String, KafkaMessage> kafkaTemplate() {
        KafkaTemplate<String, KafkaMessage> kafkaTemplate = new KafkaTemplate<>(producerFactory());
        kafkaTemplate.setTransactionIdPrefix(kafkaConfig.getTransactionPrefix());
        return kafkaTemplate;
    }




    @Bean
    public KafkaTransactionManager<String, KafkaMessage> kafkaTransactionManager(ProducerFactory<String, KafkaMessage> producerFactory) {
        return new KafkaTransactionManager<>(producerFactory);
    }
}

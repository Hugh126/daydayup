package spring.summary;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service("consumerService")
@Slf4j
public class KafkaConsumerService {

    private static AtomicInteger version = new AtomicInteger(100);

    @KafkaListener(topics = "#{kafkaConfig.topic}", groupId = "#{kafkaConfig.groupId}", containerFactory = "kafkaListenerContainerFactory")
    // 使用事务，确保消息处理和偏移量提交在同一事务中
    @Transactional
    public void listen(List<ConsumerRecord<String, Object>> recordList, Acknowledgment acknowledgment) {

        // 处理消息的业务逻辑
        try {
            version.incrementAndGet();
            for (ConsumerRecord<String, Object> record : recordList) {
                Object message = record.value();
                if (message instanceof KafkaMessage) {
                    KafkaMessage<?> kafkaMessage = (KafkaMessage<?>) message;
                    processKafkaMessage(kafkaMessage);
                } else if (message instanceof String) {
                    processStringMessage((String) message);
                } else {
                    // 未知类型处理
                    processUnknownMessage(message);
                }
            }
            // 手动提交偏移量
            acknowledgment.acknowledge();
        } catch (Exception e) {
            // 处理异常情况，例如记录日志或重试机制
            e.printStackTrace();
        }
    }


    private void processKafkaMessage(KafkaMessage<?> message) {
        // 处理KafkaMessage类型的消息
        log.warn("[自定义消息消费]" + message.toString());
    }

    private void processStringMessage(String message) {
        // 处理String类型的消息
        log.warn("[String类型消息消费]" + message + " version= " + version.get());
    }

    private void processUnknownMessage(Object message) {
        // 处理未知类型的消息
        log.warn("[未知类型消息消费]" + message.toString());
    }


}

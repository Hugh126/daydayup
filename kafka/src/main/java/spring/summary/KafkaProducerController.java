package spring.summary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/kafka")
public class KafkaProducerController {

    @Autowired
    private KafkaConfig kafkaConfig;

    private final KafkaTemplate<String, KafkaMessage> kafkaTemplate;

    @Autowired
    public KafkaProducerController(KafkaTemplate<String, KafkaMessage> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping("/publish")
    @Transactional
    public String sendMessage(@RequestBody KafkaMessage message) {
        kafkaTemplate.send(kafkaConfig.getTopic(), String.valueOf(message.hashCode()), message);
        return "Message sent to Kafka topic";
    }

}

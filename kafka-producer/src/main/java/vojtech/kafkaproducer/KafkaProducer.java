package vojtech.kafkaproducer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
public class KafkaProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducer.class);

    @Value("${custom.kafka.producer.topic.name}")
    private String topicName;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String message){
        kafkaTemplate.send(topicName, message);
        LOGGER.info(String.format("Published message: \"%s\"", message));
    }
}

package vojtech.kafkaconsumer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import vojtech.model.Person;

@Component
public class TestProducer {
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducer.class);

    @Autowired
    private KafkaTemplate<String, Person> kafkaTemplate;

    public void sendMessage(String topic, Person person){
        kafkaTemplate.send(topic, person);
        LOGGER.info(String.format("\n   Published message: \"%s\"\n   on topic: \"%s\"", person, topic));
    }
}

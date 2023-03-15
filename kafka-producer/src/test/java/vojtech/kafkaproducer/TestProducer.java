package vojtech.kafkaproducer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import vojtech.model.Person;

@Service
public class TestProducer {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestProducer.class);

    @Autowired
    @Qualifier("embeddedTemplate")
    private KafkaTemplate<String, Person> embeddedKafkaTemplate;

    public void sendMessage(String topic, Person person){
        embeddedKafkaTemplate.send(topic, person);
        LOGGER.info("\n   Published message: \"" + person + "\"\n   on topic: \"" + topic + "\"");
    }
}

package vojtech.kafkaproducer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import vojtech.model.Person;

@Slf4j
@Service
public class TestProducer {

    @Autowired
    @Qualifier("embeddedTemplate")
    private KafkaTemplate<String, Person> embeddedKafkaTemplate;

    public void sendMessage(String topic, Person person){
        embeddedKafkaTemplate.send(topic, person);
        log.info("\n   Published message: \"" + person + "\"\n   on topic: \"" + topic + "\"");
    }
}

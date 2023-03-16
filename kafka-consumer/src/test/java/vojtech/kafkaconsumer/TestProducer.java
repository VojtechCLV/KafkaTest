package vojtech.kafkaconsumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import vojtech.model.Person;

@Slf4j
@Component
public class TestProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void send(String topic, String payload) {
        log.info("sending payload='{}' to topic='{}'", payload, topic);
        kafkaTemplate.send(topic, payload);
    }
}















/*@Slf4j
@Component
@Profile({ "test" })
public class TestProducer {

    @Autowired
    private KafkaTemplate<String, Person> kafkaTemplate;

    public void sendMessage(String topic, Person person){
        kafkaTemplate.send(topic, person);
        log.info("\n   Published message: \"" + person + "\"\n   on topic: \"" + topic + "\"");
    }
}*/

package vojtech.kafkaconsumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import vojtech.model.Person;

@Slf4j
@Service
@Profile({ "test" })
public class TestProducer {

    @Autowired
    @Qualifier("embeddedTemplate")
    private KafkaTemplate<String, Person> embeddedTemplate;

    public void send(String topic, Person person) {
        log.info("sending payload='{}' to topic='{}'", person.toString(), topic);
        embeddedTemplate.send(topic, person);
    }
}

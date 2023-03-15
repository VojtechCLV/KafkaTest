package vojtech.kafkaproducer.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import vojtech.model.Person;
import vojtech.kafkaproducer.util.PersonGenerator;

@Service
public class KafkaProducerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducerService.class);

    @Autowired
    private KafkaTemplate<String, Person> kafkaTemplate;

    @Value("${spring.kafka.topic.name}")
    private String topic;

    public void autoSend() throws InterruptedException {
        for (int i=0; i<10; i++) {
            try {
                Thread.sleep(10000);
                Person person = new Person(
                        PersonGenerator.randomName(),
                        PersonGenerator.randomAge());
                sendMessage(topic, person);
            }
            catch (Exception e) {
                LOGGER.error(String.valueOf(e.getCause()));
            }
        }
    }

    public void sendMessage(String topic, Person person){

        kafkaTemplate.send(topic, person);
        LOGGER.info("\n   Published message: \"" + person.toString() + "\"" +
                "\n   Person's name: \"" + person.getName() + "\"" +
                "\n   Person's age: \"" + person.getAge() + "\"" +
                "\n   on topic: \"" + topic + "\"");
    }
}

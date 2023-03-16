package vojtech.kafkaproducer.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import vojtech.model.Person;
import vojtech.kafkaproducer.util.PersonGenerator;

@Slf4j
@Service
public class KafkaProducerService {

    @Autowired
    private KafkaTemplate<String, Person> kafkaTemplate;

    @Value("${spring.kafka.topic.name}")
    private String topic;

    public void autoSend(int messages, long millis) throws InterruptedException {
        for (int i=0; i<messages; i++) {
            try {
                Thread.sleep(millis);
                Person person = new Person(
                        PersonGenerator.randomName(),
                        PersonGenerator.randomAge());
                sendMessage(topic, person);
            }catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                log.info(e.getMessage());
                break;
            }
        }
    }

    public void sendMessage(String topic, Person person){

        kafkaTemplate.send(topic, person);
        log.info("\n   Published message: \"" + person.toString() + "\"" +
                "\n   Person's name: \"" + person.getName() + "\"" +
                "\n   Person's age: \"" + person.getAge() + "\"" +
                "\n   on topic: \"" + topic + "\"");
    }
}

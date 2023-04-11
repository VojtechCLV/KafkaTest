package vojtech.kafkaproducer.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import vojtech.model.Person;
import vojtech.kafkaproducer.util.PersonGenerator;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
                Person person = PersonGenerator.getRandomPerson();
                sendMessage(topic, person);
            }catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                log.info(e.getMessage());
                break;
            }
        }
    }

    public void sendMessage(String topic, Person person){

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss.SSS");

        kafkaTemplate.send(topic, person);
        log.info("\n   Published message: \"" + person.toString() + "\"" +
                "\n   Person's name: \"" + person.getName() + "\"" +
                "\n   Person's age: \"" + person.getAge() + "\"" +
                "\n   on topic: \"" + topic + "\"" +
                "\n   Current time after sending: " + dtf.format(LocalDateTime.now()) +
                "\n   Current time in millis: " + System.currentTimeMillis());
    }
}

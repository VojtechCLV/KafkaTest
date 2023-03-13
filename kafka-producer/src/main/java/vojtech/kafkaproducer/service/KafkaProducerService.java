package vojtech.kafkaproducer.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import vojtech.model.Person;

import java.util.Random;

@Service
public class KafkaProducerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducerService.class);

    @Autowired
    private KafkaTemplate<String, Person> kafkaTemplate;

    @Value("${spring.kafka.topic.name}")
    private String topic;

    public void autoSend() throws InterruptedException {
        for (int i=0; i<10; i++) {
            Random random = new Random();

            String randomString = random.ints(97, 123)
                    .limit(8)
                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                    .toString();
            String generatedName = randomString.substring(0, 1).toUpperCase() + randomString.substring(1);
            try {
                Thread.sleep(10000);
                Person person = new Person(generatedName, 20 + random.nextInt(60));
                sendMessage(topic, person);
            }
            catch (Exception e) {
                LOGGER.error(String.valueOf(e.getCause()));
            }
        }
    }

    public void sendMessage(String topic, Person person){

        kafkaTemplate.send(topic, person);
        LOGGER.info(String.format("\n   Published message: \"%s\"" +
                "\n   Person's name: \"%s\"" +
                "\n   Person's age: \"%s\"" +
                "\n   on topic: \"%s\"", person.toString(), person.getName(), person.getAge(), topic));
    }
}

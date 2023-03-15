package vojtech.kafkaconsumer.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import vojtech.model.Person;

import java.util.concurrent.CountDownLatch;

@Service
public class KafkaConsumerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumerService.class);

    private CountDownLatch latch = new CountDownLatch(1);

    private String message;

    public void resetLatch() {
        latch = new CountDownLatch(1);
    }

    public CountDownLatch getLatch() {
        return latch;
    }

    public String getMessage() {
        return message;
    }

    @KafkaListener(topics = "${spring.kafka.topic.name}",
            containerFactory = "kafkaListenerContainerFactory",
            groupId = "${spring.kafka.consumer.group-id}")
    public void read(ConsumerRecord<String, Person> record){
        String key=record.key();
        Person person=record.value();
        System.out.println("Avro message received: \n key: " + key + "\n value : " + person.toString());
        System.out.println("Person's name is : " + person.getName());
        System.out.println("Person's age is : " + person.getAge());
    }
}

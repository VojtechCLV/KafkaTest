package vojtech.kafkaconsumer.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import vojtech.kafkaconsumer.PersonRepository;
import vojtech.kafkaconsumer.StandalonePersonRepository;
import vojtech.kafkaconsumer.entity.PersonEntity;
import vojtech.kafkaconsumer.entity.StandalonePersonEntity;
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

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private StandalonePersonRepository standPersonRepository;

    @KafkaListener(topics = "${spring.kafka.topic.name}",
            containerFactory = "kafkaListenerContainerFactory",
            groupId = "${spring.kafka.consumer.group-id}")
    public void read(ConsumerRecord<String, Person> record){
        String key=record.key();
        Person person=record.value();
        System.out.println("Avro message received: \n key: " + key + "\n value : " + person.toString());
        System.out.println("Person's name is : " + person.getName());
        System.out.println("Person's age is : " + person.getAge());


/*        PersonEntity personEntity = new PersonEntity();
        personEntity.setName(person.getName());
        personEntity.setAge(person.getAge());
        System.out.println("\n   Entity name : " + personEntity.getName());
        System.out.println("\n   Entity age : " + personEntity.getAge());
        System.out.println("\n   Entity : " + personEntity);
        personRepository.save(personEntity);
        System.out.println("\n   FIND ALL : " + personRepository.findAll());*/


        StandalonePersonEntity standPerson = new StandalonePersonEntity();
        standPerson.setName(person.getName());
        standPerson.setAge(person.getAge());
        System.out.println("\n   Entity name : " + standPerson.getName());
        System.out.println("\n   Entity age : " + standPerson.getAge());
        System.out.println("\n   Entity : " + standPerson);
        standPersonRepository.save(standPerson);
        System.out.println("\n   FIND ALL : " + standPersonRepository.findAll());


    }
}

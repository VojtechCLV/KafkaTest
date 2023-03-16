package vojtech.kafkaconsumer.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import vojtech.kafkaconsumer.StandalonePersonRepository;
import vojtech.kafkaconsumer.entity.StandalonePersonEntity;
import vojtech.kafkaconsumer.mapper.PersonMapper;
import vojtech.model.Person;

import java.util.concurrent.CountDownLatch;

@Slf4j
@Service
public class KafkaConsumerService {
    private final PersonMapper mapper = Mappers.getMapper(PersonMapper.class);

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
    private StandalonePersonRepository standPersonRepository;

    @KafkaListener(topics = "${spring.kafka.topic.name}",
            containerFactory = "kafkaListenerContainerFactory",
            groupId = "${spring.kafka.consumer.group-id}")
    public void read(ConsumerRecord<String, Person> record){
        String key=record.key();
        Person person=record.value();
        log.info("Avro message received: \n key: " + key + "\n value : " + person.toString());

        // Load the mapper to map received person into repository-friendly entity
        StandalonePersonEntity personDst = mapper.sourceToDestination(person);

        // Saving received person into repository
        standPersonRepository.save(personDst);

        // Check for people named Honza saved in the session
        if (standPersonRepository.findByName("Honza").size() == 0) {
            log.info("\n   Didn't find any Honza... \uD83D\uDCA9");
        } else {
            log.info("\n   Found " + standPersonRepository.findByName("Honza").size()
                    + " instance(s) of Honza! \uD83C\uDF89");
        }
    }
}

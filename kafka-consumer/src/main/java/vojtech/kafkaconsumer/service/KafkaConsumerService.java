package vojtech.kafkaconsumer.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import vojtech.kafkaconsumer.authentication.AppUserRepository;
import vojtech.kafkaconsumer.repository.PersonRepository;
import vojtech.kafkaconsumer.entity.PersonEntity;
import vojtech.kafkaconsumer.mapper.PersonMapper;
import vojtech.kafkaconsumer.util.Benchmark;
import vojtech.model.Person;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Slf4j
@Service
public class KafkaConsumerService {
    private final PersonMapper mapper = Mappers.getMapper(PersonMapper.class);

    @Autowired
    private PersonRepository standPersonRepository;

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    PersonRepository personRepository;

    @KafkaListener(topics = "${spring.kafka.topic.name}",
            containerFactory = "kafkaListenerContainerFactory",
            groupId = "${spring.kafka.consumer.group-id}")
    public void read(ConsumerRecord<String, Person> message){

        int millisSinceCreation = (int) (System.currentTimeMillis() - message.timestamp());

        String key=message.key();
        Person person=message.value();

        log.info("Avro message received: \n key: {}\n value : {}", key, person.toString());

        // Map received person into repository-friendly entity
        PersonEntity personDst = mapper.sourceToDestination(person);

        // Saving received person into repository
        standPersonRepository.save(personDst);

        Benchmark.addToDurationList(millisSinceCreation);
    }
}

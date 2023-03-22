package vojtech.kafkaconsumer.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import vojtech.kafkaconsumer.repository.PersonRepository;
import vojtech.kafkaconsumer.entity.PersonEntity;
import vojtech.kafkaconsumer.mapper.PersonMapper;
import vojtech.model.Person;

@Slf4j
@Service
public class KafkaConsumerService {
    private final PersonMapper mapper = Mappers.getMapper(PersonMapper.class);

    @Autowired
    private PersonRepository standPersonRepository;

    @KafkaListener(topics = "${spring.kafka.topic.name}",
            containerFactory = "kafkaListenerContainerFactory",
            groupId = "${spring.kafka.consumer.group-id}")
    public void read(ConsumerRecord<String, Person> message){
        String key=message.key();
        Person person=message.value();
        log.info("Avro message received: \n key: " + key + "\n value : " + person.toString());

        // Load the mapper to map received person into repository-friendly entity
        PersonEntity personDst = mapper.sourceToDestination(person);

        // Saving received person into repository
        standPersonRepository.save(personDst);

        log.info("Saved " + personDst + " into database");
    }
}

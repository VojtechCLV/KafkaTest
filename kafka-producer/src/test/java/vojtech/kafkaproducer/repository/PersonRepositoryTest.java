package vojtech.kafkaproducer.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import vojtech.kafkaproducer.PersonRepository;
import vojtech.kafkaproducer.entity.PersonEntity;

import java.util.List;

@Slf4j
@DataJpaTest
@ActiveProfiles({ "test" })
public class PersonRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PersonRepository personRepository;

    @Test
    public void testSavePerson(){

        PersonEntity personEntity = new PersonEntity();
        personEntity.setName("Horacio");
        personEntity.setAge(61);

        PersonEntity personEntity2 = new PersonEntity();
        personEntity2.setName("Grogu");
        personEntity2.setAge(297);

        log.info("\n   findAll BEFORE SAVE = " + personRepository.findAll());

        log.info("\n   saving " + personEntity);
        personRepository.save(personEntity);

        log.info("\n   findAll1 = " + personRepository.findAll());

        personRepository.save(personEntity2);

        log.info("\n   findAll2 = " + personRepository.findAll());

        log.info("\n   findByName = " + personRepository.findByName(personEntity.getName()));

        List<PersonEntity> result = personRepository.findByName("Grogu");

        log.info("\n   Result = " + result);

        //TODO Gotta fix finding by name

    }
}
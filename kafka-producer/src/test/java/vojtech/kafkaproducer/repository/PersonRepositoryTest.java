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
class PersonRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PersonRepository personRepository;

    @Test
    void testSavePerson(){

        PersonEntity personEntity = new PersonEntity();
        personEntity.setName("Grogu");
        personEntity.setAge(61);

        PersonEntity personEntity2 = new PersonEntity();
        personEntity2.setName("Grogu2");
        personEntity2.setAge(62);

        log.info("\n   saving " + personEntity);
        personRepository.save(personEntity);

        log.info("\n   saving " + personEntity2);
        personRepository.save(personEntity2);

        log.info("\n   findAll = " + personRepository.findAll());

        log.info("\n   findByName = " + personRepository.findByName(personEntity.getName()));

        List<PersonEntity> result = personRepository.findByName("Grogu");

        log.info("\n   Result = " + result);

        List<String> result2 = personRepository.findNames();

        log.info("\n   Result = " + result2);

        //TODO Gotta fix finding by name

    }
}
package vojtech.kafkaproducer.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import vojtech.kafkaproducer.PersonRepository;
import vojtech.kafkaproducer.entity.PersonEntity;
import vojtech.model.Person;
//import vojtech.kafkaproducer.entity.Person2;

import java.util.List;

@DataJpaTest
@ActiveProfiles({ "test" })
public class PersonRepositoryTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonRepositoryTest.class);

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PersonRepository personRepository;

    @Test
    //@Rollback(value = false)
    public void testSavePerson(){

        PersonEntity personEntity = new PersonEntity();
        personEntity.setName("Horacio");
        personEntity.setAge(61);

        PersonEntity personEntity2 = new PersonEntity();
        personEntity2.setName("Grogu");
        personEntity2.setAge(297);

        LOGGER.info("\n   findAll BEFORE SAVE = " + personRepository.findAll());


        LOGGER.info("\n   saving " + personEntity);
        personRepository.save(personEntity);

/*        LOGGER.info("\n   Name = " + personEntity.getName());
        LOGGER.info("\n   Age = " + personEntity.getAge());*/

        LOGGER.info("\n   findAll1 = " + personRepository.findAll());

        personRepository.save(personEntity2);

        LOGGER.info("\n   findAll2 = " + personRepository.findAll());

        LOGGER.info("\n   findByName = " + personRepository.findByName(personEntity.getName()));

        List<PersonEntity> result = personRepository.findByName("Grogu");

        LOGGER.info("\n   Result = " + result);
    }
}
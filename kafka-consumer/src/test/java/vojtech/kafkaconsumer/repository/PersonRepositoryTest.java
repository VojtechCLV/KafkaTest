package vojtech.kafkaconsumer.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import vojtech.kafkaconsumer.TestPerson;
import vojtech.kafkaconsumer.entity.PersonEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@DataJpaTest
@ActiveProfiles({ "test" })
@TestPropertySource(locations="classpath:test.properties")
class PersonRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PersonRepository personRepository;

    @Test
    void whenSavedToRepository_thenReadableFromRepository() throws InterruptedException {

        String NAME_ONE = TestPerson.getTestPerson().getName();
        int AGE_ONE = TestPerson.getTestPerson().getAge();

        String NAME_TWO = "Rick";
        int AGE_TWO = 57;

        log.info("\n   Creating entities to test, verifying values...");
        PersonEntity personEntity = new PersonEntity(NAME_ONE,AGE_ONE);
        PersonEntity personEntity2 = new PersonEntity(NAME_TWO,AGE_TWO);

        assertEquals("Person [id=null, name=" + NAME_ONE + ", age=" + AGE_ONE + "]",personEntity.toString(),
                "String representation of entity does not match test data, ID should be null");
        assertEquals(NAME_ONE,personEntity.getName(),
                "Failed to get saved name from entity correctly");
        assertEquals(AGE_ONE,personEntity.getAge(),
                "Failed to get saved age from entity correctly");

        log.info("\n   Saving first entity: {}", personEntity);
        personRepository.save(personEntity);

        log.info("\n   Saving second entity: {}", personEntity2);
        personRepository.save(personEntity2);

        log.info("\n   Checking IDs of saved entities");
        assertEquals(1, personEntity.getId(),
                "Saving should assign ID with value 1");
        assertEquals(2, personEntity2.getId(),
                "Saving should assign ID with value 2");

        log.info("\n   Result of FindAll: " + personRepository.findAll());
        assertEquals(2,personRepository.findAll().size(),
                "Should have found exactly 2 matches");

        log.info("\n   Verifying results of findByName query for {}", NAME_ONE);
        List<PersonEntity> resultList = personRepository.findByName(NAME_ONE);
        assertEquals(1,resultList.size(),
                "Should have found exactly 1 match");
        assertEquals(NAME_ONE,resultList.get(0).getName(),
                "Result should have contained one entity with name " + NAME_ONE);
        assertEquals("Person [id=1, name=" + NAME_ONE + ", age=" + AGE_ONE + "]",resultList.get(0).toString(),
                "Mismatch of string representation or test data values");

    }
}
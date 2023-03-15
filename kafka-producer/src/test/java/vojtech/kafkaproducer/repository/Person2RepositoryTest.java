package vojtech.kafkaproducer.repository;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import vojtech.kafkaproducer.entity.Person2;
import vojtech.kafkaproducer.person2.Person2Repository;

@DataJpaTest
@ActiveProfiles({ "test" })
public class Person2RepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private Person2Repository person2Repo;

    private static final Logger LOGGER = LoggerFactory.getLogger(Person2RepositoryTest.class);

    @Test
    public void testFindByFirstNameMethod() {
        Person2 person2 = new Person2("Vojtech", "Moravec",28);
        entityManager.persist(person2);

        List<Person2> result = person2Repo.findByFirstName(person2.getFirstName());

        LOGGER.info("\n   findAll = " + person2Repo.findAll());


        LOGGER.info("\n   Result = " + result);
        assertEquals("[Person [ ID = 1, First Name = Vojtech, Last Name = Moravec, Age = 28 ]]",
                result.toString());
    }
}

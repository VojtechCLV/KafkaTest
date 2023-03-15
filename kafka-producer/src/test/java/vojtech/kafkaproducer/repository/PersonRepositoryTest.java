package vojtech.kafkaproducer.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import vojtech.model.Person;

@DataJpaTest
public class PersonRepositoryTest {
/*    @Autowired
    private PersonCrudRepository personCrudRepository;

    @Test
    @Rollback(value = false)
    public void testSavePerson(){

        Person person = new Person("Horacio", 48);
        personCrudRepository.save(person);

        Assertions.assertThat(person.getAge()).isGreaterThan(0);
    }*/

}
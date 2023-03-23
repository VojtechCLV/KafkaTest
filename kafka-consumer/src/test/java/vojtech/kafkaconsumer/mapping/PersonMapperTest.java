package vojtech.kafkaconsumer.mapping;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import vojtech.kafkaconsumer.TestPerson;
import vojtech.kafkaconsumer.entity.PersonEntity;
import vojtech.kafkaconsumer.mapper.PersonMapper;
import vojtech.kafkaconsumer.util.PersonGenerator;
import vojtech.model.Person;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
class PersonMapperTest {

    private final PersonMapper mapper = Mappers.getMapper(PersonMapper.class);

    @Test
    void givenSrcToDst_whenMaps_thenCorrect() {
        Person personSrc = new Person(TestPerson.getTestPerson().getName(), TestPerson.getTestPerson().getAge());
        PersonEntity personDst = mapper.sourceToDestination(personSrc);

        log.info("\n   SRC NAME: " + personSrc.getName() + "\n   SRC AGE: " + personSrc.getAge());
        log.info("\n   DST NAME: " + personDst.getName() + "\n   DST AGE: " + personDst.getAge());

        assertEquals(personSrc.getName(), personDst.getName());
        assertEquals(personSrc.getAge(), personDst.getAge());
    }

    @Test
    void givenDstToSrc_whenMaps_thenCorrect() {
        PersonEntity personDst = new PersonEntity();
        personDst.setName(PersonGenerator.randomName());
        personDst.setAge(PersonGenerator.randomAge());
        Person personSrc = mapper.destinationToSource(personDst);

        log.info("\n   DST NAME: " + personDst.getName() + "\n   DST AGE: " + personDst.getAge());
        log.info("\n   SRC NAME: " + personSrc.getName() + "\n   SRC AGE: " + personSrc.getAge());

        assertEquals(personDst.getName(), personSrc.getName());
        assertEquals(personDst.getAge(), personSrc.getAge());
    }
}
package vojtech.kafkaconsumer;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import vojtech.kafkaconsumer.entity.StandalonePersonEntity;
import vojtech.kafkaconsumer.mapper.PersonMapper;
import vojtech.model.Person;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
public class PersonMapperTest {


    private final PersonMapper mapper
            = Mappers.getMapper(PersonMapper.class);

    @Test
    public void givenSrcToDst_whenMaps_thenCorrect() {
        Person personSrc = new Person();
        personSrc.setName("Test Person Name");
        personSrc.setAge(99);
        StandalonePersonEntity personDst = mapper.sourceToDestination(personSrc);

        log.info("\n   SRC NAME: " + personSrc.getName() + "\n   SRC AGE: " + personSrc.getAge());
        log.info("\n   DST NAME: " + personDst.getName() + "\n   DST AGE: " + personDst.getAge());

        assertEquals(personSrc.getName(), personDst.getName());
        assertEquals(personSrc.getAge(), personDst.getAge());
    }

    @Test
    public void givenDstToSrc_whenMaps_thenCorrect() {
        StandalonePersonEntity personDst = new StandalonePersonEntity();
        personDst.setName("Test StandalonePersonEntity Name");
        personDst.setAge(11);
        Person personSrc = mapper.destinationToSource(personDst);

        log.info("\n   DST NAME: " + personDst.getName() + "\n   DST AGE: " + personDst.getAge());
        log.info("\n   SRC NAME: " + personSrc.getName() + "\n   SRC AGE: " + personSrc.getAge());

        assertEquals(personDst.getName(), personSrc.getName());
        assertEquals(personDst.getAge(), personSrc.getAge());
    }
}
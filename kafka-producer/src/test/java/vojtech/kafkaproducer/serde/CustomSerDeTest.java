package vojtech.kafkaproducer.serde;

//import org.junit.jupiter.api.*;
import org.junit.jupiter.api.*;
//import org.junit.runner.RunWith;
//import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import vojtech.kafkaproducer.util.CustomDeserializer;
import vojtech.kafkaproducer.util.CustomSerializer;
import vojtech.model.Person;
import vojtech.kafkaproducer.TestPerson;

import java.util.Arrays;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DirtiesContext
@SpringBootTest
//@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles({ "test" })
@TestPropertySource(locations="classpath:test.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CustomSerDeTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomSerDeTest.class);

    @Value("${test.kafka.topic.name}")
    private String topic;

    @Autowired
    private CustomSerializer serializer;

    @Autowired
    private CustomDeserializer deserializer;

/*    private final String testName = "Vojtech";
    private final Integer testAge = 28;
    private final Person sentPerson = new Person(testName,testAge);*/

    Person testPerson = TestPerson.getTestPerson();

    @BeforeEach
    void setup() {
        serializer = new CustomSerializer();
        deserializer = new CustomDeserializer();
    }

    @Test
    @Order(1)
    public void givenJSONEncoder_whenSerialized_PersonGetsSerialized(){
        byte[] data = serializer.serialize(topic, testPerson);
        LOGGER.info("   SERIALIZATION COMPLETE");
        assertTrue(Objects.nonNull(data));
        String partOfSerialized = "123, 34, 110, 97, 109, 101, 34, 58, 34";
        assertTrue(Arrays.toString(data).contains(partOfSerialized));
        assertTrue(data.length > 0);
    }

    @Test
    @Order(2)
    public void givenJSONDecoder_whenDeserialized_PersonGetsDeserialized() {
        byte[] data = serializer.serialize(topic, testPerson);
        Person deSerializedPerson = deserializer.deserialize(topic,data);
        LOGGER.info("   DESERIALIZED DATA: " + deSerializedPerson);
        assertEquals(deSerializedPerson, testPerson);
        assertEquals(deSerializedPerson.getName().toString(), testPerson.getName());
    }
}

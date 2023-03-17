package vojtech.kafkaproducer.serde;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import vojtech.kafkaproducer.util.CustomDeserializer;
import vojtech.kafkaproducer.util.CustomSerializer;
import vojtech.model.Person;
import vojtech.kafkaproducer.TestPerson;

import java.util.Arrays;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
@DirtiesContext
@SpringBootTest
@ActiveProfiles({ "test" })
@TestPropertySource(locations="classpath:test.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CustomSerDeTest {

    @Value("${test.kafka.topic.name}")
    private String topic;

    @Autowired
    private CustomSerializer serializer;

    @Autowired
    private CustomDeserializer deserializer;

    Person testPerson = TestPerson.getTestPerson();

    @BeforeEach
    void setup() {
        serializer = new CustomSerializer();
        deserializer = new CustomDeserializer();
    }

    @Test
    @Order(1)
    void givenJSONEncoder_whenSerialized_PersonGetsSerialized(){
        byte[] data = serializer.serialize(topic, testPerson);
        log.info("   SERIALIZATION COMPLETE");
        assertTrue(Objects.nonNull(data));
        String partOfSerialized = "123, 34, 110, 97, 109, 101, 34, 58, 34";
        assertTrue(Arrays.toString(data).contains(partOfSerialized));
        assertTrue(data.length > 0);
    }

    @Test
    @Order(2)
    void givenJSONDecoder_whenDeserialized_PersonGetsDeserialized() {
        byte[] data = serializer.serialize(topic, testPerson);
        Person deSerializedPerson = deserializer.deserialize(topic,data);
        log.info("   DESERIALIZED DATA: " + deSerializedPerson);
        assertEquals(deSerializedPerson, testPerson);
        assertEquals(deSerializedPerson.getName(), testPerson.getName());
    }
}

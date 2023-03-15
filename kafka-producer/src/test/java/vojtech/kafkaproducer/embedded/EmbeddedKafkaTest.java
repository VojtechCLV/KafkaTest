package vojtech.kafkaproducer.embedded;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import vojtech.kafkaproducer.TestConsumer;
import vojtech.kafkaproducer.TestProducer;
import vojtech.kafkaproducer.util.AvroDeserializer;
import vojtech.kafkaproducer.util.AvroSerializer;
import vojtech.model.Person;

import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.TimeUnit;


@DirtiesContext
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@EmbeddedKafka(partitions = 1, brokerProperties = { "listeners=PLAINTEXT://localhost:9092", "port=9092" })
@ActiveProfiles({ "test" })
@TestPropertySource(locations="classpath:test.properties")
class EmbeddedKafkaTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(EmbeddedKafkaTest.class);

/*	@Autowired
	@Qualifier("embeddedTemplate")*/
	public KafkaTemplate<String, Person> embeddedKafkaTemplate;

	@Autowired
	//@Qualifier("personConsumerFactory")
	private TestConsumer consumer;

	@Autowired
	private TestProducer producer;

	@Autowired
	private AvroSerializer serializer;

	@Autowired
	private AvroDeserializer deserializer;

	@Value("${test.kafka.topic.name}")
	private String topic;

	private final String testName = "Vojtech";
	private final Integer testAge = 28;
	private final Person sentPerson = new Person(testName,testAge);

	@BeforeEach
	void setup() {
		consumer.resetLatch();
		serializer = new AvroSerializer();
		deserializer = new AvroDeserializer();
	}

	@Test
	public void givenEmbeddedKafkaBroker_whenSendingWithCustomSerDe_thenMessagePojoReceived() throws Exception {
		producer.sendMessage(topic, sentPerson);

		LOGGER.info("\n   Publishing message: " + sentPerson + " \n   Topic: " + topic);

		boolean messageConsumed = consumer.getLatch()
				.await(10, TimeUnit.SECONDS);
		assertTrue(messageConsumed);
		assertEquals(testName, consumer.getPersonPayload().getName().toString());
		assertEquals(testAge, consumer.getPersonPayload().getAge());
	}

	@Test
	public void givenJSONEncoder_whenSerialized_PersonGetsSerialized(){
		byte[] data = serializer.serialize(topic, sentPerson);
		LOGGER.info("   SERIALIZATION COMPLETE");
		assertTrue(Objects.nonNull(data));
		String partOfSerialized = "123, 34, 110, 97, 109, 101, 34, 58, 34";
		assertTrue(Arrays.toString(data).contains(partOfSerialized));
		assertTrue(data.length > 0);
	}

	@Test
	public void givenJSONDecoder_whenDeserialized_PersonGetsDeserialized() {
		byte[] data = serializer.serialize(topic,sentPerson);
		Person deSerializedPerson = deserializer.deserialize(topic,data);
		LOGGER.info("   DESERIALIZED DATA: " + deSerializedPerson);
		assertEquals(deSerializedPerson, sentPerson);
		assertEquals(deSerializedPerson.getName().toString(), sentPerson.getName());
	}
}
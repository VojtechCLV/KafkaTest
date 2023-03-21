package vojtech.kafkaconsumer.embedded;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import vojtech.kafkaconsumer.TestConsumer;
import vojtech.kafkaconsumer.TestPerson;
import vojtech.kafkaconsumer.TestProducer;
import vojtech.kafkaconsumer.repository.PersonRepository;
import vojtech.model.Person;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@Slf4j
@SpringBootTest(classes = {
		EmbeddedConfig.class,
		PersonRepository.class,
		ByteConsumerService.class,
		TestProducer.class,
		TestConsumer.class
})
@DirtiesContext
@ActiveProfiles({ "test" })
@EnableKafka
@EmbeddedKafka(partitions = 1, brokerProperties = { "listeners=PLAINTEXT://localhost:9092", "port=9092" })
@TestPropertySource(locations="classpath:test.properties")
class EmbeddedKafkaTest {

	@Autowired
	private TestConsumer avroConsumer;

	@Autowired
	private ByteConsumerService byteConsumer;

	@Autowired
	private TestProducer producer;

	@Value("${test.kafka.topic.name}")
	private String topic;

	@BeforeEach
	void setup() {
		avroConsumer.resetLatch();
		byteConsumer.resetLatch();
	}

	@Test
	void givenEmbeddedKafkaBroker_whenSendingWithSimpleProducer_thenMessageReceived()
			throws Exception {
		Person testPerson = TestPerson.getTestPerson();

		String bytePayload, avroPayload;

		log.info("   Sending Test Person " + testPerson + " to test topic...");
		producer.send(topic, testPerson);

		boolean messageConsumed = avroConsumer.getLatch().await(10, TimeUnit.SECONDS);
		assertTrue(messageConsumed, "Avro Message has not been consumed in time");
		avroPayload = avroConsumer.getPayload().toString();

		assertTrue(avroPayload.contains(testPerson.toString()),
				"Person info not found in message. Actual Avro payload: " + avroPayload);

		assertEquals(testPerson.toString(), avroPayload,
				"Deserialized message does not match expected string");
		log.info("   Message successfully consumed by Avro Consumer");

		boolean byteMessageConsumed = byteConsumer.getLatch().await(10, TimeUnit.SECONDS);
		assertTrue(byteMessageConsumed, "Byte message has not been consumed in time");
		bytePayload = Arrays.toString(byteConsumer.getPayload());

		String expectedBytePayloadStart = "[0, 0, 0, 0, 1, ";
		assertTrue(bytePayload.contains(expectedBytePayloadStart),
				"Should start with 0 magic byte, and then 4 bytes for schema ID like 0001, " +
						"followed by serialized message. Actual byte payload: " + bytePayload);
	}
}

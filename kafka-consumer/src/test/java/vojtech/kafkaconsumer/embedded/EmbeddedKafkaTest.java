package vojtech.kafkaconsumer.embedded;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import lombok.extern.slf4j.Slf4j;
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

	@Value("${test.topic}")
	private String topic;

	@Test
	void givenEmbeddedKafkaBroker_whenSendingWithSimpleProducer_thenMessageReceived()
			throws Exception {
		Person testPerson = new Person("Tester",1);

		String bytePayload, avroPayload;

		producer.send(topic, testPerson);

		boolean messageConsumed = avroConsumer.getLatch().await(10, TimeUnit.SECONDS);
		assertTrue(messageConsumed);

		avroPayload = avroConsumer.getPayload().toString();
		bytePayload = Arrays.toString(byteConsumer.getPayload());

		assertTrue(avroConsumer.getPayload().toString().contains(testPerson.toString()),
				"Person info not found in message");

		assertEquals(testPerson.toString(), avroPayload,
				"Deserialized message does not match expected string");

		String expectedBytePayload = "[0, 0, 0, 0, 1, 12, 84, 101, 115, 116, 101, 114, 2]";
		assertEquals(expectedBytePayload, bytePayload,
				"Should start with 0 magic byte, and then 4 bytes for schema ID like 0001, " +
						"followed by serialized message");
	}
}

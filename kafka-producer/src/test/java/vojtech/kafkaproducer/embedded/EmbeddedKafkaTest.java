package vojtech.kafkaproducer.embedded;

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
import vojtech.kafkaproducer.TestConsumer;
import vojtech.kafkaproducer.TestPerson;
import vojtech.kafkaproducer.TestProducer;
import vojtech.model.Person;

import java.util.concurrent.TimeUnit;

@Slf4j
@SpringBootTest(classes = {
		EmbeddedConfig.class,
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
	private TestConsumer consumer;

	@Autowired
	private TestProducer producer;

	@Value("${test.kafka.topic.name}")
	private String topic;

	Person testPerson = TestPerson.getTestPerson();

	@BeforeEach
	void setup() {
		consumer.resetLatch();
	}

	@Test
	void givenEmbeddedKafkaBroker_whenSendingWithCustomSerDe_thenMessagePojoReceived() throws Exception {
		producer.send(topic, testPerson);

		log.info("\n   Publishing message: " + testPerson + " \n   Topic: " + topic);

		boolean messageConsumed = consumer.getLatch()
				.await(10, TimeUnit.SECONDS);
		assertTrue(messageConsumed);
		assertEquals(testPerson.getName(), consumer.getPayload().getName());
		assertEquals(testPerson.getAge(), consumer.getPayload().getAge());
	}

	// TODO Find out why are we stuck in JoinGroup loop all of a sudden...
}

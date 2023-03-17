package vojtech.kafkaconsumer.embedded;

import static org.junit.jupiter.api.Assertions.assertTrue;

import io.confluent.kafka.schemaregistry.client.SchemaRegistryClient;
import io.confluent.kafka.schemaregistry.testutil.MockSchemaRegistry;
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

import java.util.concurrent.TimeUnit;

// TODO
// Enable Schema Registry mocking so specific 5 Avro bytes are present in integration testing
// without actual Schema Registry present on network

@Slf4j
@SpringBootTest(classes = {
		EmbeddedConfig.class,
		PersonRepository.class,
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

	@Value("${test.topic}")
	private String topic;

	@Test
	void givenEmbeddedKafkaBroker_whenSendingWithSimpleProducer_thenMessageReceived()
			throws Exception {
		Person testPerson = new Person("Tester",1);

		producer.send(topic, testPerson);

		boolean messageConsumed = consumer.getLatch().await(10, TimeUnit.SECONDS);
		assertTrue(messageConsumed);
		assertTrue(consumer.getPayload().toString().contains(testPerson.toString()));
	}
}

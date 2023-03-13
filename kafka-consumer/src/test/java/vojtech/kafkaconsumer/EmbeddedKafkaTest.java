package vojtech.kafkaconsumer;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import vojtech.kafkaconsumer.service.KafkaConsumerService;

@SpringBootTest
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = { "listeners=PLAINTEXT://localhost:9092", "port=9092" })
class EmbeddedKafkaTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(EmbeddedKafkaTest.class);

	@Autowired
	public KafkaTemplate<String, String> template;

	@Autowired
	private KafkaConsumerService consumer;

	@Autowired
	private TestProducer producer;

	@Value("${spring.kafka.topic.name}")
	private String topic;

	private final ObjectMapper objectMapper = new ObjectMapper();

	@BeforeEach
	void setup() {
		consumer.resetLatch();
	}

	/*@Test
	public void givenEmbeddedKafkaBroker_whenSendingWithDefaultTemplate_thenMessageReceived() throws Exception {
		//String sentMessage = "Sending using default template";

		Person testPerson = new Person("Vojtech",28);

		template.send(topic, testPerson);

		LOGGER.info(String.format("\n   Publishing message: %s \n   Topic: %s", testPerson, topic));

		boolean messageConsumed = consumer.getLatch()
				.await(10, TimeUnit.SECONDS);
		assertTrue(messageConsumed);
		assertThat(consumer.getMessage(), containsString(testPerson.getName().toString()));

	}*/

/*	@Test
	public void givenEmbeddedKafkaBroker_whenSendingWithSimpleProducer_thenMessageReceived() throws Exception {
		Person testPerson = new Person("Vojtech",28);

		LOGGER.info(String.format("\n   Publishing message: %s \n   Topic: %s", testPerson, topic));

		producer.sendMessage(topic, testPerson);

		boolean messageConsumed = consumer.getLatch()
				.await(10, TimeUnit.SECONDS);
		assertTrue(messageConsumed);
		assertThat(consumer.getMessage(), containsString((String) testPerson.getName()));
	}*/
}
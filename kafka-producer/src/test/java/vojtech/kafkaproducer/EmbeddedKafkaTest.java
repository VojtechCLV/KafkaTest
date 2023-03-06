package vojtech.kafkaproducer;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = { "listeners=PLAINTEXT://localhost:9092", "port=9092" })
class EmbeddedKafkaTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(EmbeddedKafkaTest.class);

	@Autowired
	public KafkaTemplate<String, String> template;

	@Autowired
	private TestConsumer consumer;

	@Autowired
	private KafkaProducer producer;

	@Value("${custom.kafka.topic.name}")
	private String topic;

	private final ObjectMapper objectMapper = new ObjectMapper();

	@BeforeEach
	void setup() {
		consumer.resetLatch();
	}

	@Test
	public void givenEmbeddedKafkaBroker_whenSendingWithDefaultTemplate_thenMessageReceived() throws Exception {
		String sentMessage = "Sending using default template";

		template.send(topic, sentMessage);

		LOGGER.info(String.format("\n   Publishing message: %s \n   Topic: %s", sentMessage, topic));

		boolean messageConsumed = consumer.getLatch()
				.await(10, TimeUnit.SECONDS);
		assertTrue(messageConsumed);
		assertThat(consumer.getMessage(), containsString(sentMessage));
	}

	@Test
	public void givenEmbeddedKafkaBroker_whenSendingWithSimpleProducer_thenMessageReceived() throws Exception {
		String sentMessage = "Sending using our own KafkaProducer";

		producer.sendMessage(topic, sentMessage);

		boolean messageConsumed = consumer.getLatch()
				.await(10, TimeUnit.SECONDS);
		assertTrue(messageConsumed);
		assertThat(consumer.getMessage(), containsString(sentMessage));
	}
}
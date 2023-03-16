package vojtech.kafkaconsumer;

import static org.junit.jupiter.api.Assertions.assertTrue;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import vojtech.model.Person;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@SpringBootTest
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = { "listeners=PLAINTEXT://localhost:9092", "port=9092" })
class EmbeddedKafkaTest {

	@Autowired
	private TestConsumer consumer;

	@Autowired
	private TestProducer producer;

	@Value("${test.topic}")
	private String topic;

	@Test
	public void givenEmbeddedKafkaBroker_whenSendingWithSimpleProducer_thenMessageReceived()
			throws Exception {
		String data = "Sending with our own simple KafkaProducer";

		producer.send(topic, data);

		boolean messageConsumed = consumer.getLatch().await(10, TimeUnit.SECONDS);
		assertTrue(messageConsumed);
		assertTrue(consumer.getPayload().toString().contains(data));
	}

	// TODO Fix whatever I managed to break again...
}
















/*
@Slf4j
@SpringBootTest//(classes = {EmbeddedConfig.class})
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = { "listeners=PLAINTEXT://localhost:9092", "port=9092" })
//@TestPropertySource(locations="classpath:test.properties")
@ActiveProfiles({ "test" })
class EmbeddedKafkaTest {

	@Autowired
	public KafkaTemplate<String, String> template;

*/
/*	@Autowired
	private KafkaConsumerService consumer;*//*


	@Autowired
	//@Qualifier("embeddedPersonConsumerFactory")
	private TestConsumer consumer;

	@Autowired
	private TestProducer producer;

	//@Value("${test.kafka.topic.name}")
	@Value("${spring.kafka.topic.name}")
	private String topic;

	@BeforeEach
	void setup() {
		consumer.resetLatch();
	}

	@Test
	public void givenEmbeddedKafkaBroker_whenSendingWithDefaultTemplate_thenMessageReceived() throws Exception {
		String sentMessage = "This is a test message to verify embedded kafka functionality";

		//Person testPerson = new Person("Vojtech",28);

		template.send(topic, sentMessage);

		log.info(String.format("\n   Publishing message: %s \n   Topic: %s", sentMessage, topic));

		boolean messageConsumed = consumer.getLatch()
				.await(10, TimeUnit.SECONDS);
		assertTrue(messageConsumed);
		assertTrue(consumer.getMessage().contains(sentMessage),
				"Received message does not contain test string: " + sentMessage +
						"\nReceived message: " + consumer.getMessage());

	}

*/
/*	@Test
	public void givenEmbeddedKafkaBroker_whenSendingWithSimpleProducer_thenMessageReceived() throws Exception {
		Person testPerson = new Person("Vojtech",28);

		log.info(String.format("\n   Publishing message: %s \n   Topic: %s", testPerson, topic));

		producer.sendMessage(topic, testPerson);

		boolean messageConsumed = consumer.getLatch()
				.await(10, TimeUnit.SECONDS);
		assertTrue(messageConsumed);
		assertTrue(consumer.getMessage().contains(testPerson.getName()),
				"Received message does not contain name " + testPerson.getName() +
						"\nReceived message: " + consumer.getMessage());
	}*//*

}*/

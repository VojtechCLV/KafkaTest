package vojtech.kafkaproducer.embedded;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
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
import vojtech.kafkaproducer.TestPerson;
import vojtech.kafkaproducer.TestProducer;
import vojtech.kafkaproducer.util.CustomDeserializer;
import vojtech.kafkaproducer.util.CustomSerializer;
import vojtech.model.Person;

import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.TimeUnit;


@DirtiesContext
@SpringBootTest
//@RunWith(SpringJUnit4ClassRunner.class)
@EmbeddedKafka(partitions = 1, brokerProperties = { "listeners=PLAINTEXT://localhost:9092", "port=9092" })
@ActiveProfiles({ "test" })
@TestPropertySource(locations="classpath:test.properties")
class EmbeddedKafkaTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(EmbeddedKafkaTest.class);

/*	@Autowired
	@Qualifier("embeddedTemplate")*/
	//public KafkaTemplate<String, Person> embeddedKafkaTemplate;

	@Autowired
	//@Qualifier("personConsumerFactory")
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
	public void givenEmbeddedKafkaBroker_whenSendingWithCustomSerDe_thenMessagePojoReceived() throws Exception {
		producer.sendMessage(topic, testPerson);

		LOGGER.info("\n   Publishing message: " + testPerson + " \n   Topic: " + topic);

		boolean messageConsumed = consumer.getLatch()
				.await(10, TimeUnit.SECONDS);
		assertTrue(messageConsumed);
		assertEquals(testPerson.getName(), consumer.getPersonPayload().getName().toString());
		assertEquals(testPerson.getAge(), consumer.getPersonPayload().getAge());
	}
}

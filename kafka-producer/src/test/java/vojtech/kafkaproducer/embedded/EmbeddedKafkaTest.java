package vojtech.kafkaproducer.embedded;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

import java.util.concurrent.TimeUnit;

@Slf4j
@DirtiesContext
@SpringBootTest
@EmbeddedKafka(partitions = 1, brokerProperties = { "listeners=PLAINTEXT://localhost:9092", "port=9092" })
@ActiveProfiles({ "test" })
@TestPropertySource(locations="classpath:test.properties")
class EmbeddedKafkaTest {

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

		log.info("\n   Publishing message: " + testPerson + " \n   Topic: " + topic);

		boolean messageConsumed = consumer.getLatch()
				.await(10, TimeUnit.SECONDS);
		assertTrue(messageConsumed);
		assertEquals(testPerson.getName(), consumer.getPersonPayload().getName());
		assertEquals(testPerson.getAge(), consumer.getPersonPayload().getAge());
	}

	// TODO Find out why are we stuck in loop all of a sudden...
}

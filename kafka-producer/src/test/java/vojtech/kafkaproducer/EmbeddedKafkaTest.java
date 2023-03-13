package vojtech.kafkaproducer;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
//import vojtech.kafkaproducer.util.AvroSerializer;

@SpringBootTest
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = { "listeners=PLAINTEXT://localhost:9092", "port=9092" })
class EmbeddedKafkaTest {
/*
	private static final Logger LOGGER = LoggerFactory.getLogger(EmbeddedKafkaTest.class);

	@Autowired
	public KafkaTemplate<String, Person> template;

	@Autowired
	private TestConsumer consumer;

	@Autowired
	private KafkaProducerService producer;

	@Value("${custom.kafka.topic.name}")
	private String topic;

	private final ObjectMapper objectMapper = new ObjectMapper();

	public Person sentPerson = new Person("Vojtech",28);
	public String serializedPersonJSON = "[123, 34, 110, 97, 109, 101, 34, 58, 34, 86, 111, 106, 116, 101, 99, 104, 34, 44, 34, 97, 103, 101, 34, 58, 50, 56, 125]";
	public String serializedPersonBinary = "[14, 86, 111, 106, 116, 101, 99, 104, 56]";

	@BeforeEach
	void setup() {
		consumer.resetLatch();
		serializer = new AvroSerializer();
		deSerializer = new AvroDeSerializer();
	}



	AvroSerializer serializer;
	AvroDeSerializer deSerializer;*/

/*	@Test
	public void givenEmbeddedKafkaBroker_whenSendingWithDefaultTemplate_thenMessageReceived() throws Exception {
		template.send(topic, sentPerson);

		LOGGER.info(String.format("\n   Publishing message: %s \n   Topic: %s", sentPerson, topic));

		boolean messageConsumed = consumer.getLatch()
				.await(10, TimeUnit.SECONDS);
		assertTrue(messageConsumed);
		assertThat(consumer.getMessage(), containsString("Vojtech"));
		assertThat(consumer.getMessage(), containsString("28"));
	}

	@Test
	public void givenEmbeddedKafkaBroker_whenSendingWithSimpleProducer_thenMessageReceived() throws Exception {
		producer.sendMessage(topic, sentPerson);

		LOGGER.info(String.format("\n   Publishing message: %s \n   Topic: %s", sentPerson, topic));

		boolean messageConsumed = consumer.getLatch()
				.await(10, TimeUnit.SECONDS);
		assertTrue(messageConsumed);
		assertThat(consumer.getMessage(), containsString("Vojtech"));
		assertThat(consumer.getMessage(), containsString("28"));
	}*/
/*	@Test
	public void whenSerialized_UsingJSONEncoder_PersonGetsSerialized(){
		byte[] data = serializer.serializePersonJSON(sentPerson);
		LOGGER.info("     SERIALIZATION COMPLETE");
		assertTrue(Objects.nonNull(data));
		assertEquals(serializedPersonJSON, Arrays.toString(data));
		assertTrue(data.length > 0);
	}

	@Test
	public void whenSerialized_UsingBinaryEncoder_PersonGetsSerialized(){
		byte[] data = serializer.serializePersonBinary(sentPerson);
		LOGGER.info("     SERIALIZATION COMPLETE");
		assertTrue(Objects.nonNull(data));
		assertEquals(serializedPersonBinary, Arrays.toString(data));
		assertTrue(data.length > 0);
	}

	@Test
	public void WhenDeserializeUsingJSONDecoder_thenActualAndExpectedObjectsAreEqual() {
		byte[] data = serializer.serializePersonJSON(sentPerson);
		Person deSerializedPerson = deSerializer.deSerializePersonJSON(data);
		LOGGER.info("     DESERIALIZED DATA: " + deSerializedPerson);
		assertEquals(deSerializedPerson, sentPerson);
		assertEquals(deSerializedPerson.getName().toString(), sentPerson.getName());
	}

	@Test
	public void WhenDeserializeUsingBinaryecoder_thenActualAndExpectedObjectsAreEqual() {
		byte[] data = serializer.serializePersonBinary(sentPerson);
		Person deSerializedPerson = deSerializer.deSerializePersonBinary(data);
		LOGGER.info("     DESERIALIZED DATA: " + deSerializedPerson);
		assertEquals(deSerializedPerson, sentPerson);
		assertEquals(deSerializedPerson.getName().toString(), sentPerson.getName());
	}*/
}
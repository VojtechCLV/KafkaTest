package vojtech.kafkaproducer.integration;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.awaitility.Awaitility;
import vojtech.kafkaproducer.util.PersonGenerator;
import vojtech.model.Person;
import org.junit.jupiter.api.BeforeEach;
import io.confluent.kafka.schemaregistry.client.rest.entities.SchemaString;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import com.github.tomakehurst.wiremock.client.WireMock;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching;
import static org.hamcrest.Matchers.equalTo;

@Slf4j
@AutoConfigureWireMock(port=0)
@DirtiesContext
@SpringBootTest(classes = { IntegrationConfig.class })
//@RunWith(SpringJUnit4ClassRunner.class)
@EmbeddedKafka(partitions = 1, brokerProperties = { "listeners=PLAINTEXT://localhost:9092", "port=9092" })
@ActiveProfiles({ "test" })
@TestPropertySource(locations="classpath:test.properties")
public class IntegrationTest {
/*
    private KafkaTestListener testListener;

    @Autowired
    private KafkaTemplate<String,Person> testKafkaTemplate;

    @Value("${test.kafka.topic.name}")
    private String topic;

    @Configuration
    static class TestConfig {

        @Bean
        public KafkaTestListener testListener() {
            return new KafkaTestListener();
        }
    }

    @BeforeEach
    public void setUp() throws Exception {
        testListener.counter.set(0);

        WireMock.reset();
        WireMock.resetAllRequests();
        WireMock.resetAllScenarios();
        WireMock.resetToDefault();

        registerSchema(1, topic, Person.getClassSchema().toString());
    }

    public static class KafkaTestListener {
        AtomicInteger counter = new AtomicInteger(0);

        @KafkaListener(groupId = "Test_Group",
                topics = "Test_Topic",
                containerFactory = "testKafkaListenerContainerFactory",
                autoStartup = "true")
        void receive(@Payload final Person person) {
            log.info(String.format("\n   Kafka Listener received person named %s", person.getName()));
            counter.incrementAndGet();
        }
    }

    private void registerSchema(int schemaId, String topic, String schema) throws Exception {
        stubFor(post(urlPathMatching("/subjects/" + topic + "-value"))
                .willReturn(aResponse().withStatus(200)
                .withHeader("Content-Type", "application/json")
                .withBody("{\"id\":" + schemaId + "}")));

        final SchemaString schemaString = new SchemaString(schema);
        stubFor(get(urlPathMatching("/schemas/ids/" + schemaId))
                .willReturn(aResponse().withStatus(200)
                .withHeader("Content-Type", "application/json")
                .withBody(schemaString.toJson())));
    }

    @Test
    public void testSendPerson() throws Exception {
        int totalMessages = 10;
        Thread.sleep(5000);

        try {
            for (int i=0; i<totalMessages; i++) {
                Person person = new Person("Test Person", PersonGenerator.randomAge());
                testKafkaTemplate.send(topic,person);
            }
            Awaitility.await().atMost(10, TimeUnit.SECONDS)
                    .pollDelay(100, TimeUnit.MILLISECONDS)
                    .until(testListener.counter::get, equalTo(totalMessages));
        } catch (Exception e) {
            log.error("\nOopsie\n" + e.getMessage() + e.getCause());
        }
    }*/

    //TODO Gotta fix schema registry mocking
}

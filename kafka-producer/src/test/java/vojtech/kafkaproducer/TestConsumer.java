package vojtech.kafkaproducer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import vojtech.model.Person;

import java.util.concurrent.CountDownLatch;

@Service
public class TestConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestConsumer.class);
    private CountDownLatch latch = new CountDownLatch(1);
    private Person personPayload;

    @KafkaListener(topics = "${test.kafka.topic.name}",
            //groupId = "${test.kafka.consumer.group-id}",
            groupId = "Test2_Group",
            containerFactory = "embeddedKafkaListenerContainerFactory")
    public void receive(Person incomingMessage) {
        LOGGER.info("Received message = " + incomingMessage.toString());
        personPayload = incomingMessage;
        latch.countDown();
    }

    public void resetLatch() {
        latch = new CountDownLatch(1);
    }

    public CountDownLatch getLatch() {
        return latch;
    }

    public Person getPersonPayload() {
        return personPayload;
    }
}

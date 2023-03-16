package vojtech.kafkaproducer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import vojtech.model.Person;

import java.util.concurrent.CountDownLatch;

@Slf4j
@Service
public class TestConsumer {
    private CountDownLatch latch = new CountDownLatch(1);
    private Person personPayload;

    @KafkaListener(topics = "${test.kafka.topic.name}",
            groupId = "${test.kafka.consumer.group-id}",
            containerFactory = "embeddedKafkaListenerContainerFactory")
    public void receive(Person incomingMessage) {
        log.info("Received message = " + incomingMessage.toString());
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

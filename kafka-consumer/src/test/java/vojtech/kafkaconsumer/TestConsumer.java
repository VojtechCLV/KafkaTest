package vojtech.kafkaconsumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Component
public class TestConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestConsumer.class);
    private CountDownLatch latch = new CountDownLatch(1);
    private String message;

    @KafkaListener(topics = "${custom.kafka.topic.name}",
            groupId = "${spring.kafka.consumer.group-id}")
    public void receive(String incomingMessage) {
        LOGGER.info("Received message = " + incomingMessage);
        message = incomingMessage;
        latch.countDown();
    }

    public void resetLatch() {
        latch = new CountDownLatch(1);
    }

    public CountDownLatch getLatch() {
        return latch;
    }

    public String getMessage() {
        return message;
    }
}

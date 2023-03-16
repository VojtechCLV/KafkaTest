package vojtech.kafkaconsumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import vojtech.model.Person;

import java.util.List;
import java.util.concurrent.CountDownLatch;


@Slf4j
@Component
public class TestConsumer {

    private CountDownLatch latch = new CountDownLatch(1);
    private String payload;

    @KafkaListener(topics = "${test.topic}",
    groupId = "Test_Group")
    public void receive(ConsumerRecord<?, ?> consumerRecord) {
        log.info("received payload='{}'", consumerRecord.toString());
        payload = consumerRecord.toString();
        latch.countDown();
    }

    public void resetLatch() {
        latch = new CountDownLatch(1);
    }

    public Object getPayload() {
        return payload;
    }

    public CountDownLatch getLatch() {
        return latch;
    }

    // other getters
}














/*
@Slf4j
@Service
@Profile({ "test" })
public class TestConsumer {
    private CountDownLatch latch = new CountDownLatch(1);
    private String message;

    @KafkaListener(//topics = "${test.kafka.topic.name}",
            //groupId = "${test.kafka.consumer.group-id}")
            topics = "${spring.kafka.topic.name}",
            groupId = "${spring.kafka.consumer.group-id}")
    public void receive(String incomingMessage) {
        log.info("Received message = " + incomingMessage);
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
*/

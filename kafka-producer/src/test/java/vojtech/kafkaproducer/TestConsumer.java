package vojtech.kafkaproducer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import vojtech.model.Person;

import java.util.concurrent.CountDownLatch;

@Slf4j
@Service
public class TestConsumer {

    private CountDownLatch latch = new CountDownLatch(1);
    private Person payload;

    @KafkaListener(topics = "${test.kafka.topic.name}",
            containerFactory = "embeddedKafkaListenerContainerFactory",
            groupId = "Test_Group")
    public void receive(ConsumerRecord<?, Person> consumerRecord) {
        log.info("received payload = '{}'", consumerRecord.toString());
        log.info("   VALUE: = '{}'", consumerRecord.value().toString());
        payload = consumerRecord.value();
        latch.countDown();
    }

    public void resetLatch() {
        latch = new CountDownLatch(1);
    }

    public Person getPayload() {
        return payload;
    }

    public CountDownLatch getLatch() {
        return latch;
    }
}
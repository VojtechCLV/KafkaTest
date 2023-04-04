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

    private static CountDownLatch latch = new CountDownLatch(1);
    private static Person payload;

    @KafkaListener(topics = "${test.kafka.topic.name}",
            containerFactory = "embeddedKafkaListenerContainerFactory",
            groupId = "${test.kafka.consumer.group-id}")
    public void receive(ConsumerRecord<?, Person> consumerRecord) {
        log.info("\n   RECEIVED PAYLOAD = '{}'", consumerRecord.toString());
        log.info("\n   VALUE: = '{}'", consumerRecord.value().toString());
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
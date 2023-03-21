package vojtech.kafkaconsumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import vojtech.model.Person;

import java.util.concurrent.CountDownLatch;


@Slf4j
@Service
@Profile({"test"})
public class TestConsumer {

    private CountDownLatch latch = new CountDownLatch(1);
    private String payload;

    @KafkaListener(topics = "${test.kafka.topic.name}",
            autoStartup = "true",
            containerFactory = "embeddedKafkaListenerContainerFactory",
            groupId = "Test_Group")
    public void receive(ConsumerRecord<?, Person> consumerRecord) {
        log.info("received payload = '{}'", consumerRecord.toString());
        log.info("   VALUE: = '{}'", consumerRecord.value().toString());
        payload = consumerRecord.value().toString();
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
}

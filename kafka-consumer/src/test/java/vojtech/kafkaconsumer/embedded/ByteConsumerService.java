package vojtech.kafkaconsumer.embedded;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;

@Slf4j
@Service
@Profile({"test"})
public class ByteConsumerService {

    private CountDownLatch latch = new CountDownLatch(1);
    private byte[] payload;

    @KafkaListener(topics = "${test.kafka.topic.name}",
            containerFactory = "byteKafkaListenerContainerFactory",
            groupId = "${spring.kafka.consumer.group-id}")
    public void read(ConsumerRecord<String, byte[]> record){
        payload = record.value();
        log.info("   Original Byte Array of Avro Message: {}", Arrays.toString(payload));
        byte[] cutArray = new byte[5];

        for (int i = payload.length-1, k = 4; i > 0 ; i--) {
            if (i < 5) {
                cutArray[k--] = payload[i];
            }
        }
        latch.countDown();
    }

    public CountDownLatch getLatch() {
        return latch;
    }
    public void resetLatch() {
        latch = new CountDownLatch(1);
    }
    public byte[] getPayload() {
        return payload;
    }
}

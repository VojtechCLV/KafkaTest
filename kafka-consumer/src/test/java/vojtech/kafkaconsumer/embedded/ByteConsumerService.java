package vojtech.kafkaconsumer.embedded;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

// To be used for testing once Schema Registry is mocked and Avro message contains 5 Avro bytes

@Slf4j
@Service
public class ByteConsumerService {
/*
    @KafkaListener(topics = "${spring.kafka.topic.name}",
            containerFactory = "kafkaListenerContainerFactory",
            groupId = "${spring.kafka.consumer.group-id}")
    public void read(ConsumerRecord<String, byte[]> record){
        String key=record.key();
        byte[] payload = record.value();
        long offset= record.offset();

        System.out.println("   Original Array: " + Arrays.toString(payload));

        byte[] cutArray = new byte[5];

        for (int i = payload.length-1, k = 4; i > 0 ; i--) {
            if (i < 5) {
                cutArray[k--] = payload[i];
            }
        }

        System.out.println("   Array after removal operation: " + Arrays.toString(cutArray));
    }*/
}

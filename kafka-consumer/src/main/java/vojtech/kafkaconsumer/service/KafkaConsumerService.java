package vojtech.kafkaconsumer.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import vojtech.model.Person;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class KafkaConsumerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumerService.class);

    private CountDownLatch latch = new CountDownLatch(1);

    final AtomicInteger counter = new AtomicInteger();

    private String message;

    public void resetLatch() {
        latch = new CountDownLatch(1);
    }

    public CountDownLatch getLatch() {
        return latch;
    }

    public String getMessage() {
        return message;
    }

    @KafkaListener(topics = "${spring.kafka.topic.name}",
            containerFactory = "kafkaListenerContainerFactory",
            groupId = "${spring.kafka.consumer.group-id}")
    public void read(ConsumerRecord<String, Person> record){
        String key=record.key();
        Person person=record.value();
        System.out.println("Avro message received: \n key: " + key + "\n value : " + person.toString());
        System.out.println("Person's name is : " + person.getName());
        System.out.println("Person's age is : " + person.getAge());
    }
}







/*    AvroSerializer serializer = new AvroSerializer();


    public void sendMessage(String topic, Person person){

        kafkaTemplate.send(topic, person);
        LOGGER.info(String.format("\n   Published message: \"%s\"" +
                "\n   Person's name: \"%s\"" +
                "\n   Person's age: \"%s\"" +
                "\n   on topic: \"%s\"",person.toString(), person.getName(), person.getAge(), topic));
    }

    @Value("${custom.kafka.topic.name}")
    private String topic;*/

/*    public void sendMessage(String topic, Person person){
        ListenableFuture<SendResult<String,Person>> future = (ListenableFuture<SendResult<String, Person>>) kafkaTemplate.send(topic, person);
        future.addCallback(new ListenableFutureCallback<SendResult<String, Person>>() {
            @Override
            public void onFailure(Throwable ex) {
                System.out.println("Message failed to produce");
            }

            @Override
            public void onSuccess(SendResult<String, Person> result) {
                System.out.println("Avro message successfully produced");
            }
        });

    }*/
//}

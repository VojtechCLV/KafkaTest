package vojtech.kafkaproducer.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vojtech.kafkaproducer.service.KafkaProducerService;
import vojtech.model.Person;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/kafka")
public class Controller {

    private static final Logger LOGGER = LoggerFactory.getLogger(Controller.class);

    @Value("${spring.kafka.topic.name}")
    private String topicName;

    @Autowired
    KafkaProducerService kafkaProducer;

    @PostMapping("/publish")
    public ResponseEntity<String> sendPerson(@RequestBody Person person) {
        try {
            LOGGER.info("   Sending person");
            kafkaProducer.sendMessage(topicName, person);
            return ResponseEntity.ok("Okidoki");
        } catch(Exception e) {
            LOGGER.error(e.getMessage());
            return ResponseEntity.internalServerError().body(e.getCause().toString());
        }
    }

    @PostMapping("/start")
    public ResponseEntity<String> startSending() {

        LOGGER.info("   Starting AutoSend, sending 10 messages, 10 second delay between each");
        try {
            Thread newThread = new Thread(() -> {
                try {
                    kafkaProducer.autoSend();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
            newThread.start();
            return ResponseEntity.ok("Okidoki");
        } catch(Exception e) {
            LOGGER.error(e.getMessage());
            return ResponseEntity.internalServerError().body(e.getCause().toString());
        }
    }
}

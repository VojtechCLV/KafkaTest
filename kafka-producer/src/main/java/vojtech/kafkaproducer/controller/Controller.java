package vojtech.kafkaproducer.controller;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vojtech.kafkaproducer.service.KafkaProducerService;
import vojtech.model.Person;

@OpenAPIDefinition(info = @Info(
        title = "Producer API",
        description = "API for generating and sending person data to Kafka",
        version = "2.0",
        contact = @Contact(
                name = "Vojtech Moravec",
                email = "email@moravecvoj.tech"
        ),
        license = @License(
                name = "MIT Licence",
                url = "https://opensource.org/licenses/mit-license.php"
        )))
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/kafka")
public class Controller {

    @Value("${spring.kafka.topic.name}")
    private String topicName;

    static final String OKRESPONSE = "Ok";

    @Autowired
    KafkaProducerService kafkaProducer;

    @PostMapping("/publish")
    public ResponseEntity<String> sendPerson(@RequestBody Person person) {
        try {
            log.info("   Sending person");
            kafkaProducer.sendMessage(topicName, person);
            return ResponseEntity.ok(OKRESPONSE);
        } catch(Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body(e.getCause().toString());
        }
    }

    @PostMapping("/start")
    public ResponseEntity<String> startSending(@RequestParam Integer messages, @RequestParam Long millis) {

        log.info("   Starting AutoSend, sending {} messages, {} millisecond delay between each", messages, millis);
        try {
            Thread autoSendThread = new Thread(() -> {
                try {
                    kafkaProducer.autoSend(messages,millis);
                    while (!Thread.interrupted()) {
                        Thread.yield();
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }, "autoSendThread");
            autoSendThread.start();
            return ResponseEntity.ok(OKRESPONSE);
        } catch(Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body(e.getCause().toString());
        }
    }

    @PostMapping("/stop")
    public ResponseEntity<String> stopSending() {

        try {
            log.info("Interrupting autoSend thread");
            getThreadByName("autoSendThread").interrupt();
            }
        catch(Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body(e.getCause().toString());
        }
        return ResponseEntity.ok(OKRESPONSE);
    }

    public Thread getThreadByName(String name) {
        for (Thread thread : Thread.getAllStackTraces().keySet()) {
            if (thread.getName().equals(name)) return thread;
        }
        return null;
    }

}

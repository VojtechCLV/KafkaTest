package vojtech.kafkaproducer.api;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.openapitools.api.KafkaApi;
import org.openapitools.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vojtech.kafkaproducer.mapper.PersonMapper;
import vojtech.kafkaproducer.service.KafkaProducerService;
import vojtech.kafkaproducer.util.ThreadFinder;

import java.util.Objects;

@Slf4j
@RestController
public class Controller implements KafkaApi {

    static final String OKRESPONSE = "Ok";

    private final PersonMapper mapper = Mappers.getMapper(PersonMapper.class);

    @Autowired
    KafkaProducerService kafkaProducer;

    @Value("${spring.kafka.topic.name}")
    private String topicName;

    @Override
    public ResponseEntity<String> sendPerson(Person person) {
        try {
            vojtech.model.Person personSrc = mapper.destinationToSource(person);
            kafkaProducer.sendMessage(topicName, personSrc);
            return ResponseEntity.ok(OKRESPONSE);
        } catch(Exception e) {
            return ResponseEntity.internalServerError().body(e.getCause().toString());
        }
    }

    @Override
    public ResponseEntity<String> startSending(Integer messages, Long millis) {
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

    @Override
    public ResponseEntity<String> stopSending() {
        try {
            log.info("Interrupting autoSend thread");
            Objects.requireNonNull(ThreadFinder.getThreadByName("autoSendThread")).interrupt();
        }
        catch(Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body(e.getCause().toString());
        }
        return ResponseEntity.ok(OKRESPONSE);
    }
}

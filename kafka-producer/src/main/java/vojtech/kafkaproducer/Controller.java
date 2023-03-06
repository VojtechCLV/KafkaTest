package vojtech.kafkaproducer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/kafka")
public class Controller {

    private final KafkaProducer kafkaProducer;

    public Controller(KafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    @Value("${custom.kafka.topic.name}")
    private String topicName;

    @GetMapping("/publish")
    public ResponseEntity<String> publish(@RequestParam("message") String message){
        kafkaProducer.sendMessage(topicName,message);
        return ResponseEntity.ok(String.format("Sending message to Kafka topic <b>%s</b> <br> Message: <i>%s</i>",
                topicName, message));
    }
}

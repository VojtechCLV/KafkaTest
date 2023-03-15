package vojtech.kafkaproducer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("vojtech.kafkaproducer")
//@EnableJpaRepositories("vojtech.kafkaproducer.*")
@ComponentScan(basePackages = { "vojtech.kafkaproducer.*" })
//@EntityScan("vojtech.model")
public class KafkaProducerApplication {

	public static void main(String[] args) {
		SpringApplication.run(KafkaProducerApplication.class, args);
	}

}

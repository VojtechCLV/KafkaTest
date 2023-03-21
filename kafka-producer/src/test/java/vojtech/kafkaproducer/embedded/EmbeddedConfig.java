package vojtech.kafkaproducer.embedded;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import vojtech.kafkaproducer.util.CustomDeserializer;
import vojtech.kafkaproducer.util.CustomSerializer;
import vojtech.model.Person;

import java.util.HashMap;
import java.util.Map;

@TestConfiguration
@Profile({ "test" })
public class EmbeddedConfig {

    @Value("${test.kafka.producer.bootstrap-servers}")
    private String bootstrapServers;

    @Bean(name="embeddedProducerFactory")
    public ProducerFactory<String, Person> embeddedProducerFactory() {
        Map<String, Object> testProps = new HashMap<>();
        testProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        testProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        testProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, CustomSerializer.class);
        return new DefaultKafkaProducerFactory<>(testProps);
    }

    @Bean(name="embeddedTemplate")
    public KafkaTemplate<String, Person> embeddedTemplate(final ProducerFactory<String, Person> embeddedProducerFactory) {
        return new KafkaTemplate<>(embeddedProducerFactory);
    }

    @Bean(name="personConsumerFactory")
    public ConsumerFactory<String, Person> personConsumerFactory() {
        Map<String, Object> testProps = new HashMap<>();
        testProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        testProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        testProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, CustomDeserializer.class);
        testProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"earliest");
        //testProps.put(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG, true);
        return new DefaultKafkaConsumerFactory<>(testProps);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Person> embeddedKafkaListenerContainerFactory(final ConsumerFactory<String, Person> consumerFactory) {
        final ConcurrentKafkaListenerContainerFactory<String, Person> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }
}

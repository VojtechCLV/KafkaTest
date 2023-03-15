package vojtech.kafkaproducer.embedded;

import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;
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
import vojtech.kafkaproducer.util.AvroDeserializer;
import vojtech.kafkaproducer.util.AvroSerializer;
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
        testProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        testProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, AvroSerializer.class.getName());
        return new DefaultKafkaProducerFactory<>(testProps);
    }

    @Bean(name="embeddedTemplate")
    public KafkaTemplate<String, Person> embeddedTemplate(final ProducerFactory<String, Person> embeddedProducerFactory) {
        return new KafkaTemplate<>(embeddedProducerFactory);
    }

    @Bean(name="personConsumerFactory")
    public ConsumerFactory<String, Person> personConsumerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, AvroDeserializer.class);
        configProps.put(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG, true);
        return new DefaultKafkaConsumerFactory<>(configProps);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Person> embeddedKafkaListenerContainerFactory(final ConsumerFactory<String, Person> consumerFactory) {
        final ConcurrentKafkaListenerContainerFactory<String, Person> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }
}

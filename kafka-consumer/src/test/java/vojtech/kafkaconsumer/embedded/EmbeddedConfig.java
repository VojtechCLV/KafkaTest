package vojtech.kafkaconsumer.embedded;

import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import io.confluent.kafka.serializers.KafkaAvroSerializerConfig;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import vojtech.model.Person;

import java.util.HashMap;
import java.util.Map;

@TestConfiguration
@Profile({ "test" })
public class EmbeddedConfig {
    @Value("${test.kafka.producer.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${test.schema.registry.url}")
    private String schemaRegistry;

    @Bean(name="embeddedProducerFactory")
    public ProducerFactory<String, Person> embeddedProducerFactory() {
        Map<String, Object> testProps = new HashMap<>();
        testProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        testProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        testProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class);
        testProps.put(KafkaAvroSerializerConfig.SCHEMA_REGISTRY_URL_CONFIG, schemaRegistry);
        return new DefaultKafkaProducerFactory<>(testProps);
    }

    @Bean(name="embeddedTemplate")
    public KafkaTemplate<String, Person> embeddedTemplate(final ProducerFactory<String, Person> embeddedProducerFactory) {
        return new KafkaTemplate<>(embeddedProducerFactory);
    }

    @Bean
    public ConsumerFactory<String, Person> embeddedPersonConsumerFactory() {
        Map<String, Object> testProps = new HashMap<>();
        testProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        testProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        testProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        testProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class);
        testProps.put(KafkaAvroDeserializerConfig.SCHEMA_REGISTRY_URL_CONFIG, schemaRegistry);
        testProps.put(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG, true);
        return new DefaultKafkaConsumerFactory<>(testProps);
    }


    @Bean
    public ConsumerFactory<String, byte[]> bytePersonConsumerFactory() {
        Map<String, Object> testProps = new HashMap<>();
        testProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        testProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        testProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        testProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        testProps.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, ByteArrayDeserializer.class);
        testProps.put(KafkaAvroDeserializerConfig.SCHEMA_REGISTRY_URL_CONFIG, schemaRegistry);
        testProps.put(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG, true);
        return new DefaultKafkaConsumerFactory<>(testProps);
    }


    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Person> embeddedKafkaListenerContainerFactory(final ConsumerFactory<String, Person> consumerFactory) {
        final ConcurrentKafkaListenerContainerFactory<String, Person> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, byte[]> byteKafkaListenerContainerFactory(final ConsumerFactory<String, byte[]> consumerFactory) {
        final ConcurrentKafkaListenerContainerFactory<String, byte[]> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }
}

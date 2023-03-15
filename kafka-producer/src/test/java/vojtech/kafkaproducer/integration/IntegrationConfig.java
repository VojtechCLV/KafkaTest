package vojtech.kafkaproducer.integration;

import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import io.confluent.kafka.serializers.KafkaAvroSerializerConfig;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import vojtech.model.Person;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class IntegrationConfig {
/*    @Bean(name="testConsumerFactory")
    public ConsumerFactory<String, Person> testConsumerFactory(
            @Value("${test.producer.bootstrap-servers}") final String bootstrapServers,
            @Value("${test.schema.registry.url}") final String schemaRegistry) {
        final Map<String, Object> configProps = new HashMap<>();
        configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configProps.put(ConsumerConfig.GROUP_ID_CONFIG, "Test_Group");
        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class);
        configProps.put(KafkaAvroDeserializerConfig.SCHEMA_REGISTRY_URL_CONFIG, schemaRegistry);
        configProps.put(KafkaAvroDeserializerConfig.AUTO_REGISTER_SCHEMAS, true);
        configProps.put(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG, true);
        return new DefaultKafkaConsumerFactory<>(configProps);
    }*/

/*    @Bean
    ConcurrentKafkaListenerContainerFactory testKafkaListenerContainerFactory(ConsumerFactory<String, Person> testConsumerFactory) {
        ConcurrentKafkaListenerContainerFactory factory = new ConcurrentKafkaListenerContainerFactory();
        factory.setConsumerFactory(testConsumerFactory);
        return factory;
    }*/

/*    @Bean
    public ProducerFactory<String, Person> testProducerFactory(
            @Value("${test.producer.bootstrap-servers}") final String bootstrapServers,
            @Value("${test.schema.registry.url}") final String schemaRegistry) {
        final Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class);
        configProps.put(KafkaAvroSerializerConfig.SCHEMA_REGISTRY_URL_CONFIG, schemaRegistry);
        configProps.put(KafkaAvroSerializerConfig.AUTO_REGISTER_SCHEMAS, false);
        return new DefaultKafkaProducerFactory<>(configProps);
    }*/

/*    @Bean
    public KafkaTemplate<String, Person> testKafkaTemplate(final ProducerFactory<String, Person> testProducerFactory) {
        return new KafkaTemplate<>(testProducerFactory);
    }*/
}

package vojtech.kafkaproducer.util;


import io.confluent.kafka.schemaregistry.client.MockSchemaRegistryClient;
import io.confluent.kafka.schemaregistry.client.SchemaRegistryClient;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;

import java.util.Map;

public class MockKafkaAvroDeserializer extends KafkaAvroDeserializer {
    public MockKafkaAvroDeserializer() {
        super();
        super.schemaRegistry = new MockSchemaRegistryClient();
    }

    public MockKafkaAvroDeserializer(SchemaRegistryClient client) {
        super(new MockSchemaRegistryClient());
    }

    public MockKafkaAvroDeserializer(SchemaRegistryClient client, Map<String, ?> props) {
        super(new MockSchemaRegistryClient(), props);
    }
}
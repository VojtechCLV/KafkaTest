package vojtech.kafkaproducer.util;

import io.confluent.kafka.schemaregistry.client.MockSchemaRegistryClient;
import io.confluent.kafka.schemaregistry.client.SchemaRegistryClient;
import io.confluent.kafka.schemaregistry.client.rest.exceptions.RestClientException;
import io.confluent.kafka.serializers.KafkaAvroSerializer;

import java.io.IOException;
import java.util.Map;

public class MockKafkaAvroSerializer extends KafkaAvroSerializer {
    public MockKafkaAvroSerializer() throws RestClientException, IOException {
        super();
        super.schemaRegistry = new MockSchemaRegistryClient();
    }

    public MockKafkaAvroSerializer(SchemaRegistryClient client) {
        super(new MockSchemaRegistryClient());
    }

    public MockKafkaAvroSerializer(SchemaRegistryClient client, Map<String, ?> props) {
        super(new MockSchemaRegistryClient(), props);
    }
}
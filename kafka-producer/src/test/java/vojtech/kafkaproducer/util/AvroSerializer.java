package vojtech.kafkaproducer.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serializer;
import org.springframework.stereotype.Component;
import vojtech.model.Person;
import org.apache.avro.io.*;
import org.apache.avro.specific.SpecificDatumWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

@Component
public class AvroSerializer implements Serializer<Person> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final Logger LOGGER = LoggerFactory.getLogger(AvroSerializer.class);

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public byte[] serialize(String topic, Person person) {
        DatumWriter<Person> writer = new SpecificDatumWriter<>(Person.class);
        byte[] data = new byte[0];
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        try {
            Encoder jsonEncoder = EncoderFactory.get().jsonEncoder(Person.getClassSchema(), stream);
            LOGGER.info("   PRE-SERIALIZATION: " + person);
            writer.write(person, jsonEncoder);
            jsonEncoder.flush();
            data = stream.toByteArray();
            LOGGER.info("   SERIALIZED DATA: " + Arrays.toString(data));
        } catch (IOException e) {
            LOGGER.error("Serialization error " + e.getMessage());
        }
        return data;
    }

    @Override
    public void close() {
    }
}

package vojtech.kafkaproducer.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serializer;
import org.springframework.stereotype.Component;
import vojtech.model.Person;
import org.apache.avro.io.*;
import org.apache.avro.specific.SpecificDatumWriter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

@Slf4j
@Component
public class CustomSerializer implements Serializer<Person> {

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
            writer.write(person, jsonEncoder);
            jsonEncoder.flush();
            data = stream.toByteArray();
            log.info("   SERIALIZED DATA: " + Arrays.toString(data));
        } catch (IOException e) {
            log.error("Serialization error: " + e.getMessage());
        }
        return data;
    }

    @Override
    public void close() {
    }
}

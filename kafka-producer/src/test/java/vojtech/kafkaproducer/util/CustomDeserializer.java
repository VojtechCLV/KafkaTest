package vojtech.kafkaproducer.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Deserializer;
import org.springframework.stereotype.Component;
import vojtech.model.Person;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.specific.SpecificDatumReader;

import java.io.IOException;
import java.util.Arrays;

@Slf4j
@Component
public class CustomDeserializer implements Deserializer<Person> {

    @Override
    public Person deserialize(String topic, byte[] data) {
        DatumReader<Person> reader = new SpecificDatumReader<>(Person.class);
        Decoder decoder = null;
        log.info("   SERIALIZED DATA: " + Arrays.toString(data));
        try {
            decoder = DecoderFactory.get()
                    .jsonDecoder(Person.getClassSchema(), new String(data));
            return reader.read(null, decoder);
        } catch (IOException e) {
            log.error("   Deserialization error" + e.getMessage());
        }
        return null;
    }
}

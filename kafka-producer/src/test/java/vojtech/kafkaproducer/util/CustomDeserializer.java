package vojtech.kafkaproducer.util;

import org.apache.kafka.common.serialization.Deserializer;
import org.springframework.stereotype.Component;
import vojtech.model.Person;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.specific.SpecificDatumReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;

@Component
public class CustomDeserializer implements Deserializer<Person> {

    private static Logger LOGGER = LoggerFactory.getLogger(CustomDeserializer.class);

    @Override
    public Person deserialize(String topic, byte[] data) {
        DatumReader<Person> reader = new SpecificDatumReader<>(Person.class);
        Decoder decoder = null;
        LOGGER.info("   SERIALIZED DATA: " + Arrays.toString(data));
        try {
            decoder = DecoderFactory.get()
                    .jsonDecoder(Person.getClassSchema(), new String(data));
            return reader.read(null, decoder);
        } catch (IOException e) {
            LOGGER.error("   Deserialization error" + e.getMessage());
        }
        return null;
    }
}

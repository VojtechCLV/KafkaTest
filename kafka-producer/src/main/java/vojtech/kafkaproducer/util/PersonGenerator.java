package vojtech.kafkaproducer.util;

import java.util.Random;

public class PersonGenerator {

    static Random random = new Random();

    public static String randomName() {
        int nameLength = 4 + random.nextInt(5);
        String randomName = random.ints(97, 120)
                .limit(nameLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        return randomName.substring(0, 1).toUpperCase() + randomName.substring(1);
    }

    public static Integer randomAge() {
        return 20 + random.nextInt(60);
    }
}

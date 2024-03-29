package vojtech.kafkaproducer;

import org.springframework.context.annotation.Profile;
import vojtech.model.Person;

@Profile({"test"})
public class TestPerson {

    private static final String testName = "Grogu";
    private static final Integer testAge = 50;
    private static final Person testPerson = new Person(testName,testAge);

    public static Person getTestPerson() {
        return testPerson;
    }
}

package vojtech.kafkaproducer;

import vojtech.model.Person;

public class TestPerson {

    private static final String testName = "Vojtech";
    private static final Integer testAge = 28;
    private static final Person testPerson = new Person(testName,testAge);

    public static Person getTestPerson() {
        return testPerson;
    }
}

package vojtech.kafkaconsumer;

import vojtech.model.Person;

public class TestPerson {

    private static final String testName = "Grogu";
    private static final Integer testAge = 50;
    private static final Person testPerson = new Person(testName,testAge);

    public static Person getTestPerson() {
        return testPerson;
    }
}

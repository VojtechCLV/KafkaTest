package vojtech.kafkaconsumer.unit;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import vojtech.kafkaconsumer.util.Benchmark;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class BenchmarkTest {

    private Map<String, String> results;
    private final DecimalFormat df = new DecimalFormat("#.##", DecimalFormatSymbols.getInstance(Locale.getDefault()));


    @BeforeEach
    void setup() {
        Benchmark.purgeAll();
    }

    @AfterAll
    static void cleanup() {
        Benchmark.purgeAll();
    }

    @Test
    void getDurationListIsEmptyTest() {
        assertTrue(Benchmark.getDurationListIsEmpty());
        Benchmark.addToDurationList(5);
        assertFalse(Benchmark.getDurationListIsEmpty());
    }

    @Test
    void  purgeAllTest() {
        Benchmark.addToDurationList(5);
        results = Benchmark.processAndGetResults();
        assertEquals(1,Integer.valueOf(results.get("Message count")),
                "There should be exactly 1 message recorded after processing");
        Benchmark.purgeAll();
        results = Benchmark.processAndGetResults();
        assertNull(results.get("Message count"), "There should be no messages recorded after purging");
        assertTrue(Benchmark.getDurationListIsEmpty());
    }

    @Test
    void addToDurationListTest() {
        Benchmark.addToDurationList(5);
        Benchmark.addToDurationList(7);
        results = Benchmark.processAndGetResults();
        assertEquals("[5, 7]",results.get("Individual durations"));
    }

    @Test
    void processAndGetResultsTest() throws ParseException {
        NumberFormat nf = NumberFormat.getInstance(Locale.getDefault());

        Benchmark.addToDurationList(5);
        Benchmark.addToDurationList(108);
        results = Benchmark.processAndGetResults();
        assertEquals("2",results.get("Message count"));
        assertEquals("5",results.get("Minimum"));
        assertEquals("108",results.get("Maximum"));
        assertEquals(56.50D,nf.parse(results.get("Average")));
        assertNotNull(results.get("WARNING"));
    }
}

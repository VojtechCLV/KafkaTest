package vojtech.kafkaconsumer.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;

public class Benchmark {

    private static int minDuration;
    private static int maxDuration;
    private static final DecimalFormat df = new DecimalFormat("#.##", DecimalFormatSymbols.getInstance(Locale.getDefault()));
    private static double averageDuration;
    private static int messageCount;
    private static ArrayList<Integer> durationList = new ArrayList<>();

    private Benchmark() {
        throw new IllegalStateException("Utility class");
    }

    public static void addToDurationList (int duration) {
        durationList.add(duration);
    }

    private static void resetInts() {
        minDuration=0;
        maxDuration=0;
        averageDuration=0;
        messageCount=0;
    }

    public static void purgeAll() {
        durationList.clear();
        resetInts();
    }

    public static Map<String, String> processAndGetResults() {

        if (durationList.isEmpty()) {
            HashMap<String, String> mappedError = new HashMap<>();
            mappedError.put("Error", "The list of delivery durations is empty, nothing to process");
            return mappedError;
        }

        messageCount = durationList.size();
        minDuration = Collections.min(durationList);
        maxDuration = Collections.max(durationList);
        averageDuration = calculateAverageDuration(durationList);

        HashMap<String, String> mappedResults = new HashMap<>();
        mappedResults.put("Message count", String.valueOf(messageCount));
        mappedResults.put("Minimum", String.valueOf(minDuration));
        mappedResults.put("Maximum", String.valueOf(maxDuration));
        mappedResults.put("Average", String.valueOf(df.format(averageDuration)));
        mappedResults.put("Individual durations", String.valueOf(durationList));

        if (maxDuration-minDuration > 100) mappedResults.put("WARNING",
                "Gap of >100ms between minimum and maximum durations detected. Can be caused by first message taking "+
                        "more time setting up the connection, or rapid frequent sending. You can purge the statistics "+
                        "using /benchmark/purge and try again with warmed up system.");

        return mappedResults;
    }

    public static boolean getDurationListIsEmpty() {
        return durationList.isEmpty();
    }

    private static double calculateAverageDuration(ArrayList<Integer> durations) {
        return durations.stream()
                .mapToDouble(d -> d)
                .average()
                .orElse(0.0);
    }
}

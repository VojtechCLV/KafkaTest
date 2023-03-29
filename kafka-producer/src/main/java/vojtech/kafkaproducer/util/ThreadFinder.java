package vojtech.kafkaproducer.util;

public class ThreadFinder {

    private ThreadFinder() {
        throw new IllegalStateException("Utility class");
    }

    public static Thread getThreadByName(String name) {
        for (Thread thread : Thread.getAllStackTraces().keySet()) {
            if (thread.getName().equals(name)) return thread;
        }
        return null;
    }
}

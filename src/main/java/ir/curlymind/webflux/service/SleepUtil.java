package ir.curlymind.webflux.service;

public class SleepUtil {
    public static void sleepSeconds(int seconds) {
        sleepMillis(seconds * 1000);
    }

    public static void sleepMillis(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

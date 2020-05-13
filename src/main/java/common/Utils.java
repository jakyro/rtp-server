package common;

public class Utils {
    public static void safeSleep(long l) {
        try {
            Thread.sleep(l);
        } catch (InterruptedException ignored) {
        }
    }
}

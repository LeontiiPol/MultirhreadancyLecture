package concurrency.helpers;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class Helper {

    public static void sleep(Thread thread) {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

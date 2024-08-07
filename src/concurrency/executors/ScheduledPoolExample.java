package concurrency.executors;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledPoolExample {

    public static void main(String[] args) throws InterruptedException {
        try (ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(8)) {
            scheduledExecutorService.schedule(() -> System.out.println("Delayed start"), 5, TimeUnit.SECONDS);
            scheduledExecutorService.scheduleWithFixedDelay(() -> System.out.println("Repeatable start"), 5, 10, TimeUnit.SECONDS);
            Thread.sleep(30000);
        }
    }
}

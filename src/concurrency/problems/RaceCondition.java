package concurrency.problems;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.IntStream;

public class RaceCondition {
    private static int counter = 0;
    private static final AtomicLong synchronizedCounter = new AtomicLong(0);

    public static void main(String[] args) {
        nonDetermined();
    }

    private static void determined() {
        try (ExecutorService executorService = Executors.newFixedThreadPool(8)) {
            for (int i = 0; i < 5; i++) {
                executorService.submit(() -> IntStream.range(0, 1_000_000).forEach(a -> synchronizedCounter.incrementAndGet()));
            }
        }
        System.out.println(synchronizedCounter.get());
    }

    private static void nonDetermined() {
        try (ExecutorService executorService = Executors.newFixedThreadPool(8)) {
            for (int i = 0; i < 5; i++) {
                executorService.submit(() -> IntStream.range(0, 1_000_000).forEach(a -> ++counter));
            }
        }
        System.out.println(counter);
    }
}

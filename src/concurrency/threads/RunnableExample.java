package concurrency.threads;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static concurrency.threads.CallableExample.TIME_PATTERN;

public class RunnableExample {

    public static void main(String[] args) {
        // Использование Runnable
        new Thread(RunnableExample::runnableTask).start();
    }



    public static void runnableTask() {
        System.out.printf("Hello world, it's RUNNABLE!, %s right now", LocalTime.now().format(DateTimeFormatter.ofPattern(TIME_PATTERN)));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.printf("%s interrupter", Thread.currentThread().getName());
            throw new RuntimeException(e);
        }
        System.out.println("\nRunnable finished.");
    }
}

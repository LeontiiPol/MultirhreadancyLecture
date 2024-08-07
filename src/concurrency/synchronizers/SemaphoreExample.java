package concurrency.synchronizers;

import concurrency.helpers.Helper;

import java.util.concurrent.Semaphore;
import java.util.stream.IntStream;

public class SemaphoreExample {

    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(2);
        IntStream.rangeClosed(1, 6)
                .mapToObj(i -> thread(i, semaphore))
                .peek(Helper::sleep)
                .forEach(Thread::start);

    }

    private static Thread thread(int number, Semaphore semaphore) {
        Thread thread = new Thread(() -> sayHello(semaphore));
        thread.setName("Thread №" + number);
        return thread;
    }

    private static void sayHello(Semaphore semaphore) {
        try {
            while (!semaphore.tryAcquire()) {
                System.out.println("waiting " + Thread.currentThread().getName());
                Thread.sleep(10000);
            }
            System.out.println("hello from " + Thread.currentThread().getName());
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            semaphore.release();
        }
    }
}

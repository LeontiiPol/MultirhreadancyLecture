package concurrency.synchronizers;

import concurrency.helpers.Helper;

import java.util.concurrent.CountDownLatch;
import java.util.stream.IntStream;

public class CountDownLatchExample {

    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(3);
        IntStream.rangeClosed(1, 3)
                .mapToObj(i -> thread(i, countDownLatch))
                .peek(Helper::sleep)
                .forEach(Thread::start);
    }

    private static Thread thread(int number, CountDownLatch countDownLatch) {
        Thread thread = new Thread(() -> sayHello(countDownLatch));
        thread.setName("Thread №" + number);
        return thread;
    }

    private static void sayHello(CountDownLatch countDownLatch) {
        try {
            System.out.println(Thread.currentThread().getName() + " arrived");
            countDownLatch.countDown();
            System.out.println(Thread.currentThread().getName() + " waiting");
            countDownLatch.await();
            System.out.println(Thread.currentThread().getName() + " finished");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

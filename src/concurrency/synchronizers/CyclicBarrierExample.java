package concurrency.synchronizers;

import concurrency.helpers.Helper;

import java.util.concurrent.CyclicBarrier;
import java.util.stream.IntStream;

public class CyclicBarrierExample {

    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3);

        IntStream.rangeClosed(1, 6)
                .mapToObj(i -> thread(i, cyclicBarrier))
                .peek(Helper::sleep)
                .forEach(Thread::start);
    }

    private static Thread thread(int number, CyclicBarrier cyclicBarrier) {
        Thread thread = new Thread(() -> sayHello(cyclicBarrier));
        thread.setName("Thread №" + number);
        return thread;
    }

    private static void sayHello(CyclicBarrier cyclicBarrier) {
        try {
            System.out.println(Thread.currentThread().getName() + " waiting");
            cyclicBarrier.await();
            System.out.println(Thread.currentThread().getName() + " finished");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

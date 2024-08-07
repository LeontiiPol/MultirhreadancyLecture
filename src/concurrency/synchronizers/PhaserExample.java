package concurrency.synchronizers;

import concurrency.helpers.Helper;

import java.util.concurrent.Phaser;
import java.util.stream.IntStream;

public class PhaserExample {

    public static void main(String[] args) {
        Phaser phaser = new Phaser(2);
        IntStream.rangeClosed(1, 4)
                .mapToObj(i -> thread(i, phaser))
                .peek(Helper::sleep)
                .forEach(Thread::start);
    }

    private static Thread thread(int number, Phaser phaser) {
        Thread thread = new Thread(() -> sayHello(phaser));
        thread.setName("Thread №" + number);
        return thread;
    }

    private static void sayHello(Phaser phaser) {
        try {
            System.out.println(Thread.currentThread().getName() + " on 1 phase and wait");
            phaser.arriveAndAwaitAdvance();

            System.out.println(Thread.currentThread().getName() + " on 2 phase and wait");
            phaser.arriveAndAwaitAdvance();

            System.out.println(Thread.currentThread().getName() + " finished");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

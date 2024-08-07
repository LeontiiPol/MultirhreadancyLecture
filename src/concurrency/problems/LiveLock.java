package concurrency.problems;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LiveLock {

    public static void main(String[] args) {
        Eater jack = new Eater();
        Eater sam = new Eater();

        Lock fork = new ReentrantLock();
        Lock knife = new ReentrantLock();

        try (ExecutorService executorService = Executors.newFixedThreadPool(2)) {
            executorService.execute(() -> jack.eat(fork, knife));
            executorService.execute(() -> sam.eat(knife, fork));
        }
    }

    static class Eater {

        public void eat(Lock kitchenTool1, Lock kitchenTool2) {
            if (kitchenTool1.tryLock()) {
                try {
                    Thread.sleep(1000);
                    grabSecondTool(kitchenTool2);
                } catch (InterruptedException e) {
                    throw new RuntimeException("Oops");
                } finally {
                    kitchenTool1.unlock();
                }
            }
        }

        private void grabSecondTool(Lock tool) throws InterruptedException {
            while (!tool.tryLock()) {
                cook();
            }
            eat(tool);
        }

        private void cook() throws InterruptedException {
            System.out.println("Cook");
            Thread.sleep(1000);
        }

        private void eat(Lock tool) {
            try {
                System.out.println("Eat");
            } finally {
                tool.unlock();
            }
        }
    }
}

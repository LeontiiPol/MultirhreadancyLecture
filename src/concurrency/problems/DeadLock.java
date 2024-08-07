package concurrency.problems;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DeadLock {

    public static void main(String[] args) {
        Eater jack = new Eater();
        Eater sam = new Eater();

        Object fork = new Object();
        Object knife = new Object();

        try (ExecutorService executorService = Executors.newFixedThreadPool(2)) {
            executorService.execute(() -> jack.eat(fork, knife));
            executorService.execute(() -> sam.eat(knife, fork));
        }
    }

    static class Eater {

        public void eat(Object kitchenTool1, Object kitchenTool2) {
            synchronized (kitchenTool1) {
                try {
                    Thread.sleep(1000);
                    synchronized (kitchenTool2) {
                        System.out.println("eating");
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException("Oops");
                }
            }

        }
    }
}

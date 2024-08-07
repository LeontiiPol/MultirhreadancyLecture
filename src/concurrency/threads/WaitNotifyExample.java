package concurrency.threads;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class WaitNotifyExample {

    public static void main(String[] args) {
        Object locker = new Object();
        AtomicBoolean fire = new AtomicBoolean(Boolean.FALSE);

        runExample(fire, locker);
//        showExecutorError(fire, locker);
    }

    private static void runExample(AtomicBoolean fire, Object locker) {
        ExecutorService executorService = Executors.newFixedThreadPool(8);
        executorService.submit(new OlympicsFireWaiter(fire, locker));
        executorService.submit(new OlympicsFireRunner(fire, locker));
        executorService.shutdown();
        awaitTermination(executorService);
    }


    private static void showExecutorError(AtomicBoolean fire, Object locker) {
        new Thread(new OlympicsFireWaiter(fire, locker)).start();
        new Thread(new OlympicsFireRunner(fire, locker)).start();
    }

    private static void awaitTermination(ExecutorService executorService) {
        try {
            executorService.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            System.out.println("Awaiting was interrupted");
        }
    }

    public static class OlympicsFireWaiter implements Runnable {

        private final AtomicBoolean fire;
        private final Object locker;

        public OlympicsFireWaiter(AtomicBoolean fire, Object locker) {
            this.fire = fire;
            this.locker = locker;
        }

        @Override
        public void run() {
            synchronized (locker) {
                while (Boolean.FALSE.equals(fire.get())) {
                    waitFire(locker);
                }
                System.out.printf("%s GOT fire and run\n", Thread.currentThread().getName());
            }
        }

        private void waitFire(Object locker) {
            try {
                System.out.printf("%s WAITS\n", Thread.currentThread().getName());
                locker.wait();
            } catch (InterruptedException e) {
                System.out.printf("%s was interrupter\n", Thread.currentThread().getName());
                throw new RuntimeException(e);
            }
        }
    }

    public static class OlympicsFireRunner implements Runnable {

        private final Random random = new Random();
        private final AtomicBoolean fire;
        private final Object locker;

        public OlympicsFireRunner(AtomicBoolean fire, Object locker) {
            this.fire = fire;
            this.locker = locker;
        }

        @Override
        public void run() {
            synchronized (locker) {
                while (random.nextInt(10) != 3) {
                    System.out.printf("%s running with fire\n", Thread.currentThread().getName());
                }
                System.out.printf("%s carried fire\n", Thread.currentThread().getName());
                fire.set(Boolean.TRUE);
                locker.notifyAll();
            }
        }
    }
}

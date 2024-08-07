package concurrency.executors;

import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

public class ForkJoinPoolExample {

    public static void main(String[] args) {
        try(ForkJoinPool forkJoinPool = (ForkJoinPool) Executors.newWorkStealingPool(8)) {
            System.out.println(forkJoinPool.invoke(new CounterTask(1L, 1_000_000L)));
        }
    }

    private static class CounterTask extends RecursiveTask<Long> {

        private final Long from;
        private final Long to;
        private final Long operationPerThread = 1000L;


        public CounterTask(Long from, Long to) {
            this.from = from;
            this.to = to;
        }

        @Override
        protected Long compute() {
            if (from - to <= operationPerThread) {
                return LongStream.rangeClosed(from, to).reduce(0L, Long::sum);
            }

            long middle = (to - from) / 2 + from;
            CounterTask subTask1 = new CounterTask(from, middle);
            subTask1.fork();

            CounterTask subTask2 = new CounterTask(middle + 1, to);
            subTask2.fork();

            return subTask1.join() + subTask2.join();
        }
    }
}

package concurrency.threads;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class CallableExample {

    public static final String INTERRUPTED_EXCEPTION_ERR_MSG = "Метод блокирующий, поэтому прокинится InterruptedException " +
            "если во время блокировки прервется выполнение текущего потока";
    public static final String EXECUTION_EXCEPTION_ERR_MSG = "Ошибка во время выполнения";
    public static final String CANCELLATION_EXCEPTION_ERROR_MSG = "Прервали выполнение таски, но все-равно попытались получить результат";
    public static final String TIME_PATTERN = "hh:mm";


    public static void main(String[] args) throws InterruptedException {
        showWaiting();
    }

    private static void showWaiting() throws InterruptedException {
        FutureTask<LocalTime> futureTask = new FutureTask<>(CallableExample::callableTask);
        Thread thread = new Thread(futureTask);
        thread.start();
        waitResult(thread, futureTask);
    }

    private static void showInterrupting() {
        FutureTask<LocalTime> futureTask = new FutureTask<>(CallableExample::callableTask);
        Thread thread = new Thread(futureTask);
        thread.start();

        interrupt(futureTask);
    }

    private static LocalTime callableTask() {
        LocalTime startTime = LocalTime.now();
        System.out.printf("Hello world!, it's CALLABLE, %s right now", startTime.format(DateTimeFormatter.ofPattern(TIME_PATTERN)));
        try {
            Thread.sleep(2000);
            return startTime;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static void waitResult(Thread thread, FutureTask<LocalTime> futureTask) throws InterruptedException {
        System.out.printf("\n%s got %s", Thread.currentThread().getName(), getResult(futureTask));
    }

    private static void interrupt(FutureTask<LocalTime> futureTask) {
        futureTask.cancel(true);
        System.out.printf("\n%s got %s", Thread.currentThread().getName(), getResult(futureTask));
    }

    private static LocalTime getResult(FutureTask<LocalTime> futureTask) {
        try {
            return futureTask.get();
        } catch (InterruptedException e) {
            System.out.println(INTERRUPTED_EXCEPTION_ERR_MSG);
        } catch (ExecutionException e) {
            System.out.println(EXECUTION_EXCEPTION_ERR_MSG);
        } catch (CancellationException e) {
            System.out.println(CANCELLATION_EXCEPTION_ERROR_MSG);
        }
        throw new RuntimeException();
    }


}

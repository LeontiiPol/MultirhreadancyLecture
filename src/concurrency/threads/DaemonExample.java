package concurrency.threads;

public class DaemonExample {

    public static void main(String[] args) {
        Thread thread = new Thread(RunnableExample::runnableTask);
        thread.setDaemon(true);
        thread.start();
    }
}

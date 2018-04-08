package me.sourabh.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import static java.lang.Thread.sleep;

/**
 * Verify the behavior of Object.notifyAll and how the thread states are after the waiting threads get notified.
 */
public class NotifyAllThreadStateSimulator {

    private static final short THREAD_COUNT = 3;

    public static void main(String[] args) {
        new NotifyAllThreadStateSimulator().start();
    }

    public void start() {
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);
        IntStream.range(0, THREAD_COUNT).forEach((i) -> executorService.submit(this::wMethod));
        printThreadState("main");
        this.sMethod();
        executorService.shutdown();
    }

    private synchronized void sMethod() {
        try {
            sleep(2000);
            this.notifyAll();
            printThreadState("notifier");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private synchronized void wMethod() {
        try {
            this.wait();
            System.out.println(Thread.currentThread().getName() + "is up");
            printThreadState("waiter");
            sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void printThreadState(String source) {
        int activeCount = Thread.currentThread().getThreadGroup().activeCount();
        Thread[] threads = new Thread[activeCount];
        Thread.currentThread().getThreadGroup().enumerate(threads);
        System.out.println(String.format("::::::::::::::::::::::::::::::::::: %s", source));
        for (Thread thread : threads) {
            System.out.println(thread.getName() + " : " + thread.getState());
        }
        System.out.println();
    }

}

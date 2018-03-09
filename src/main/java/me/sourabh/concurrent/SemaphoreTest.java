package me.sourabh.concurrent;

import java.util.concurrent.*;
import java.util.stream.IntStream;

/**
 * {@link Semaphore} use case - wait for the right amount of permits before a thread can run the code.
 *
 * Created by sourabhmahajan on 24/01/18.
 */
public class SemaphoreTest {

    private static final int PARALLEL_THREADS = 8;
    private static final int SEMAPHORE_PERMITS = 5;
    private static final int PERMITS_TO_ACQUIRE = 2;


    public static void main(String[] args) {
        Semaphore s = new Semaphore(SEMAPHORE_PERMITS);
        CyclicBarrier b = new CyclicBarrier(PARALLEL_THREADS);
        ExecutorService executorService = Executors.newFixedThreadPool(PARALLEL_THREADS);
        IntStream.range(0, PARALLEL_THREADS).forEach(i -> executorService.submit(() -> {
            try {
                b.await();
                s.acquire(PERMITS_TO_ACQUIRE);
                System.out.println("Got permits: " + Thread.currentThread().getName());
            } catch (InterruptedException | BrokenBarrierException e1) {
                e1.printStackTrace();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            s.release(PERMITS_TO_ACQUIRE);
            System.out.println("Released permits: " + Thread.currentThread().getName());
        }));
        executorService.shutdown();
    }
}

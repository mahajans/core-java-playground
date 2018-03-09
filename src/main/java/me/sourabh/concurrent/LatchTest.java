package me.sourabh.concurrent;

import java.util.concurrent.CountDownLatch;

/**
 * Sample {@link CountDownLatch} example use
 *
 * Created by sourabhmahajan on 02/03/18.
 */
public class LatchTest {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch endLatch = new CountDownLatch(10);

        for (int i = 0; i < 10; i++) {
            final int n = i;
            new Thread(() -> {
                // nothing to start unless all the threads have been created
                try {
                    startLatch.await();
                    System.out.println(n);
                    endLatch.countDown();

                } catch (InterruptedException e) {
                    System.out.println();
                }
            }).start();
        }
        // just some random delay to show none of the threads run until start latch countdowns
        Thread.sleep(1000);
        // countdown to get all the threads started
        startLatch.countDown();
        // wait for all the threads to finish
        endLatch.await();
    }
}

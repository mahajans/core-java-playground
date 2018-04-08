package me.sourabh.concurrent;

import java.util.concurrent.CountDownLatch;

/**
 * Latch to wait for a value to be set.
 * @param <T> type of the value to hold
 */
public class ValueLatch<T> {

    private final CountDownLatch latch;

    private T value;

    public ValueLatch() {
        this.latch = new CountDownLatch(1);
    }

    public synchronized boolean setValue(T value) {
        if (latch.getCount() > 0) {
            return false;
        }
        this.value = value;
        latch.countDown();
        return true;
    }

    public T getValue() throws InterruptedException {
        latch.await();
        synchronized (this) {
            return this.value;
        }
    }

}

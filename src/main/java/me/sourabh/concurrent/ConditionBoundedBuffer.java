package me.sourabh.concurrent;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Bounded buffer implementation using extrinsic lock and conditions (for full and empty buffer). Two different conditions
 * help avoid the change of signal stealing with usage of notify instead of notifyall. It makes overall notificaiton
 * mechanism more efficient given we unnecessarily don't notify all the threads when we know only one thread can proceed
 * in such cases. Having 2 conditions guarantees that the signal goes to the right thread that is indeed waiting for that
 * particular signal.
 *
 * @param <T>
 */
public class ConditionBoundedBuffer<T> {

    private final T[] items;
    private int tail = 0, head = 0, count = 0;

    private Lock lock = new ReentrantLock();
    private Condition waitForItem = lock.newCondition();
    private Condition waitForSpace = lock.newCondition();

    public ConditionBoundedBuffer(int bufferSize) {
        items = (T[]) new Object[bufferSize];
    }

    public void put(T x) throws InterruptedException {
        lock.lock();
        try {
            while (count == items.length) {
                waitForSpace.await();
            }
            items[tail++] = x;
            if (tail == items.length)
                tail = 0;
            count++;
            waitForItem.signal();
        } finally {
            lock.unlock();
        }
    }

    public synchronized T take() throws InterruptedException {
        lock.lock();
        try {
            while (count == 0) {
                waitForItem.await();
            }
            T val = items[head++];
            if (head == items.length)
                head = 0;
            count--;
            waitForSpace.signal();
            return val;
        } finally {
            lock.unlock();
        }
    }
}

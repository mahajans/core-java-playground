package me.sourabh.concurrent;

/**
 * A bounded buffer implementation with intrinsic lock. See a more efficient version of this with extrinsic lock here
 * {@link ConditionBoundedBuffer}
 *
 * @param <T>
 */
public class IntrinsicLockBoundedBuffer<T> {

    private final T[] items;
    private int tail = 0, head = 0, count = 0;

    public IntrinsicLockBoundedBuffer(int bufferSize) {
        items = (T[]) new Object[bufferSize];
    }

    public synchronized void put(T x) throws InterruptedException {
        while (count == items.length) {
            this.wait();
        }
        items[tail++] = x;
        if (tail == items.length)
            tail = 0;
        count++;
        this.notifyAll();
    }

    public synchronized T take() throws InterruptedException {
        while (count == 0) {
            this.wait();
        }
        T val = items[head++];
        if (head == items.length)
            head = 0;
        count--;
        this.notifyAll();
        return val;
    }
}

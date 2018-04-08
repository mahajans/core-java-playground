package me.sourabh.concurrent;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Tests for {@link IntrinsicLockBoundedBuffer}
 */
public class IntrinsicLockBoundedBufferTest {

    @Test
    public void test() throws InterruptedException {
        IntrinsicLockBoundedBuffer<Integer> buffer = new IntrinsicLockBoundedBuffer<>(3);

        // scheduled task to take an item from the buffer after 2 sec.
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.schedule(buffer::take, 2, TimeUnit.SECONDS);

        buffer.put(1);
        buffer.put(2);
        buffer.put(3);

        // this call will have to wait given the buffer size is 3. This will resume when the other scheduled task takes
        // an item out of the buffer
        buffer.put(4);

        Assertions.assertEquals(Integer.valueOf(2), buffer.take());
        Assertions.assertEquals(Integer.valueOf(3), buffer.take());
        Assertions.assertEquals(Integer.valueOf(4), buffer.take());
        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);
    }

    @Test
    public void testWaiting() throws InterruptedException {
        IntrinsicLockBoundedBuffer<Integer> buffer = new IntrinsicLockBoundedBuffer<>(3);
        Thread mainThread = Thread.currentThread();

        // scheduled task to take an item from the buffer after 2 sec.
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.schedule(mainThread::interrupt, 2, TimeUnit.SECONDS);

        buffer.put(1);
        buffer.put(2);
        buffer.put(3);

        // this call will have to wait given the buffer size is 3. This will resume when the other scheduled task takes
        // an item out of the buffer
        Assertions.assertThrows(InterruptedException.class, () -> buffer.put(4));
    }

}

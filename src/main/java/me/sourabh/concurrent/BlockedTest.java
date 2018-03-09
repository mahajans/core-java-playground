package me.sourabh.concurrent;

import java.lang.management.LockInfo;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.*;
import java.util.stream.IntStream;

/**
 * Only one thread should enter the synchronized block, others should be in blocked state.
 *
 * Created by sourabhmahajan on 03/03/18.
 */
public class BlockedTest {

    private static CountDownLatch latch = new CountDownLatch(1);
    private static final ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();


    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        IntStream.range(1, 5).forEach(i -> executorService.execute(() -> {
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            doSomething();
        }));
        latch.countDown();
        executorService.shutdown();
        executorService.awaitTermination(5000, TimeUnit.SECONDS);
    }

    private synchronized static void doSomething() {
        System.out.println("***************************************");
        Thread[] threads = new Thread[Thread.currentThread().getThreadGroup().activeCount()];
        Thread.currentThread().getThreadGroup().enumerate(threads);
        Thread currentThread = Thread.currentThread();
        ThreadInfo info = threadMXBean.getThreadInfo(currentThread.getId());

        for (Thread thread :
                threads) {
            if (thread.getState() == Thread.State.BLOCKED) {
                ThreadInfo threadInfo = threadMXBean.getThreadInfo(thread.getId());
                LockInfo lockInfo = threadInfo.getLockInfo();
                System.out.println(thread.getName());
                System.out.println(lockInfo.getIdentityHashCode() + " | " + threadInfo.getLockOwnerName());
            }
        }
    }


}

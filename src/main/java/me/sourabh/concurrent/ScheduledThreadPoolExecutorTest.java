package me.sourabh.concurrent;

import java.util.concurrent.*;

/**
 * A sample use of the {@link ScheduledThreadPoolExecutor}. This guy internally uses a {@link DelayQueue} to keep the
 * runnables. The delay queue implementation, which can be created for {@link Delayed} objects, which in the case of
 * {@link ScheduledThreadPoolExecutor} is the {@link ScheduledFuture}.
 *
 * Created by sourabhmahajan on 10/03/18.
 */
public class ScheduledThreadPoolExecutorTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

        ScheduledFuture<?> scheduledFuture = executorService.scheduleAtFixedRate(() -> System.out.println("just ran 1"),
                1, 2, TimeUnit.SECONDS);

        ScheduledFuture<?> scheduledFuture2 = executorService.scheduleAtFixedRate(() -> System.out.println("just ran 2"),
                4, 2, TimeUnit.SECONDS);

        System.out.println(scheduledFuture.getDelay(TimeUnit.MILLISECONDS));
        System.out.println(scheduledFuture2.getDelay(TimeUnit.MILLISECONDS));
    }
}

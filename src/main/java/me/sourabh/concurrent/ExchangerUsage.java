package me.sourabh.concurrent;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutionException;

/**
 * A sample {@link Exchanger} use
 *
 * Trigger 2 asynchronous tasks both using the same {@link Exchanger} instance to exchange some data.
 *
 * Created by sourabhmahajan on 02/03/18.
 */
public class ExchangerUsage {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        Exchanger<Integer> exchanger = new Exchanger<>();

        CompletableFuture<Integer> f1 = CompletableFuture.supplyAsync(() -> {
            try {
                System.out.println(Thread.currentThread().getName() + ": waiting for someone to exchange the data");
                return exchanger.exchange(1);
            } catch (InterruptedException e) {
                return -1;
            }
        });
        CompletableFuture<Integer> f2 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
                System.out.println(Thread.currentThread().getName() + ": Going for exhange now after a sweet sleep");
                return exchanger.exchange(2);
            } catch (InterruptedException e) {
                return -1;
            }
        });

        System.out.println("Main: waiting for the exchange to happen");
        System.out.println(f1.get()); // gives 2
        System.out.println(f2.get()); // gives 1
    }
}

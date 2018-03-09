package me.sourabh.concurrent;

/**
 * Created by sourabhmahajan on 08/07/17.
 */
public class NoVisibility {
    private static boolean ready;
    private static int n;

    private static class ReaderThread extends Thread {
        @Override
        public void run() {
            System.out.println("started reader");
            while (!ready)
                Thread.yield();
            System.out.println("#### " + n);
        }
    }

    public static void main(String[] args) {
        System.out.println("Started main");
        new ReaderThread().start();
        n = 42;
        ready = true;
        System.out.println("main ending");
    }
}

package me.sourabh.concurrent;

/**
 * In theory, there's a chance that the ReaderThread will never terminate given the visibility issues that can arise
 * due to the values (ready) being cached in one of the cpu cores where the main thread is running. Similarly, another
 * possibility is that the thread does terminate but doesn't print 42 for n, but 0 - this is when ready is visible but n
 * is not. Obviously, another possibility is everything goes fine. For my various run, I've seen all the runs go fine.
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

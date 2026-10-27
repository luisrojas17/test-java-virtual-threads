package com.acme.vt.tests;

/**
 * This class shows how to create:
 * - a platform thread
 * - a virtual thread
 */
public class Test1 {

    static void main(String... args) throws InterruptedException {

        Runnable task = () -> {
            System.out.println(Thread.currentThread());
        };

        Test1.createPT(task);

        Test1.createVT(task);
    }

    static void createPT(final Runnable task) throws InterruptedException {

        // Platform threads need 1 MB of memory in the stack
        var pThread =
                Thread.ofPlatform()
                        .name("Platform thread")
                        .daemon(false)
                        .unstarted(task);

        // To schedule this thread
        pThread.start();

        // To wait for this thread finish its work
        pThread.join();
    }

    static void createVT(final Runnable task) throws InterruptedException {
        // By default, virtual thread is always a daemon thread.
        // You cannot create virtual threads as non-daemon threads.
        // A virtual thread runs on top of a platform thread which is taken from a specific fork/join pool.
        // Do not be confused with common fork/join pool which is used for parallel streams, not for virtual threads.

        // Running a task in a platform thread is more expensive than on virtual thread.
        // Virtual thread need about 200 or 300 bytes in the memory stack
        var vThread =
                Thread.ofVirtual()
                        .name("Virtual thread 1")
                        .unstarted(task);

        vThread.start();

        vThread.join();
    }
}

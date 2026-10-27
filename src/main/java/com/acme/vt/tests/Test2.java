package com.acme.vt.tests;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

/**
 * This class shows how long is taken to create a million of virtual threads depending on
 * cores available on the PC.
 */
public class Test2 {

    static void main(String... args) {

        var pThreads = ConcurrentHashMap.newKeySet();
        var workers = Pattern.compile("worker-[\\d]+");

        // Task to execute when the thread is going to be created.
        Runnable taskReadName = () -> {
          var name = Thread.currentThread().toString();
          var workerMatcher = workers.matcher(name);
          if (workerMatcher.find()) {
              pThreads.add(workerMatcher.group());
          }
          waitFor(1);
        };

        var begin = Instant.now();

        // To create a range of virtual threads.
        var virtualThreads = IntStream.range(0, 1_000_000)
                .mapToObj(numberOfThread -> Thread.ofVirtual().unstarted(taskReadName))
                .toList();

        // To schedule the threads.
        virtualThreads.forEach(Thread::start);

        // To iterate each virtual thread created.
        virtualThreads.forEach(thread -> {
            try {
                // And wait for its task is completed.
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        var end = Instant.now();

        // To determine how many cores and platform threads are necessary to create almost 1_000_000 VT
        System.out.println("# Cores = " + Runtime.getRuntime().availableProcessors());
        System.out.println("Time = " + Duration.between(begin, end).toMillis() + " ms.");
        System.out.println("Platform threads used = " + pThreads.size());
    }

    // For wait for one millisecond
    public static void waitFor(long millis) {

        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

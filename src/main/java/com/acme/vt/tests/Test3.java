package com.acme.vt.tests;

import com.acme.vt.helpers.WeatherServer;

import java.util.concurrent.StructuredTaskScope;

/**
 * This example shows how to use StructuredTaskScope with open factory method in order to
 * execute multiple subtask concurrently like java.util.ExecutorService API, introduced
 * in Java 5.
 */
public class Test3 {

    static void main(String... args) throws InterruptedException {

        // To send task runnable or callable
        // Use Joiner interface to get first result produced by a task that is not
        // an exception and cancels.
        //
        // If no result is produced, then it throws and exception
        try (var scope = StructuredTaskScope.open()) {
        //try (var scope = StructuredTaskScope.open(
        //        StructuredTaskScope.Joiner.anySuccessfulResultOrThrow())) {

            // To start a new thread for each subtask to execute
            // Then fork aas many task as you need
            var subTaskResult1 = scope.fork(WeatherServer::readFromGWF);
            var subTaskResult2 = scope.fork(WeatherServer::readFromNWF);
            var subTaskResult3 = scope.fork(WeatherServer::readFromPW);

            scope.join();

            var subTaskStatus1 = subTaskResult1.state();
            IO.println("Subtask state: " + subTaskStatus1);
            if (StructuredTaskScope.Subtask.State.SUCCESS.equals(subTaskStatus1)) {
                IO.println("\\t" + subTaskResult1.get());
            }

            var subTaskStatus2 = subTaskResult2.state();
            IO.println("Subtask state: " + subTaskStatus2);
            if (StructuredTaskScope.Subtask.State.SUCCESS.equals(subTaskStatus2)) {
                IO.println("\\t" + subTaskResult2.get());
            }

            var subTaskStatus3 = subTaskResult3.state();
            IO.println("Subtask state: " + subTaskStatus3);
            if (StructuredTaskScope.Subtask.State.SUCCESS.equals(subTaskStatus3)) {
                IO.println("\\t" + subTaskResult3.get());
            }
        }

    }

}

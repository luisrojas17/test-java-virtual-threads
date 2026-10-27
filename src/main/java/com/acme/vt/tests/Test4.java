package com.acme.vt.tests;

import com.acme.vt.helpers.AirlineServer;

import java.util.concurrent.StructuredTaskScope;

public class Test4 {

    static void main(String... args) throws InterruptedException {

        try (var scope = StructuredTaskScope.open()) {

            // Check the method AirlineServer::readFromDAL to reproduce the exception
            var f1 = scope.fork(AirlineServer::readFromDAL);
            var f2 = scope.fork(AirlineServer::readFromGAL);
            var f3 = scope.fork(AirlineServer::readFromIAL);

            // If one of the tasks throws an exception then the scope is canceled immediately and your
            // call to join will throw a FailedException. The exact flow is next:
            // As soon as an exception is thrown by one of the forked tasks the scope is canceled
            // It interrupts all the tasks that are still running
            // And the join call throws a FailedException with the exception as a root cause
            try {
                scope.join();
            } catch (Exception e) {
                IO.println("Exception!!!" + e);
            }

            IO.println("F1: " + f1.state());
            IO.println("F2: " + f2.state());
            IO.println("F3: " + f3.state());
        }
    }
}

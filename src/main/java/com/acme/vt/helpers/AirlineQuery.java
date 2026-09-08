package com.acme.vt.helpers;

import com.acme.vt.models.Flight;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.StructuredTaskScope;
import java.util.stream.Stream;

import static java.util.concurrent.StructuredTaskScope.Subtask.State.FAILED;
import static java.util.concurrent.StructuredTaskScope.Subtask.State.SUCCESS;

public class AirlineQuery {

    static class AirlineException extends RuntimeException {

        public AirlineException(String message) {
            super(message);
        }
    }

    static class AirlineJoiner implements StructuredTaskScope.Joiner<Flight, Flight> {

        private List<StructuredTaskScope.Subtask<? extends Flight>> subtasks =
                new ArrayList<>();

        /**
         * OnFork method is running in the main thread.
         *
         * @param subtask the subtask
         * @return
         */
        @Override
        public boolean onFork(StructuredTaskScope.Subtask<? extends Flight> subtask) {
            this.subtasks.add(subtask);
            return false;
            //return StructuredTaskScope.Joiner.super.onFork(subtask);
        }

        @Override
        public Flight result() {
            return subtasks.stream()
                    .filter(subTask -> subTask.state() == SUCCESS)
                    .map(StructuredTaskScope.Subtask::get)
                    .min(Comparator.comparing(Flight::getPrice))
                    .orElseThrow(this::exception);
        }

        public AirlineException exception() {
            var exception = new AirlineException("There were an exceptions.");

            subtasks.stream()
                    .filter(subTask -> subTask.state() == FAILED)
                    .map(StructuredTaskScope.Subtask::exception)
                    .forEach(exception::addSuppressed);

            return exception;
        }
    }

    public static Flight query() throws InterruptedException {

        try (var scope =
                     StructuredTaskScope.open(new AirlineJoiner())) {

            /*var subTask1 = scope.fork(AirlineServer::readFromDAL);
            var subTask2 = scope.fork(AirlineServer::readFromGAL);
            var subTask3 = scope.fork(AirlineServer::readFromIAL);*/

            scope.fork(AirlineServer::readFromDAL);
            scope.fork(AirlineServer::readFromGAL);
            scope.fork(AirlineServer::readFromIAL);

            return scope.join();

            /*
            scope.join();

            var bestFlight = Stream.of(subTask1, subTask2, subTask3)
                    .filter(subTask -> subTask.state() == SUCCESS)
                    .map(StructuredTaskScope.Subtask::get)
                    .min(Comparator.comparing(Flight::getPrice))
                    .orElseThrow();

            return bestFlight;*/

        }
    }
}

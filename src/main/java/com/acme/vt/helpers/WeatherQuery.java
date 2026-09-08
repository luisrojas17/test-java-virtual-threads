package com.acme.vt.helpers;

import com.acme.vt.models.Weather;

import java.util.concurrent.StructuredTaskScope;

public class WeatherQuery {

    public static Weather query() throws InterruptedException {

        // To return the first result produced by these task.
        //try (var scope = StructuredTaskScope.<Weather, Weather>open(
        try (var scope = StructuredTaskScope.open(
                //StructuredTaskScope.Joiner.anySuccessfulResultOrThrow()
                StructuredTaskScope.Joiner.<Weather>anySuccessfulResultOrThrow()
        )) {

            scope.fork(WeatherServer::readFromPW);
            scope.fork(WeatherServer::readFromNWF);
            scope.fork(WeatherServer::readFromGWF);

            // It is not the best idea doing a cast because it could hide another problem
            return scope.join();
        }
    }
}

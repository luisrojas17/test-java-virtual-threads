package com.acme.vt.helpers;

import com.acme.vt.models.Flight;
import com.acme.vt.models.Travel;

import java.util.concurrent.StructuredTaskScope;

public class TravelQuery {

    public static Travel query() throws InterruptedException {

        try (var scope = StructuredTaskScope.open(
                StructuredTaskScope.Joiner.allUntil(
                        subtask -> subtask.state() == StructuredTaskScope.Subtask.State.SUCCESS
                                && subtask.get() instanceof Flight

                )
        )) {

            // Nice to have (optional)
            var weatherTask = scope.fork(WeatherQuery::query);

            // Must have
            var flightTask = scope.fork(AirlineQuery::query);

            scope.join();

            var flight = flightTask.get();

            if (weatherTask.state() == StructuredTaskScope.Subtask.State.SUCCESS) {
                var weather = weatherTask.get();
                return new Travel.FlightWithWeather(flight, weather);
            } else {
                return new Travel.FlightNoWeather(flight);
            }

        }
    }
}

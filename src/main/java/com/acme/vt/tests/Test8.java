package com.acme.vt.tests;

import com.acme.vt.helpers.AirlineQuery;
import com.acme.vt.helpers.WeatherQuery;

/**
 * This example shows how to get the better flight according to its price and weather.
 * Also, it is implemented a custom Joiner where you can show how to override the
 * StructuredTaskScope.Joiner<T, R> interface.
 */
public class Test8 {

    void main() throws InterruptedException {

        var weather = WeatherQuery.query();
        IO.println(weather);

        var flight = AirlineQuery.query();
        IO.println(flight);

        var exceptions = AirlineQuery.exceptions();
        IO.println(exceptions);

    }

}

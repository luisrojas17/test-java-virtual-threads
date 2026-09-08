package com.acme.vt.tests;

import com.acme.vt.helpers.WeatherQuery;

/**
 * This example shows how to specify the type of result for each Subtask completed.
 */
public class Test7 {

    static void main() throws InterruptedException {

        var weather = WeatherQuery.query();

        IO.println(weather);
    }
}

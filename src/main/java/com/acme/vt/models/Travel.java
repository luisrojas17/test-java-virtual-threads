package com.acme.vt.models;

public sealed interface Travel {

    record FlightWithWeather(Flight flight, Weather weather) implements Travel {}

    record FlightNoWeather(Flight flight) implements Travel {}

}


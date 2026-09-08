package com.acme.vt.helpers;

import com.acme.vt.models.Weather;

import java.util.Random;

public class WeatherServer {

    private static Random random = new Random(314L);

    public static Weather readFromGWF() {
        waitFor(80, 5);
        return new Weather("Global Weather Forecast", "Sunny");
    }

    public static Weather readFromPW() {
        waitFor(110, 20);
        return new Weather("Planet Weather Forecast", "Sunny");
    }

    public static Weather readFromNWF() {
        waitFor(100, 15);
        return new Weather("National Weather Forecast", "Sunny");
    }

    public static void waitFor(long millis, int delta) {

        try {
            Thread.sleep(millis + random.nextInt(-delta, +delta));
        } catch(InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

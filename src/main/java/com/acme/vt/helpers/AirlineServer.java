package com.acme.vt.helpers;

import com.acme.vt.models.Flight;

import java.util.Random;

public class AirlineServer {

    private static Random random = new Random(314L);

    public static Flight readFromGAL() {
        waitFor(80, 5);
        return new Flight("Global Airlines", priceFor(100, 15));
    }

    public static Flight readFromIAL(){
        //throw new IllegalStateException("Exception from International Airlines");
        waitFor(90, 10);
        return new Flight("International Airlines", priceFor(90, 10));
    }

    public static Flight readFromDAL() {
        //while(true) {}
        waitFor(100, 15);
        //throw new IllegalStateException("A test exception");
        return new Flight("Diamond Airlines", priceFor(95, 15));
    }

    public static int priceFor(int median, int delta) {
        return median + random.nextInt(-delta, +delta);
    }

    public static void waitFor(long millis, int delta) {

        try {
            Thread.sleep(millis + random.nextInt(-delta, +delta));
        } catch(InterruptedException e) {
            IO.println("Interrupted!!!");
            throw new RuntimeException(e);
        }
    }

}

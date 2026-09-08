package com.acme.vt.models;

public class Weather {

    private String globalWeatherForecast;

    private String sunny;

    public Weather(String globalWeatherForecast, String sunny) {
        this.globalWeatherForecast = globalWeatherForecast;
        this.sunny = sunny;
    }

    public String getGlobalWeatherForecast() {
        return globalWeatherForecast;
    }

    public void setGlobalWeatherForecast(String globalWeatherForecast) {
        this.globalWeatherForecast = globalWeatherForecast;
    }

    public String getSunny() {
        return sunny;
    }

    public void setSunny(String sunny) {
        this.sunny = sunny;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "globalWeatherForecast='" + globalWeatherForecast + '\'' +
                ", sunny='" + sunny + '\'' +
                '}';
    }
}

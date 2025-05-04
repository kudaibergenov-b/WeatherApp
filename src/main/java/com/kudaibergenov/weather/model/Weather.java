package com.kudaibergenov.weather.model;

import java.time.LocalDateTime;

public class Weather {
    private final String city;
    private final double temperature;
    private final double feelsLike;
    private final String description;
    private final LocalDateTime timestamp;

    public Weather(String city, double temperature, double feelsLike, String description, LocalDateTime timestamp) {
        this.city = city;
        this.temperature = temperature;
        this.feelsLike = feelsLike;
        this.description = description;
        this.timestamp = timestamp;
    }

    public String getCityName() {
        return city;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getFeelsLike() {
        return feelsLike;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "Weather in " + city +
                ": " + temperature + "°C, feels like " + feelsLike + "°C, " + description +
                " at " + timestamp;
    }
}
package com.kudaibergenov.weather;

import com.kudaibergenov.weather.model.Weather;
import com.kudaibergenov.weather.service.WeatherService;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        WeatherService service = new WeatherService();
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Terminal Weather App ===");

        while (true) {
            System.out.print("Enter city name (or type 'exit' to quit): ");
            String city = scanner.nextLine().trim();

            if (city.equalsIgnoreCase("exit")) {
                System.out.println("Exiting...");
                break;
            }

            try {
                Weather weather = service.getWeather(city);
                System.out.println("Weather in " + weather.getCity() + ": " + weather.getTemperature() + "°C, " + weather.getDescription());
                System.out.println("Saved to database.");
            } catch (SQLException e) {
                System.out.println("Database error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Error fetching weather: " + e.getMessage());
            }
        }

        scanner.close();
    }
}
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

            if (city.isEmpty()) {
                System.out.println("City name cannot be empty.");
                continue;
            }

            if (city.length() > 100) {
                System.out.println("City name is too long. Please enter a shorter name.");
                continue;
            }

            if (!city.matches("[a-zA-Z\\s-]+")) {
                System.out.println("City name can only contain letters, spaces, and hyphens.");
                continue;
            }

            try {
                Weather weather = service.fetchAndSaveWeather(city);
                System.out.println();
                System.out.println(weather.toString());
                System.out.println();
            } catch (SQLException e) {
                System.out.println("Database error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Error fetching weather: " + e.getMessage());
            }
        }

        scanner.close();
    }
}
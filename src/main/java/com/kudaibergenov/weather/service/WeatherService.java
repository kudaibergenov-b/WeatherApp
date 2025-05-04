package com.kudaibergenov.weather.service;

import com.kudaibergenov.weather.model.Weather;
import com.kudaibergenov.weather.util.DatabaseUtil;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class WeatherService {

    private static final String API_KEY = "c9fccd7a74649b0b28d5f7c580383993";
    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/weather";

    public Weather fetchAndSaveWeather(String city) throws Exception {
        String urlString = BASE_URL + "?q=" + city + "&units=metric&appid=" + API_KEY;

        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder jsonBuilder = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            jsonBuilder.append(line);
        }

        reader.close();
        connection.disconnect();

        JSONObject json = new JSONObject(jsonBuilder.toString());

        double temp = json.getJSONObject("main").getDouble("temp");
        double feelsLike = json.getJSONObject("main").getDouble("feels_like");
        String description = json.getJSONArray("weather").getJSONObject(0).getString("description");

        Weather weather = new Weather(city, temp, feelsLike, description, LocalDateTime.now());

        saveToDatabase(weather);

        return weather;
    }

    private void saveToDatabase(Weather weather) throws Exception {
        String sql = "INSERT INTO weather_data (city_name, temperature, description, timestamp) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, weather.getCityName());
            stmt.setDouble(2, weather.getTemperature());
            stmt.setString(3, weather.getDescription());
            stmt.setTimestamp(4, Timestamp.valueOf(weather.getTimestamp()));

            stmt.executeUpdate();
        }
    }
}
package com.kudaibergenov.weather.service;

import com.kudaibergenov.weather.model.Weather;
import com.kudaibergenov.weather.util.ConfigUtil;
import com.kudaibergenov.weather.util.DatabaseUtil;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class WeatherService {

    private static final String API_KEY = ConfigUtil.get("weather.api.key");
    private static final String BASE_URL = ConfigUtil.get("weather.base.url");

    public Weather fetchAndSaveWeather(String city) throws Exception {
        String urlString = BASE_URL + "?q=" + city + "&units=metric&appid=" + API_KEY;

        JSONObject json = getJsonObject(urlString);

        double temp = json.getJSONObject("main").getDouble("temp");
        double feelsLike = json.getJSONObject("main").getDouble("feels_like");
        String description = json.getJSONArray("weather").getJSONObject(0).getString("description");

        Weather weather = new Weather(city, temp, feelsLike, description, LocalDateTime.now());

        saveToDatabase(weather);

        return weather;
    }

    @NotNull
    private static JSONObject getJsonObject(String urlString) throws IOException {
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

        return new JSONObject(jsonBuilder.toString());
    }

    private void saveToDatabase(Weather weather) throws Exception {
        String sql = "INSERT INTO weather_data (city_name, temperature, feels_like, description, timestamp) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, weather.getCityName());
            stmt.setDouble(2, weather.getTemperature());
            stmt.setDouble(3, weather.getFeelsLike());
            stmt.setString(4, weather.getDescription());
            stmt.setTimestamp(5, Timestamp.valueOf(weather.getTimestamp()));

            stmt.executeUpdate();
        }
    }
}
# Terminal-Based Weather App

This is a simple command-line weather application built with **Java** and **PostgreSQL**.  
It fetches real-time weather data from **OpenWeatherMap API** and saves the result to a local database.

---

## Features

- Fetch current weather by city name
- Save weather data with timestamps into PostgreSQL
- Uses JDBC for DB connection
- Includes basic security practices:  
  - Input validation  
  - Externalized API keys  
  - Protection against SQL injection
- Lightweight and easy to run on any machine

---

## Technologies Used

- Java 17+
- PostgreSQL
- JDBC
- OpenWeatherMap API
- JSON parsing with `org.json`
- CLI (Command-Line Interface)

---

## Project Structure

```
weather-app/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── com.kudaibergenov.weather/
│   │   │   │   ├── Main.java
│   │   │   │   ├── model/
│   │   │   │   │   └── Weather.java
│   │   │   │   ├── service/
│   │   │   │   │   └── WeatherService.java
│   │   │   │   └── util/
│   │   │   │       ├── ConfigUtil.java
│   │   │   │       └── DatabaseUtil.java
│   └── resources/
│       │── config.properties
│       └── db.properties
```

---

## Setup Instructions

### 1. Clone the Repository

```bash
git clone https://github.com/yourusername/weather-app.git
cd weather-app
```

### 2. Configure PostgreSQL Database

Create a new PostgreSQL database and table:

```sql
CREATE DATABASE weatherdb;

CREATE TABLE weather_data (
  id SERIAL PRIMARY KEY,
  city_name VARCHAR(100) NOT NULL,
  temperature DOUBLE PRECISION NOT NULL,
  description TEXT NOT NULL,
  timestamp TIMESTAMP NOT NULL
);
```

### 3. Configure `config.properties`

Create a file `config.properties` in `src/main/resources/`:

```properties
db.url=jdbc:postgresql://localhost:5432/weatherdb
db.user=your_postgres_username
db.password=your_postgres_password
weather.api.key=your_openweathermap_api_key
```

Replace placeholders with your actual DB credentials and API key.

---

## Run the Application

Use any Java IDE (like IntelliJ or Eclipse) or the terminal:

```bash
cd src
javac -cp ".;path/to/json-lib.jar;postgresql-<version>.jar" com/kudaibergenov/weather/Main.java
java -cp ".;path/to/json-lib.jar;postgresql-<version>.jar" com.kudaibergenov.weather.Main
```

Alternatively, use Gradle or Maven for easier dependency management.

---

## Security Aspects

This app applies basic security measures:

- **API Key** is stored outside the source code (`config.properties`)
- **PreparedStatements** are used to prevent SQL Injection
- **User input is validated** (length, format)
- **Exceptions** are handled gracefully

---

## Sample Output

```
=== Terminal Weather App ===
Enter city name (or type 'exit' to quit): Bishkek

Weather for Bishkek
Temperature: 22.3 °C
Feels like: 20.5 °C
Description: clear sky
Time: 2024-05-08T13:42:11
```

---

## Author

**Bakyt Kudaibergenov, SFW-121, ID-10330**  

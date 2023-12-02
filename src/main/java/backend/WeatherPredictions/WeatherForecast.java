package backend.WeatherPredictions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class WeatherForecast {
    private double lat;
    private double lon;
    private String timezone;
    private int timezoneOffset;
    private ArrayList<DailyForecast> dailyForecasts;

    public WeatherForecast() {}

    public WeatherForecast(double lat, double lon, String timezone, int timezoneOffset) {
        this.lat = lat;
        this.lon = lon;
        this.timezone = timezone;
        this.timezoneOffset = timezoneOffset;
        this.dailyForecasts = new ArrayList<>();
    }

    public WeatherForecast(double lat, double lon, String timezone, int timezoneOffset, ArrayList<DailyForecast> dailyForecasts) {
        this.lat = lat;
        this.lon = lon;
        this.timezone = timezone;
        this.timezoneOffset = timezoneOffset;
        this.dailyForecasts = dailyForecasts;
    }

    public static WeatherForecast fromJsonString(String jsonString) {
        JSONObject json = new JSONObject(jsonString);

        double lat = json.getDouble("lat");
        double lon = json.getDouble("lon");
        String timezone = json.getString("timezone");
        int timezoneOffset = json.getInt("timezone_offset");

        ArrayList<DailyForecast> dailyForecasts = new ArrayList<>();
        JSONArray dailyArray = json.getJSONArray("daily");
        for (int i = 0; i < dailyArray.length(); i++) {
            JSONObject dailyJson = dailyArray.getJSONObject(i);
            DailyForecast dailyForecast = DailyForecast.fromJson(dailyJson);
            dailyForecasts.add(dailyForecast);
        }

        return new WeatherForecast(lat, lon, timezone, timezoneOffset, dailyForecasts);
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public String getTimezone() {
        return timezone;
    }

    public int getTimezoneOffset() {
        return timezoneOffset;
    }

    public ArrayList<DailyForecast> getDailyForecasts() {
        return dailyForecasts;
    }

    // New method to get today's forecast
    public DailyForecast getTodayForecast() {
        // Assuming dailyForecasts is ordered chronologically
        if (!dailyForecasts.isEmpty()) {
            return dailyForecasts.get(0); // Return the forecast for the first day (today)
        }
        return null; // Return null if the forecast is empty
    }

    public void displayWeatherData() {
        System.out.println("Latitude: " + lat);
        System.out.println("Longitude: " + lon);
        System.out.println("Timezone: " + timezone);
        System.out.println("Timezone Offset: " + timezoneOffset);

        ArrayList<DailyForecast> dailyForecasts = getDailyForecasts();
        System.out.println("Daily Forecasts:");

        for (DailyForecast forecast : dailyForecasts) {
            forecast.displayDailyForecast();
        }
    }

    public void displayTodaysForecast() {
        DailyForecast todayForecast = getTodayForecast();

        if (todayForecast != null) {
            todayForecast.displayDailyForecast();
        } else {
            System.out.println("No forecast available for today.");
        }
    }
}
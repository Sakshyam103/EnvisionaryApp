package backend.WeatherPredictions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class WeatherForecastAPI {
    private static final String API_ENDPOINT = "https://api.openweathermap.org/data/3.0/onecall?lat=43.455345&lon=-76.510498&exclude=current,minutely,hourly,alerts&appid=8515404e4156fe7b2a0ebe8247ec284b";

    public static DailyForecast getTodaysWeather() throws IOException {
        try {
            URL url = new URL(API_ENDPOINT);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();

            if (responseCode == 200) {
                JSONObject jsonObject = new JSONObject(new Scanner(url.openStream()).useDelimiter("\\A").next());
                WeatherForecast weatherForecast = WeatherForecast.fromJsonString(jsonObject.toString());
                return weatherForecast.getTodayForecast();
            } else {
                throw new RuntimeException("Failed to fetch weather data. Response code: " + responseCode);
            }
        } catch (IOException | JSONException e) {
            throw new RuntimeException("Error connecting to the API", e);
        }
    }

    public static void main(String[] args) {
        try {
            DailyForecast todaysWeather = getTodaysWeather();
            if (todaysWeather != null) {
                System.out.println("Today's Weather Data: ");
                todaysWeather.displayDailyForecast();
            } else {
                System.out.println("No forecast available for today.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
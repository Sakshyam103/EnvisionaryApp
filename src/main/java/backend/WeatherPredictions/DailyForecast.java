package backend.WeatherPredictions;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DailyForecast {
    private long dt;
    private long sunrise;
    private long sunset;
    private Temp temp;
    private double pop;

    public DailyForecast(){}

    public DailyForecast(long dt, long sunrise, long sunset, Temp temp, double pop) {
        this.dt = dt;
        this.sunrise = sunrise;
        this.sunset = sunset;
        this.temp = temp;
        this.pop = pop;
    }

    public long getDt() {
        return dt;
    }

    public long getSunrise() {
        return sunrise;
    }

    public long getSunset() {
        return sunset;
    }

    public Temp getTemp() {
        return temp;
    }

    public double getPop() {
        return pop;
    }

    public void displayDailyForecast() {
        LocalDateTime date = Instant.ofEpochSecond(getDt()).atZone(ZoneId.systemDefault()).toLocalDateTime();
        String formattedDate = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        LocalDateTime sunriseTime = Instant.ofEpochSecond(getSunrise()).atZone(ZoneId.systemDefault()).toLocalDateTime();
        String formattedSunrise = sunriseTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));

        LocalDateTime sunsetTime = Instant.ofEpochSecond(getSunset()).atZone(ZoneId.systemDefault()).toLocalDateTime();
        String formattedSunset = sunsetTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));

        System.out.println("Date: " + formattedDate);
        System.out.println("Sunrise: " + formattedSunrise);
        System.out.println("Sunset: " + formattedSunset);

        Temp temp = getTemp();
        System.out.println("Temperature - Day: " + kelvinToFahrenheit(temp.getDay()) + " °F");
        System.out.println("Temperature - Min: " + kelvinToFahrenheit(temp.getMin()) + " °F");
        System.out.println("Temperature - Max: " + kelvinToFahrenheit(temp.getMax()) + " °F");
        System.out.println("Temperature - Night: " + kelvinToFahrenheit(temp.getNight()) + " °F");
        System.out.println("Temperature - Evening: " + kelvinToFahrenheit(temp.getEve()) + " °F");
        System.out.println("Temperature - Morning: " + kelvinToFahrenheit(temp.getMorn()) + " °F");

        // Format probability of precipitation as percentage
        int popPercentage = (int) Math.round(getPop() * 100);
        System.out.println("Probability of Precipitation: " + popPercentage + "%");
        System.out.println("----------------------------------------");
    }

    private double kelvinToFahrenheit(double k) {
        return 1.8 * (k - 273) + 32;
    }

    public static DailyForecast fromJson(JSONObject json) throws JSONException {
        long dt = json.getLong("dt");
        long sunrise = json.getLong("sunrise");
        long sunset = json.getLong("sunset");
        Temp temp = Temp.fromJson(json.getJSONObject("temp"));
        double pop = json.getDouble("pop");

        return new DailyForecast(dt, sunrise, sunset, temp, pop);
    }
}
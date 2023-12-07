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
    private double pop;
    private int dayTemp;
    private int minTemp;
    private int maxTemp;
    private int nightTemp;
    private int eveningTemp;
    private int morningTemp;

    public DailyForecast(){}

    public DailyForecast(long dt, long sunrise, long sunset, double pop, int dayTemp, int minTemp, int maxTemp, int nightTemp, int eveningTemp, int morningTemp) {
        this.dt = dt;
        this.sunrise = sunrise;
        this.sunset = sunset;
        this.pop = pop;
        this.dayTemp = kelvinToFahrenheit(dayTemp);
        this.minTemp = kelvinToFahrenheit(minTemp);
        this.maxTemp = kelvinToFahrenheit(maxTemp);
        this.nightTemp = kelvinToFahrenheit(nightTemp);
        this.eveningTemp = kelvinToFahrenheit(eveningTemp);
        this.morningTemp = kelvinToFahrenheit(morningTemp);
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

    public double getPop() {
        return pop;
    }
    public int getDayTemp() {
        return dayTemp;
    }

    public int getMinTemp() {
        return minTemp;
    }

    public int getMaxTemp() {
        return maxTemp;
    }

    public int getNightTemp() {
        return nightTemp;
    }

    public int getEveTemp() {
        return eveningTemp;
    }

    public int getMornTemp() {
        return morningTemp;
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
        System.out.println("Temperature - Min: " + getMinTemp() + " °F");
        System.out.println("Temperature - Max: " + getMaxTemp() + " °F");
        System.out.println("Temperature - Morning: " + getMornTemp() + " °F");
        System.out.println("Temperature - Day: " + getDayTemp() + " °F");
        System.out.println("Temperature - Evening: " + getEveTemp() + " °F");
        System.out.println("Temperature - Night: " + getNightTemp() + " °F");

        // Format probability of precipitation as percentage
        int popPercentage = (int) Math.round(getPop() * 100);
        System.out.println("Probability of Precipitation: " + popPercentage + "%");
        System.out.println("----------------------------------------");
    }

    private int kelvinToFahrenheit(double k) {
        double conversion = 1.8 * (k - 273) + 32;
        return (int)conversion;
    }

    public static DailyForecast fromJson(JSONObject json) throws JSONException {
        long dt = json.getLong("dt");
        long sunrise = json.getLong("sunrise");
        long sunset = json.getLong("sunset");
        double pop = json.getDouble("pop");
        int dayTemp = json.getJSONObject("temp").getInt("day");
        int minTemp = json.getJSONObject("temp").getInt("min");
        int maxTemp = json.getJSONObject("temp").getInt("max");
        int nightTemp = json.getJSONObject("temp").getInt("night");
        int eveningTemp = json.getJSONObject("temp").getInt("eve");
        int morningTemp = json.getJSONObject("temp").getInt("morn");

        return new DailyForecast(dt, sunrise, sunset, pop, dayTemp, minTemp, maxTemp, nightTemp, eveningTemp, morningTemp);
    }
}
package backend.WeatherPredictions;

import org.json.JSONException;
import org.json.JSONObject;

public class Temp {
    private int day;
    private int min;
    private int max;
    private int night;
    private int eve;
    private int morn;

    public Temp(int day, int min, int max, int night, int eve, int morn) {
        this.day = day;
        this.min = min;
        this.max = max;
        this.night = night;
        this.eve = eve;
        this.morn = morn;
    }

    public int getDay() {
        return day;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    public int getNight() {
        return night;
    }

    public int getEve() {
        return eve;
    }

    public int getMorn() {
        return morn;
    }

    public Temp() {}

    public static Temp fromJson(JSONObject json) throws JSONException {
        int day = (int) Math.round(json.getDouble("day"));
        int min = (int) Math.round(json.getDouble("min"));
        int max = (int) Math.round(json.getDouble("max"));
        int night = (int) Math.round(json.getDouble("night"));
        int eve = (int) Math.round(json.getDouble("eve"));
        int morn = (int) Math.round(json.getDouble("morn"));

        return new Temp(day, min, max, night, eve, morn);
    }
}
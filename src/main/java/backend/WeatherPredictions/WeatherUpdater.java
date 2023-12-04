package backend.WeatherPredictions;

import java.io.IOException;

public class WeatherUpdater {
    public static void updateWeatherMongoDB() throws IOException {
        DailyForecast todaysWeather = WeatherForecastAPI.getTodaysWeather();
        DailyForecast previousWeatherData = MongoDBWeatherData.retrieveCollection();
        if(previousWeatherData == null){
            System.out.println("Initial update to today's weather data.");
            MongoDBWeatherData.insertIndividualDocument(todaysWeather);
        }
        else {
            System.out.println("Update to today's weather data.");
            MongoDBWeatherData.updateDocument(todaysWeather);
        }
    }

    public static void main(String[] args) throws IOException {
        updateWeatherMongoDB();
    }
}

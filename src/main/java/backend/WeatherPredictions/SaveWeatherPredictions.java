package backend.WeatherPredictions;

import backend.Controller;
import backend.CustomPredictions.CustomPrediction;
import backend.GetUserInfo;
import backend.OverallStatistics.OverallDescriptiveStatisticsUpdater;
import backend.OverallStatistics.OverallInferentialStatisticsUpdater;
import backend.UserStatistics.UserDescriptiveStatisticsUpdater;
import backend.UserStatistics.UserInferentialStatisticsUpdater;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.conversions.Bson;

import javax.json.JsonObject;
import java.time.ZonedDateTime;
import java.util.List;

public class SaveWeatherPredictions {
    private static final WeatherPrediction prediction = new WeatherPrediction();

    public static boolean buildWeather(JsonObject input){
        prediction.getPrediction().setPredictionMadeDate(ZonedDateTime.now().toString());
        prediction.getPrediction().setPredictionType("Weather");
        prediction.getPrediction().setPredictionContent("I predict that there will be a "
        + input.getString("temperatureType").toLowerCase()+ " of " + input.getInt("temperatureValue")+
                " F on " + input.getString("ResolveDate"));
        prediction.getPrediction().setPredictionEndDate(input.getString("ResolveDate"));
        prediction.setTemperature(input.getInt("temperatureValue"));
        if(input.getString("temperatureType").equalsIgnoreCase("High")){
            prediction.setHighTempPrediction(true);
        }
        else{
            prediction.setHighTempPrediction(false);
        }
        return saveNewWeatherToMongo();
    }

    private static boolean saveNewWeatherToMongo(){
        Bson filter = Filters.eq("userID", Controller.userId);


        Document predictionObject = new Document("predictionType", prediction.getPrediction().getPredictionType())
                .append("predictionContent", prediction.getPrediction().getPredictionContent())
                .append("remindFrequency", prediction.getPrediction().getRemindFrequency())
                .append("createDate", prediction.getPrediction().getPredictionMadeDate())
                .append("resolveDate", prediction.getPrediction().getPredictionEndDate());

        Document weatherObject = new Document("highTemp", prediction.getHighTempPrediction())
                .append("predictedTemp", prediction.getTemperature())
                .append("prediction", predictionObject);

        Bson update = Updates.push("weatherPredictions", weatherObject);


        try{
            GetUserInfo.envisionaryUsersCollection.updateOne(filter, update);
            return true;
        }
        catch(Exception ex){
            ex.printStackTrace();
            return false;
        }

    }

    public static boolean resolveWeatherPrediction(JsonObject data){
        String content = data.getString("predictionContent");
        WeatherPrediction active = getWeatherFromMongo(content);
        Bson filter = Filters.eq("userID", Controller.userId);



        Document newResolved = new Document("predictionType", active.getPrediction().getPredictionType())
                .append("predictionContent", active.getPrediction().getPredictionContent())
                .append("createDate", active.getPrediction().getPredictionMadeDate())
                .append("endDate", active.getPrediction().getPredictionEndDate())
                .append("resolution", data.getBoolean("resolution"))
                .append("resolvedDate", ZonedDateTime.now().toString());
        Bson update = Updates.push("customPredictions", newResolved);

        try{
            GetUserInfo.envisionaryUsersCollection.updateOne(filter, update);
            boolean delete = DeleteStaleWeatherPrediction(active);
            // Update UserStatistics.UserDescriptiveStatistics, UserStatistics.UserInferentialStatistics, and OverallStatistics
            UserDescriptiveStatisticsUpdater.calculateAndSaveUserDescriptiveStatisticsMongoDB(Controller.userId);
            UserInferentialStatisticsUpdater.calculateAndSaveUserInferentialStatisticsMongoDB(Controller.userId);
            OverallDescriptiveStatisticsUpdater.calculateAndSaveOverallDescriptiveStatisticsMongoDB();
            OverallInferentialStatisticsUpdater.calculateAndSaveOverallInferentialStatisticsMongoDB();
            return delete;
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    private static boolean DeleteStaleWeatherPrediction(WeatherPrediction active) {
        Document removal = new Document();
        return Controller.userDoc.getList("weatherPredictions", WeatherPrediction.class).remove(active);
    }

    private static WeatherPrediction getWeatherFromMongo(String content){
        WeatherPrediction current = new WeatherPrediction();
        List<WeatherPrediction> activeWeather = Controller.userDoc.getList("weatherPredictions", WeatherPrediction.class);
        for(WeatherPrediction prediction : activeWeather){
            if(prediction.getPrediction().getPredictionContent().equalsIgnoreCase(content)){
                current = prediction;
            }
        }
        return current;
    }
}

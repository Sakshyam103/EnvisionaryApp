package backend.FootballMatchPredictions;

import backend.Controller;
import backend.GetUserInfo;
import backend.OverallStatistics.OverallDescriptiveStatisticsUpdater;
import backend.OverallStatistics.OverallInferentialStatisticsUpdater;
import backend.UserStatistics.UserDescriptiveStatisticsUpdater;
import backend.UserStatistics.UserInferentialStatisticsUpdater;
import backend.WeatherPredictions.WeatherPrediction;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.conversions.Bson;

import javax.json.JsonObject;
import java.time.ZonedDateTime;
import java.util.List;

public class SaveFootballPredictions {
    private static final FootballMatchPrediction prediction = new FootballMatchPrediction();

    public static boolean buildFootball(JsonObject input){
        prediction.getPrediction().setPredictionMadeDate(ZonedDateTime.now().toString());
        prediction.getPrediction().setPredictionType("Football");
        if(input.getString("result").equalsIgnoreCase("draw")){
            prediction.getPrediction().setPredictionContent("I predict that " + input.getString("team") +
                    " will have a draw the game on " + input.getJsonObject("match").getString("utcDate"));
        }
        else{
            prediction.getPrediction().setPredictionContent("I predict that " + input.getString("team") +
                    " will " + input.getString("result") + " the game on " + input.getString("match"));
        }
        prediction.getPrediction().setPredictionEndDate(input.getJsonObject("match").getString("utcDate"));
        prediction.setPredictedMatchOutcome(input.getString("result"));
        prediction.setPredictedMatchTeam(input.getString("team"));
        prediction.getPrediction().setRemindFrequency("Standard");

        return saveNewFootballToMongo();
    }

    private static boolean saveNewFootballToMongo(){
        Bson filter = Filters.eq("userID", Controller.userId);


        Document predictionDocument = new Document("predictionType", prediction.getPrediction().getPredictionType())
                .append("predictionContent", prediction.getPrediction().getPredictionContent())
                .append("remindFrequency", prediction.getPrediction().getRemindFrequency())
                .append("predictionMadeDate", prediction.getPrediction().getPredictionMadeDate())
                .append("predictionEndDate", prediction.getPrediction().getPredictionEndDate());

        Document footballMatchPredictionDocument = new Document("match", prediction.getPredictionMatch())
                .append("team", prediction.getPredictedMatchTeam())
                .append("result", prediction.getPredictedMatchOutcome())
                .append("prediction", predictionDocument);

        Bson update = Updates.push("footballMatchPredictions", footballMatchPredictionDocument);


        try{
            GetUserInfo.envisionaryUsersCollection.updateOne(filter, update);
            return true;
        }
        catch(Exception ex){
            ex.printStackTrace();
            return false;
        }

    }

    public static boolean resolveFootballPrediction(JsonObject data){
        String content = data.getString("predictionContent");
        FootballMatchPrediction active = getFootballFromMongo(content);
        Bson filter = Filters.eq("userID", Controller.userId);

        Document newResolved = new Document("predictionType", active.getPrediction().getPredictionType())
                .append("predictionContent", active.getPrediction().getPredictionContent())
                .append("createDate", active.getPrediction().getPredictionMadeDate())
                .append("endDate", active.getPrediction().getPredictionEndDate())
                .append("resolution", data.getBoolean("resolution"))
                .append("resolvedDate", ZonedDateTime.now().toString());
        Bson update = Updates.push("resolvedPredictions", newResolved);

        try{
            GetUserInfo.envisionaryUsersCollection.updateOne(filter, update);
            boolean delete = DeleteStaleFootballPrediction(active);
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

    private static boolean DeleteStaleFootballPrediction(FootballMatchPrediction active) {
        Document removal = new Document();
        return Controller.userDoc.getList("footballMatchPredictions", FootballMatchPrediction.class).remove(active);
    }

    private static FootballMatchPrediction getFootballFromMongo(String content){
        FootballMatchPrediction current = new FootballMatchPrediction();
        List<FootballMatchPrediction> activeFootball = Controller.userDoc.getList("footballMatchPredictions", FootballMatchPrediction.class);
        for(FootballMatchPrediction prediction : activeFootball){
            if(prediction.getPrediction().getPredictionContent().equalsIgnoreCase(content)){
                current = prediction;
            }
        }
        return current;
    }

}

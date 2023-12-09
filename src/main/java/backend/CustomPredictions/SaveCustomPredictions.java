package backend.CustomPredictions;

import backend.BasePredictionObject.Prediction;
import backend.Controller;
import backend.GetUserInfo;
import backend.OverallStatistics.OverallDescriptiveStatisticsUpdater;
import backend.OverallStatistics.OverallInferentialStatisticsUpdater;
import backend.ResolvedPredictions.ResolvedPrediction;
import backend.UserStatistics.UserDescriptiveStatisticsUpdater;
import backend.UserStatistics.UserInferentialStatisticsUpdater;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.StringReader;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static backend.UserInfo.MongoDBEnvisionaryUsers.retrieveUserResolvedPredictions;

public class SaveCustomPredictions {
    private static final CustomPrediction prediction = new CustomPrediction();

    public static boolean buildCustom(JSONObject input) {
        prediction.getPrediction().setPredictionMadeDate(ZonedDateTime.now().toString());
        prediction.getPrediction().setPredictionType("Custom");
        prediction.getPrediction().setPredictionContent(input.getString("Prediction"));
        prediction.getPrediction().setPredictionEndDate(input.getString("ResolveDate"));
        prediction.getPrediction().setRemindFrequency("Standard");
        return saveNewCustomToMongo();
    }

    private static boolean saveNewCustomToMongo() {
        Bson filter = Filters.eq("userID", Controller.userId);

        Document predictionDocument = new Document()
                .append("predictionType", prediction.getPrediction().getPredictionType())
                .append("predictionContent", prediction.getPrediction().getPredictionContent())
                .append("remindFrequency", prediction.getPrediction().getRemindFrequency())
                .append("predictionMadeDate", prediction.getPrediction().getPredictionMadeDate())
                .append("predictionEndDate", prediction.getPrediction().getPredictionEndDate());

        Document customPredictionDocument = new Document()
                .append("prediction", predictionDocument);

        Bson update = Updates.push("customPredictions", customPredictionDocument);

        try {
            GetUserInfo.envisionaryUsersCollection.updateOne(filter, update);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public static boolean resolveCustomPrediction(JSONObject data) {
        String content = data.getString("predictionContent");
        CustomPrediction active = getCustomFromMongo(content);
        Bson filter = Filters.eq("userID", Controller.userId);

        Document newResolved = new Document("predictionType", active.getPrediction().getPredictionType())
                .append("predictionContent", active.getPrediction().getPredictionContent())
                .append("predictionMadeDate", active.getPrediction().getPredictionMadeDate())
                .append("predictionEndDate", active.getPrediction().getPredictionEndDate())
                .append("resolution", data.getBoolean("resolution"))
                .append("resolvedDate", ZonedDateTime.now().toString());
        Bson update = Updates.push("resolvedPredictions", newResolved);

        try {
            GetUserInfo.envisionaryUsersCollection.updateOne(filter, update);
            boolean delete = DeleteStaleCustomPrediction(active);
            // Update UserStatistics.UserDescriptiveStatistics, UserStatistics.UserInferentialStatistics, and OverallStatistics
            UserDescriptiveStatisticsUpdater.calculateAndSaveUserDescriptiveStatisticsMongoDB(Controller.userId);
            UserInferentialStatisticsUpdater.calculateAndSaveUserInferentialStatisticsMongoDB(Controller.userId);
            OverallDescriptiveStatisticsUpdater.calculateAndSaveOverallDescriptiveStatisticsMongoDB();
            OverallInferentialStatisticsUpdater.calculateAndSaveOverallInferentialStatisticsMongoDB();
            return delete;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static boolean DeleteStaleCustomPrediction(CustomPrediction active) {
        Document removal = new Document();
        return Controller.userDoc.getList("customPredictions", CustomPrediction.class).remove(active);
    }

    private static CustomPrediction getCustomFromMongo(String content) {
        CustomPrediction current = new CustomPrediction();
        List<CustomPrediction> activeCustom = Controller.userDoc.getList("customPredictions", CustomPrediction.class);
        for (CustomPrediction prediction : activeCustom) {
            if (prediction.getPrediction().getPredictionContent().equalsIgnoreCase(content)) {
                current = prediction;
            }
        }
        return current;
    }

    public static ArrayList<CustomPrediction> getAllCustomFromMongo() {
        String jsonDoc = Controller.userDoc.toJson();
        StringReader stringReader = new StringReader(jsonDoc);
        try {
            JSONObject object = new JSONObject(new JSONTokener(stringReader));
            JSONArray array = object.getJSONArray("customPredictions");
            ArrayList<CustomPrediction> predictions = new ArrayList<>();
            for (int i = 0; i < array.length(); i++) {
                JSONObject value = array.getJSONObject(i);
                CustomPrediction newCustomPrediction = new CustomPrediction();
                newCustomPrediction.getPrediction().setPredictionType(value.getString("predictionType"));
                newCustomPrediction.getPrediction().setPredictionContent(value.getString("predictionContent"));
                newCustomPrediction.getPrediction().setPredictionMadeDate(value.getString("predictionMadeDate"));
                newCustomPrediction.getPrediction().setPredictionEndDate(value.getString("predictionEndDate"));
                predictions.add(newCustomPrediction);
            }
            return predictions;
        } catch (JSONException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static ArrayList<ResolvedPrediction> retrieveUserResolvedPredictions() {
        String jsonDoc = Controller.userDoc.toJson();
        StringReader stringReader = new StringReader(jsonDoc);
        try {
            JSONObject object = new JSONObject(new JSONTokener(stringReader));
            JSONArray array = object.getJSONArray("resolvedPredictions");
            ArrayList<ResolvedPrediction> predictions = new ArrayList<>();
            for (int i = 0; i < array.length(); i++) {
                JSONObject value = array.getJSONObject(i);
                ResolvedPrediction newResolvedCustomPrediction = new ResolvedPrediction();
                newResolvedCustomPrediction.setPredictionMadeDate(value.getString("predictionMadeDate"));
                newResolvedCustomPrediction.setPredictionType(value.getString("predictionType"));
                newResolvedCustomPrediction.setPredictionContent(value.getString("predictionContent"));
                newResolvedCustomPrediction.setPredictionEndDate(value.getString("predictionEndDate"));
                predictions.add(newResolvedCustomPrediction);
            }
            return predictions;
        } catch (JSONException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}

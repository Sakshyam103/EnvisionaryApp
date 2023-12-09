package backend.CelestialBodyPredictions;

import backend.BasePredictionObject.Prediction;
import backend.Controller;
import backend.GetUserInfo;
import backend.OverallStatistics.OverallDescriptiveStatisticsUpdater;
import backend.OverallStatistics.OverallInferentialStatisticsUpdater;
import backend.UserStatistics.UserDescriptiveStatisticsUpdater;
import backend.UserStatistics.UserInferentialStatisticsUpdater;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;

import java.io.StringReader;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class SavePlanets {
    private static final CelestialBodyPrediction prediction = new CelestialBodyPrediction();

    public static boolean buildSpace(JSONObject input) {
        prediction.getPrediction().setPredictionMadeDate(ZonedDateTime.now().toString());
        prediction.getPrediction().setPredictionType("CelestialBody");
        prediction.getPrediction().setPredictionEndDate(input.getString("resolveDate"));
        prediction.getCelestialBody().setCelestialBodyType(input.getString("value"));
        prediction.getPrediction().setPredictionContent("I predict that " + input.getString("value") +
                " will have a new amount by " + input.getString("resolveDate"));
        prediction.getPrediction().setRemindFrequency("Standard");
        return saveNewSpaceToMongo();
    }

    private static boolean saveNewSpaceToMongo() {
        Bson filter = Filters.eq("userID", Controller.userId);

        Document predictionObject = new Document("predictionType", prediction.getPrediction().getPredictionType())
                .append("predictionContent", prediction.getPrediction().getPredictionContent())
                .append("remindFrequency", prediction.getPrediction().getRemindFrequency())
                .append("predictionMadeDate", prediction.getPrediction().getPredictionMadeDate())
                .append("predictionEndDate", prediction.getPrediction().getPredictionEndDate());

        Document celestialBodyPredictionDocument = new Document("celestialBodyType", prediction.getCelestialBody().getCelestialBodyType())
                .append("knownCount", prediction.getCelestialBody().getKnownCount())
                .append("updatedDate", prediction.getCelestialBody().getUpdatedDate())
                .append("prediction", predictionObject);

        Bson update = Updates.push("celestialBodyPredictions", celestialBodyPredictionDocument);

        try {
            GetUserInfo.envisionaryUsersCollection.updateOne(filter, update);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public static boolean resolvePlanetPredictions(JSONObject data) {
        String content = data.getString("predictionContent");
        CelestialBodyPrediction active = getSpaceFromMongo(content);
        Bson filter = Filters.eq("userID", Controller.userId);

        Document newResolved = new Document("predictionType", active.getPrediction().getPredictionType())
                .append("predictionContent", active.getPrediction().getPredictionContent())
                .append("createDate", active.getPrediction().getPredictionMadeDate())
                .append("endDate", active.getPrediction().getPredictionEndDate())
                .append("resolution", data.getBoolean("resolution"))
                .append("resolvedDate", ZonedDateTime.now().toString());
        Bson update = Updates.push("resolvedPredictions", newResolved);

        try {
            GetUserInfo.envisionaryUsersCollection.updateOne(filter, update);
            boolean delete = DeleteStaleSpacePrediction(active);
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

    private static boolean DeleteStaleSpacePrediction(CelestialBodyPrediction active) {
        Document removal = new Document();
        return Controller.userDoc.getList("celestialBodyPredictions", CelestialBodyPrediction.class).remove(active);
    }

    private static CelestialBodyPrediction getSpaceFromMongo(String content) {
        CelestialBodyPrediction current = new CelestialBodyPrediction();
        List<CelestialBodyPrediction> activeSpace = Controller.userDoc.getList("celestialBodyPredictions", CelestialBodyPrediction.class);
        for (CelestialBodyPrediction prediction : activeSpace) {
            if (prediction.getPrediction().getPredictionContent().equalsIgnoreCase(content)) {
                current = prediction;
            }
        }
        return current;
    }

    public static ArrayList<Prediction> getAllPlanetsFromMongo() {
        String jsonDoc = Controller.userDoc.toJson();
        StringReader stringReader = new StringReader(jsonDoc);
        try {
            JSONObject object = new JSONObject(new JSONTokener(stringReader));
            JSONArray array = object.getJSONArray("celestialBodyPredictions");
            ArrayList<Prediction> predictions = new ArrayList<>();

            for (int i = 0; i < array.length(); i++) {
                JSONObject value = array.getJSONObject(i);
                Prediction sample = new Prediction();
                sample.setPredictionMadeDate(value.getJSONObject("prediction").getString("predictionMadeDate"));
                sample.setPredictionType(value.getJSONObject("prediction").getString("predictionType"));
                sample.setPredictionContent(value.getJSONObject("prediction").getString("predictionContent"));
                sample.setPredictionEndDate(value.getJSONObject("prediction").getString("predictionEndDate"));
                sample.setRemindFrequency(value.getJSONObject("prediction").getString("remindFrequency"));

                predictions.add(sample);
            }
            return predictions;
        } catch (JSONException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
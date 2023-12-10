package backend.CustomPredictions;

import backend.BasePredictionObject.Prediction;
import backend.Controller;
import backend.FootballMatchPredictions.FootballMatchPrediction;
import backend.GetUserInfo;
import backend.OverallStatistics.OverallDescriptiveStatisticsUpdater;
import backend.OverallStatistics.OverallInferentialStatisticsUpdater;
import backend.ResolvedPredictions.ResolvedPrediction;
import backend.UserStatistics.UserDescriptiveStatisticsUpdater;
import backend.UserStatistics.UserInferentialStatisticsUpdater;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;

import javax.json.*;
import java.io.StringReader;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static backend.UserInfo.MongoDBEnvisionaryUsers.retrieveCollection;
import static backend.UserInfo.MongoDBEnvisionaryUsers.retrieveUserResolvedPredictions;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;


public class SaveCustomPredictions {

    private static final CustomPrediction prediction = new CustomPrediction();

    public static boolean buildCustom(JsonObject input){
        prediction.getPrediction().setPredictionMadeDate(ZonedDateTime.now().toString());
        prediction.getPrediction().setPredictionType("Custom");
        prediction.getPrediction().setPredictionContent(input.getString("Prediction"));
        prediction.getPrediction().setPredictionEndDate(input.getString("ResolveDate"));
        prediction.getPrediction().setRemindFrequency("Standard");
        return saveNewCustomToMongo();
    }

    private static boolean saveNewCustomToMongo(){
        Bson filter = Filters.eq("userID", Controller.userId);

        Document customPredictionDocument = new Document("predictionType", prediction.getPrediction().getPredictionType())
                .append("predictionContent", prediction.getPrediction().getPredictionContent())
                .append("remindFrequency", prediction.getPrediction().getRemindFrequency())
                .append("predictionMadeDate", prediction.getPrediction().getPredictionMadeDate())
                .append("predictionEndDate", prediction.getPrediction().getPredictionEndDate());
        Document prediction = new Document("prediction", customPredictionDocument);
        Bson update = Updates.push("customPredictions", prediction);


        try{
            GetUserInfo.envisionaryUsersCollection.updateOne(filter, update);
            return true;
        }
        catch(Exception ex){
            ex.printStackTrace();
            return false;
        }

    }

    public static boolean resolveCustomPrediction(JsonObject data){
        String content = data.getString("predictionContent");
        Prediction active = getCustomFromMongo(content);
        Bson filter = Filters.eq("userID", Controller.userId);

        Document newResolved = new Document("predictionType", active.getPredictionType())
                .append("predictionContent", active.getPredictionContent())
                .append("predictionMadeDate", active.getPredictionMadeDate())
                .append("predictionEndDate", active.getPredictionEndDate())
                .append("resolution", data.getBoolean("resolution"))
                .append("resolvedDate", ZonedDateTime.now().toString());
        Bson update = Updates.push("resolvedPredictions", newResolved);

        try{
            UpdateResult delete = GetUserInfo.envisionaryUsersCollection.updateOne(filter, update);

            // Update UserStatistics.UserDescriptiveStatistics, UserStatistics.UserInferentialStatistics, and OverallStatistics
            UserDescriptiveStatisticsUpdater.calculateAndSaveUserDescriptiveStatisticsMongoDB(Controller.userId);
            UserInferentialStatisticsUpdater.calculateAndSaveUserInferentialStatisticsMongoDB(Controller.userId);
            OverallDescriptiveStatisticsUpdater.calculateAndSaveOverallDescriptiveStatisticsMongoDB();
            OverallInferentialStatisticsUpdater.calculateAndSaveOverallInferentialStatisticsMongoDB();
            return delete.wasAcknowledged();
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    private static Prediction getCustomFromMongo(String content){
        String jsonDoc = Controller.userDoc.toJson();
        StringReader stringReader = new StringReader(jsonDoc);
        JsonReaderFactory factory = Json.createReaderFactory(null);
        JsonReader reader = factory.createReader(stringReader);
        JsonObject object = reader.readObject();
        JsonArray array = object.getJsonArray("customPredictions");
        Prediction newCustomPrediction = new Prediction();
        for(JsonValue value : array){
            if(value.asJsonObject().getJsonObject("prediction").getString("predictionContent").equalsIgnoreCase(content)){
                newCustomPrediction.setPredictionType(value.asJsonObject().getJsonObject("prediction").getString("predictionType"));
                newCustomPrediction.setPredictionContent(value.asJsonObject().getJsonObject("prediction").getString("predictionContent"));
                newCustomPrediction.setPredictionMadeDate(value.asJsonObject().getJsonObject("prediction").getString("predictionMadeDate"));
                newCustomPrediction.setPredictionEndDate(value.asJsonObject().getJsonObject("prediction").getString("predictionEndDate"));

                Document query = new Document("userID", Controller.userId);
                int index = array.indexOf(value);
                Document update = new Document("$unset", new Document("customPredictions." + index, ""));
                GetUserInfo.envisionaryUsersCollection.updateOne(query, update);
                UpdateResult result = GetUserInfo.envisionaryUsersCollection.updateOne(query, new Document("$pull", new Document("customPredictions", null)));
                System.out.println("Deletion Status : " + result.toString());
            }
        }
        return newCustomPrediction;
    }

    public static ArrayList<Prediction> getAllCustomFromMongo(){
        String jsonDoc = Controller.userDoc.toJson();
        StringReader stringReader = new StringReader(jsonDoc);
        JsonReaderFactory factory = Json.createReaderFactory(null);
        JsonReader reader = factory.createReader(stringReader);
        JsonObject object = reader.readObject();
        JsonArray array = object.getJsonArray("customPredictions");
        ArrayList<Prediction> predictions = new ArrayList<>();
        for(JsonValue value : array){
            Prediction sample = new Prediction();
            sample.setPredictionMadeDate(value.asJsonObject().getJsonObject("prediction").getString("predictionMadeDate"));
            sample.setPredictionType(value.asJsonObject().getJsonObject("prediction").getString("predictionType"));
            sample.setPredictionContent(value.asJsonObject().getJsonObject("prediction").getString("predictionContent"));
            sample.setPredictionEndDate(value.asJsonObject().getJsonObject("prediction").getString("predictionEndDate"));
            predictions.add(sample);
        }
        return predictions;
    }

    public static ArrayList<ResolvedPrediction> retrieveUserResolvedPredictions(){
        String jsonDoc = Controller.userDoc.toJson();
        StringReader stringReader = new StringReader(jsonDoc);
        JsonReaderFactory factory = Json.createReaderFactory(null);
        JsonReader reader = factory.createReader(stringReader);
        JsonObject object = reader.readObject();
        JsonArray array = object.getJsonArray("resolvedPredictions");
        ArrayList<ResolvedPrediction> predictions = new ArrayList<>();
        for(JsonValue value : array){
            ResolvedPrediction sample = new ResolvedPrediction();
            sample.setPredictionMadeDate(value.asJsonObject().getString("predictionMadeDate"));
            sample.setPredictionType(value.asJsonObject().getString("predictionType"));
            sample.setPredictionContent(value.asJsonObject().getString("predictionContent"));
            sample.setPredictionEndDate(value.asJsonObject().getString("predictionEndDate"));
            sample.setResolution(value.asJsonObject().getBoolean("resolution"));
            predictions.add(sample);
        }
        return predictions;
    }

    public static ArrayList<String> getCustomContent(){
        String jsonDoc = Controller.userDoc.toJson();
        StringReader stringReader = new StringReader(jsonDoc);
        JsonReaderFactory factory = Json.createReaderFactory(null);
        JsonReader reader = factory.createReader(stringReader);
        JsonObject object = reader.readObject();
        JsonArray array = object.getJsonArray("customPredictions");
        ArrayList<String> predictions = new ArrayList<>();
        for(JsonValue value : array){
            predictions.add(value.asJsonObject().getJsonObject("prediction").getString("predictionContent"));
        }
        return predictions;
    }

}




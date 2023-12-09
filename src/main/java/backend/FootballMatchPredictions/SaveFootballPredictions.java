package backend.FootballMatchPredictions;

import backend.BasePredictionObject.Prediction;
import backend.Controller;
import backend.GetUserInfo;
import backend.OverallStatistics.OverallDescriptiveStatisticsUpdater;
import backend.OverallStatistics.OverallInferentialStatisticsUpdater;
import backend.UserStatistics.UserDescriptiveStatisticsUpdater;
import backend.UserStatistics.UserInferentialStatisticsUpdater;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
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

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class SaveFootballPredictions {
    private static final FootballMatchPrediction prediction = new FootballMatchPrediction();

    public static boolean buildFootball(JsonObject input){
        FootballMatch match = findMatch(input);
        prediction.getPrediction().setPredictionMadeDate(ZonedDateTime.now().toString());
        prediction.getPrediction().setPredictionType("Football");
        if(input.getString("result").equalsIgnoreCase("draw")){
            prediction.getPrediction().setPredictionContent("I predict that " + input.getString("team") +
                    " will have a draw the game on " + match.getUtcDate().substring(0,10));
        }
        else{
            prediction.getPrediction().setPredictionContent("I predict that " + input.getString("team") +
                    " will " + input.getString("result") + " the game on " + match.getUtcDate().substring(0,10));
        }
        prediction.getPrediction().setPredictionEndDate(match.getUtcDate());
        prediction.setPredictedMatchOutcome(input.getString("result"));
        prediction.setPredictedMatchTeam(input.getString("team"));
        prediction.getPrediction().setRemindFrequency("Standard");
        prediction.setPredictionMatch(match);

        return saveNewFootballToMongo();
    }

    private static FootballMatch findMatch(JsonObject input) {
        MongoClient client = connectToMongoDBMatch();
        Bson query = Filters.eq("matchListTimeFrame", "UpcomingWeek1");
        MongoCollection<FootballMatchList> collection = client.getDatabase("Envisionary").getCollection("FootballMatchData", FootballMatchList.class);
        FootballMatchList list = collection.find(query).first();
        assert list != null;
        for(FootballMatch match : list.getFootballMatches()){
            if(input.getString("match").equalsIgnoreCase(match.getHomeTeam() + " vs " + match.getAwayTeam())){
                return match;
            }
        }
        return null;
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

    public static ArrayList<Prediction> getAllFootballFromMongo(){
        String jsonDoc = Controller.userDoc.toJson();
        StringReader stringReader = new StringReader(jsonDoc);
        JsonReaderFactory factory = Json.createReaderFactory(null);
        JsonReader reader = factory.createReader(stringReader);
        JsonObject object = reader.readObject();
        JsonArray array = object.getJsonArray("footballMatchPredictions");
        ArrayList<Prediction> predictions = new ArrayList<>();

        for(JsonValue value : array){
            Prediction sample = new Prediction();
            sample.setPredictionMadeDate(value.asJsonObject().getJsonObject("prediction").asJsonObject().getString("predictionMadeDate"));
            sample.setPredictionType(value.asJsonObject().getJsonObject("prediction").asJsonObject().getString("predictionType"));
            sample.setPredictionContent(value.asJsonObject().getJsonObject("prediction").asJsonObject().getString("predictionContent"));
            sample.setPredictionEndDate(value.asJsonObject().getJsonObject("prediction").asJsonObject().getString("predictionEndDate"));
            sample.setRemindFrequency(value.asJsonObject().getJsonObject("prediction").asJsonObject().getString("remindFrequency"));

            predictions.add(sample);
        }
        return predictions;
    }

    private static MongoClient connectToMongoDBMatch() {
        Logger.getLogger("org.mongodb.driver").setLevel(Level.WARNING);
        ConnectionString mongoUri = new ConnectionString("mongodb+srv://" + System.getenv("MONGO_USER") + ":" + System.getenv("MONGO_DB_PASSWORD") + "@" + System.getenv("MONGO_CLUSTER") + ".19uobkz.mongodb.net/?retryWrites=true&w=majority");
        CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));
        MongoClientSettings settings = MongoClientSettings.builder()
                .codecRegistry(pojoCodecRegistry)
                .applyConnectionString(mongoUri).build();
        return MongoClients.create(settings);
    }

}

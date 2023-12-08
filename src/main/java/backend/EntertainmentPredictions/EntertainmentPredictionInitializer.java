package backend.EntertainmentPredictions;

import backend.Controller;
import backend.CustomPredictions.CustomPrediction;
import backend.GetUserInfo;
import backend.OverallStatistics.OverallDescriptiveStatisticsUpdater;
import backend.OverallStatistics.OverallInferentialStatisticsUpdater;
import backend.ResolvedPredictions.ResolvedPrediction;
import backend.UserInfo.MongoDBEnvisionaryUsers;
import backend.UserStatistics.UserDescriptiveStatisticsUpdater;
import backend.UserStatistics.UserInferentialStatisticsUpdater;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.apache.catalina.core.ApplicationFilterRegistration;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class EntertainmentPredictionInitializer {

    public static boolean resolveEntertainmentPrediction(String userIdentifier, ResolvedPrediction prediction) {
        Bson filter = Filters.eq("userID", Controller.userId);

        Document newResolved = new Document("predictionContent", prediction.getPredictionContent())
                .append("predictionEndDate", prediction.getPredictionEndDate())
                .append("predictionMadeDate", prediction.getPredictionMadeDate())
                .append("predictionType", prediction.getPredictionType())
                .append("resolution", prediction.getResolution())
                .append("resolvedDate", prediction.getResolvedDate());
        Bson update = Updates.push("resolvedPredictions", newResolved);



        try{
            GetUserInfo.envisionaryUsersCollection.updateOne(filter, update);

            // Update UserStatistics.UserDescriptiveStatistics, UserStatistics.UserInferentialStatistics, and OverallStatistics
            UserDescriptiveStatisticsUpdater.calculateAndSaveUserDescriptiveStatisticsMongoDB(userIdentifier);
            UserInferentialStatisticsUpdater.calculateAndSaveUserInferentialStatisticsMongoDB(userIdentifier);
            OverallDescriptiveStatisticsUpdater.calculateAndSaveOverallDescriptiveStatisticsMongoDB();
            OverallInferentialStatisticsUpdater.calculateAndSaveOverallInferentialStatisticsMongoDB();
            return true;
        }
        catch(Exception error){
            error.printStackTrace();
            return false;
        }
    }

    public static boolean saveNewCustomMovieToMongo(ResolvedPrediction prediction){
        Bson filter = Filters.eq("userID", Controller.userId);
        CustomPrediction newPrediction = new CustomPrediction();

        newPrediction.getPrediction().setPredictionType("Custom");
        newPrediction.getPrediction().setPredictionContent(prediction.getPredictionContent());
        newPrediction.getPrediction().setPredictionMadeDate(ZonedDateTime.now().toString());

        Document newResolved = new Document("predictionType", newPrediction.getPrediction().getPredictionType())
                .append("predictionContent", newPrediction.getPrediction().getPredictionContent())
                .append("remindFrequency", newPrediction.getPrediction().getRemindFrequency())
                .append("createDate", newPrediction.getPrediction().getPredictionMadeDate())
                .append("resolveDate", newPrediction.getPrediction().getPredictionEndDate());
        Bson update = Updates.push("customPredictions", newResolved);


        try{
            GetUserInfo.envisionaryUsersCollection.updateOne(filter, update);
            return true;
        }
        catch(Exception ex){
            ex.printStackTrace();
            return false;
        }

    }
}

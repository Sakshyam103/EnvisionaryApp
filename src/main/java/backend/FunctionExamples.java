package backend;

import backend.CelestialBodyPredictions.CelestialBodyPredictionInitializer;
import backend.CustomPredictions.CustomPredictionInitializer;
import backend.FootballMatchPredictions.FootballMatchPredictionInitializer;
import backend.Notifications.NotificationUpdater;
import backend.UserInfo.MongoDBEnvisionaryUsers;


//■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
// FunctionExamples class - Written by Brandon LaPointe
//■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
// Provides functionality examples of all the classes.
//
public class FunctionExamples {
    public static void main(String[] args) {
        //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
        // METHODS USED TO RETURN DATA FROM MONGO DB THAT WILL HAVE TO BE MODIFIED FOR UI INPUT
        //
        CustomPredictionInitializer.createNewCustomPredictionMongoDB("bLapointe");

        CustomPredictionInitializer.removeCustomPredictionMongoDB("bLapointe");

        CustomPredictionInitializer.resolveCustomPredictionMongoDB("bLapointe");

        // For testing
        //FootballMatchPredictionInitializer.createNewFootballMatchYesterdayPredictionMongoDB("bLapointe");

        // For testing
        //FootballMatchPredictionInitializer.createNewFootballMatchTodayPredictionMongoDB("bLapointe");

        FootballMatchPredictionInitializer.createNewFootballMatchTomorrowPredictionMongoDB("bLapointe");

        FootballMatchPredictionInitializer.createNewFootballMatchUpcomingWeek1PredictionMongoDB("bLapointe");

        FootballMatchPredictionInitializer.createNewFootballMatchUpcomingWeek2PredictionMongoDB("bLapointe");

        FootballMatchPredictionInitializer.createNewFootballMatchUpcomingWeek3PredictionMongoDB("bLapointe");

        FootballMatchPredictionInitializer.removeFootballMatchPredictionMongoDB("bLapointe");

        CelestialBodyPredictionInitializer.createNewCelestialBodyPredictionMongoDB("bLapointe");

        CelestialBodyPredictionInitializer.removeCelestialBodyPredictionMongoDB("bLapointe");

        NotificationUpdater.removeUserNotificationMongoDB("bLapointe");

        NotificationUpdater.removeAllUserNotificationsMongoDB("bLapointe");

        //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
        // ALL METHODS USED TO RETURN DATA FROM MONGO DB THAT FETCH DATA FOR VIEWING
        //

        // Returns ArrayList<CustomPrediction>
        MongoDBEnvisionaryUsers.retrieveUserCustomPredictions("bLapointe");

        // Returns ArrayList<CelestialBodyPrediction>
        MongoDBEnvisionaryUsers.retrieveUserCelestialBodyPredictions("bLapointe");

        // Returns ArrayList<FootballMatchPrediction>
        MongoDBEnvisionaryUsers.retrieveUserFootballMatchPredictions("bLapointe");

        // Returns ArrayList<WeatherPrediction>
        MongoDBEnvisionaryUsers.retrieveUserWeatherPredictions("bLapointe");

        // Returns ArrayList<ResolvedPrediction>
        MongoDBEnvisionaryUsers.retrieveUserResolvedPredictions("bLapointe");

        // Returns ArrayList<Notification>
        MongoDBEnvisionaryUsers.retrieveUserNotifications("bLapointe");

        // Returns UserDescriptiveStatistics Object
        MongoDBEnvisionaryUsers.retrieveUserDescriptiveStatistics("bLapointe");

        // Returns UserInferentialStatistics Object
        MongoDBEnvisionaryUsers.retrieveUserInferentialStatistics("bLapointe");
    }
}
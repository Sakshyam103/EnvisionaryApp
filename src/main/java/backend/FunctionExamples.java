package backend;

import backend.CelestialBodyPredictions.CelestialBodyPrediction;
import backend.CelestialBodyPredictions.CelestialBodyPredictionInitializer;
import backend.CustomPredictions.CustomPrediction;

import backend.CustomPredictions.CustomPredictionInitializer;
import backend.FootballMatchPredictions.FootballMatchPrediction;
import backend.FootballMatchPredictions.FootballMatchPredictionInitializer;

import backend.Notifications.Notification;
import backend.Notifications.NotificationUpdater;
import backend.ResolvedPredictions.ResolvedPrediction;
import backend.UserInfo.MongoDBEnvisionaryUsers;
import backend.UserStatistics.UserDescriptiveStatistics;
import backend.UserStatistics.UserInferentialStatistics;
import backend.UserStatistics.UserInferentialStatisticsUpdater;
import backend.WeatherPredictions.WeatherPrediction;
import backend.WeatherPredictions.WeatherPredictionInitializer;

import java.util.ArrayList;



//■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
// FunctionExamples class - Written by Brandon LaPointe
//■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
// Provides functionality examples of all the classes needed for Frontend/Backend interaction.
//
public class FunctionExamples {
    public static void main(String[] args) {
        //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■

        // METHODS USED TO CREATE / REMOVE / RESOLVE PREDICTIONS WITHIN MONGO DB THAT WILL HAVE TO BE MODIFIED FOR UI INPUT
        //
//TODO        CustomPredictionInitializer.createNewCustomPredictionMongoDB("TestUser");
//
//TODO        CustomPredictionInitializer.removeCustomPredictionMongoDB("TestUser");
//                  Might have to pull data to frontend first and have user choice be an index value or something to set equality to in order to remove on backend?
//                  Something like:
//                      - ArrayList<CustomPrediction> userCustomPredictions = MongoDBEnvisionaryUsers.retrieveUserCustomPredictions(userId);
//                      - IF userCustomPredictions != null
//                          - Display these in some sort of selectable list and use removeCustomPredictionMongoDB(index, userId) to remove.
//                          (I already wrote the method - If you can do this I will write the rest of them like this)
//
//TODO        CustomPredictionInitializer.resolveCustomPredictionMongoDB("TestUser");
//                  Same as above
//
//TODO        FootballMatchPredictionInitializer.createNewFootballMatchUpcomingWeek1PredictionMongoDB("TestUser");
//
//TODO        FootballMatchPredictionInitializer.removeFootballMatchPredictionMongoDB("TestUser");
//                  Same as above
//
//TODO        CelestialBodyPredictionInitializer.createNewCelestialBodyPredictionMongoDB("TestUser");
//
//TODO        CelestialBodyPredictionInitializer.removeCelestialBodyPredictionMongoDB("TestUser");
//                  Same as above
//
//TODO        WeatherPredictionInitializer.createNewWeatherPredictionMongoDB("TestUser");
//
//TODO        WeatherPredictionIntializer.removeWeatherPredictionMongoDB("TestUser");
//                  Same as above
//
//         Create method for Entertainment Prediction
//
//         Remove method for Entertainment Prediction
//
//TODO        NotificationUpdater.removeUserNotificationMongoDB("TestUser");
//                  Same as above
//
//TODO        NotificationUpdater.removeAllUserNotificationsMongoDB("TestUser");
//                  Just need some sort of confirmation before removal in case it was an accidental press


        //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
        // ALL METHODS USED TO RETURN DATA FROM MONGO DB THAT FETCH DATA FOR VIEWING
        //


        // ArrayList<CustomPrediction> userCustomPredictions = MongoDBEnvisionaryUsers.retrieveUserCustomPredictions(userId);
        // Returns user's ArrayList<CustomPrediction>
        System.out.println("Fetching user custom predictions from MongoDB...");
        ArrayList<CustomPrediction> testCustomPredictions = MongoDBEnvisionaryUsers.retrieveUserCustomPredictions("TestUser");
        // HAVE TO ADD NULL CHECKS
        if (testCustomPredictions != null) {
            for (CustomPrediction testCustomPrediction : testCustomPredictions) {
                testCustomPrediction.getPrediction().printPredictionDetails();
            }
        }
        else {
            System.out.println("No custom predictions available for user.");
        }
        System.out.println();

        // ArrayList<CelestialBodyPrediction> userCelestialBodyPredictions = MongoDBEnvisionaryUsers.retrieveUserCelestialBodyPredictions(userId);
        // Returns user's ArrayList<CelestialBodyPrediction>
        System.out.println("Fetching user celestial body predictions from MongoDB...");
        ArrayList<CelestialBodyPrediction> testCelestialBodyPredictions = MongoDBEnvisionaryUsers.retrieveUserCelestialBodyPredictions("TestUser");
        // HAVE TO ADD NULL CHECKS
        if (testCelestialBodyPredictions != null) {
            for (CelestialBodyPrediction testCelestialBodyPrediction : testCelestialBodyPredictions) {
                testCelestialBodyPrediction.getPrediction().printPredictionDetails();
            }
        }
        else {
            System.out.println("No celestial body predictions available for user.");
        }
        System.out.println();

        // ArrayList<FootballMatchPrediction> userFootballMatchPredictions = MongoDBEnvisionaryUsers.retrieveUserFootballMatchPredictions(userId);
        // Returns user's ArrayList<FootballMatchPrediction>
        System.out.println("Fetching user football match predictions from MongoDB...");
        ArrayList<FootballMatchPrediction> testFootballMatchPredictions = MongoDBEnvisionaryUsers.retrieveUserFootballMatchPredictions("TestUser");
        // HAVE TO ADD NULL CHECKS
        if (testFootballMatchPredictions != null) {
            for (FootballMatchPrediction testFootballMatchPrediction : testFootballMatchPredictions) {
                testFootballMatchPrediction.getPrediction().printPredictionDetails();
            }
        }
        else {
            System.out.println("No football match predictions available for user.");
        }
        System.out.println();

        // ArrayList<WeatherPrediction> userWeatherPredictions = MongoDBEnvisionaryUsers.retrieveUserWeatherPredictions(userId);
        // Returns user's ArrayList<WeatherPrediction>
        System.out.println("Fetching user weather predictions from MongoDB...");
        ArrayList<WeatherPrediction> testWeatherPredictions = MongoDBEnvisionaryUsers.retrieveUserWeatherPredictions("TestUser");
        // HAVE TO ADD NULL CHECKS
        if (testWeatherPredictions != null) {
            for (WeatherPrediction testWeatherPrediction : testWeatherPredictions) {
                testWeatherPrediction.getPrediction().printPredictionDetails();
            }
        }
        else {
            System.out.println("No weather predictions available for user.");
        }
        System.out.println();

        // ArrayList<ResolvedPrediction> userResolvedPredictions = MongoDBEnvisionaryUsers.retrieveUserResolvedPredictions(userId);
        // Returns user's ArrayList<ResolvedPrediction>
        System.out.println("Fetching user resolved predictions from MongoDB...");
        ArrayList<ResolvedPrediction> testResolvedPredictions = MongoDBEnvisionaryUsers.retrieveUserResolvedPredictions("TestUser");
        // HAVE TO ADD NULL CHECKS
        if (testResolvedPredictions != null) {
            for (ResolvedPrediction testResolvedPrediction : testResolvedPredictions) {
                testResolvedPrediction.printPredictionDetails();
            }
        }
        else {
            System.out.println("No resolved predictions available for user.");
        }
        System.out.println();

        // MongoDBEnvisionaryUsers.retrieveUserNotifications(userId);
        // Returns user's ArrayList<Notification>
        System.out.println("Fetching user notifications from MongoDB...");
        ArrayList<Notification> testNotifications = MongoDBEnvisionaryUsers.retrieveUserNotifications("TestUser");
        // HAVE TO ADD NULL CHECKS
        if (testNotifications != null) {
            for (Notification testNotification : testNotifications) {
                testNotification.printNotification();
            }
        }
        else {
            System.out.println("No notifications available for user.");
        }
        System.out.println();

        // MongoDBEnvisionaryUsers.retrieveUserDescriptiveStatistics(userId);
        // Returns user's UserDescriptiveStatistics Object
        System.out.println("Fetching user descriptive statistics from MongoDB...");
        UserDescriptiveStatistics testUserDescriptiveStatistics = MongoDBEnvisionaryUsers.retrieveUserDescriptiveStatistics("TestUser");
        // HAVE TO ADD NULL CHECKS
        if (testUserDescriptiveStatistics != null) {
            testUserDescriptiveStatistics.printUserDescriptiveStatistics();
        }
        else {
            System.out.println("No descriptive statistics available for user.");
        }
        System.out.println();

        // MongoDBEnvisionaryUsers.retrieveUserInferentialStatistics(userId);
        // Returns user's UserInferentialStatistics Object
        System.out.println("Fetching user inferential statistics from MongoDB...");
        UserInferentialStatistics testUserInferentialStatistics = MongoDBEnvisionaryUsers.retrieveUserInferentialStatistics("TestUser");
        // HAVE TO ADD NULL CHECKS
        if (testUserInferentialStatistics != null) {
            testUserInferentialStatistics.printUserInferentialStatistics();
        }
        else {
            System.out.println("No inferential statistics available for user.");
        }
        System.out.println();
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // loginLogic
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    public static void loginLogic(String userIdentifier, String userEmail) {
        // if userId exists within MongoDB EnvisionaryUsers, set the userId
        if (MongoDBEnvisionaryUsers.userIdExists(userIdentifier)) {
            // TODO: Set the userId within the frontend/backend for the application to interact with the methods
            String userId = userIdentifier;
        }
        // else create a new EnvisionaryUser using the userId
        else {
            MongoDBEnvisionaryUsers.insertIndividualEnvisionaryUser(userIdentifier, userEmail);
            // TODO: Set the userId within the frontend/backend for the application to interact with the methods
            String userId = userIdentifier;
        }
    }
}
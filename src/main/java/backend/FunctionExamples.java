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
//TODO        CustomPredictionInitializer.createNewCustomPredictionMongoDB("bLapointe");
//
//TODO        CustomPredictionInitializer.removeCustomPredictionMongoDB("bLapointe");
//                  Might have to pull data to frontend first and have user choice be an index value or something to set equality to in order to remove on backend?
//                  Something like:
//                      - ArrayList<CustomPrediction> userCustomPredictions = MongoDBEnvisionaryUsers.retrieveUserCustomPredictions(userId);
//                      - IF userCustomPredictions != null
//                          - Display these in some sort of selectable list and use removeCustomPredictionMongoDB(index, userId) to remove.
//                          (I already wrote the method - If you can do this I will write the rest of them like this)
//
//TODO        CustomPredictionInitializer.resolveCustomPredictionMongoDB("bLapointe");
//                  Same as above
//
//TODO        FootballMatchPredictionInitializer.createNewFootballMatchUpcomingWeek1PredictionMongoDB("bLapointe");
//
//TODO        FootballMatchPredictionInitializer.removeFootballMatchPredictionMongoDB("bLapointe");
//                  Same as above
//
//TODO        CelestialBodyPredictionInitializer.createNewCelestialBodyPredictionMongoDB("bLapointe");
//
//TODO        CelestialBodyPredictionInitializer.removeCelestialBodyPredictionMongoDB("bLapointe");
//                  Same as above
//
//TODO        WeatherPredictionInitializer.createNewWeatherPredictionMongoDB("bLapointe");
//
//TODO        WeatherPredictionIntializer.removeWeatherPredictionMongoDB("bLapointe");
//                  Same as above
//
//         Create method for Entertainment Prediction
//
//         Remove method for Entertainment Prediction
//
//TODO        NotificationUpdater.removeUserNotificationMongoDB("bLapointe");
//                  Same as above
//
//TODO        NotificationUpdater.removeAllUserNotificationsMongoDB("bLapointe");
//                  Just need some sort of confirmation before removal in case it was an accidental press

        //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
        // ALL METHODS USED TO RETURN DATA FROM MONGO DB THAT FETCH DATA FOR VIEWING
        //

        // ArrayList<CustomPrediction> userCustomPredictions = MongoDBEnvisionaryUsers.retrieveUserCustomPredictions(userId);
        // Returns user's ArrayList<CustomPrediction>
        ArrayList<CustomPrediction> testCustomPredictions = MongoDBEnvisionaryUsers.retrieveUserCustomPredictions("bLapointe");
        // HAVE TO ADD NULL CHECKS
        if (testCustomPredictions != null) {
            for (CustomPrediction testCustomPrediction : testCustomPredictions) {
                testCustomPrediction.getPrediction().printPredictionDetails();
            }
        }
        System.out.println();

        // ArrayList<CelestialBodyPrediction> userCelestialBodyPredictions = MongoDBEnvisionaryUsers.retrieveUserCelestialBodyPredictions(userId);
        // Returns user's ArrayList<CelestialBodyPrediction>
        ArrayList<CelestialBodyPrediction> testCelestialBodyPredictions = MongoDBEnvisionaryUsers.retrieveUserCelestialBodyPredictions("bLapointe");
        // HAVE TO ADD NULL CHECKS
        if (testCelestialBodyPredictions != null) {
            for (CelestialBodyPrediction testCelestialBodyPrediction : testCelestialBodyPredictions) {
                testCelestialBodyPrediction.getPrediction().printPredictionDetails();
            }
        }
        System.out.println();

        // ArrayList<FootballMatchPrediction> userFootballMatchPredictions = MongoDBEnvisionaryUsers.retrieveUserFootballMatchPredictions(userId);
        // Returns user's ArrayList<FootballMatchPrediction>
        ArrayList<FootballMatchPrediction> testFootballMatchPredictions = MongoDBEnvisionaryUsers.retrieveUserFootballMatchPredictions("bLapointe");
        // HAVE TO ADD NULL CHECKS
        if (testFootballMatchPredictions != null) {
            for (FootballMatchPrediction testFootballMatchPrediction : testFootballMatchPredictions) {
                testFootballMatchPrediction.getPrediction().printPredictionDetails();
            }
        }
        System.out.println();

        // ArrayList<WeatherPrediction> userWeatherPredictions = MongoDBEnvisionaryUsers.retrieveUserWeatherPredictions(userId);
        // Returns user's ArrayList<WeatherPrediction>
        ArrayList<WeatherPrediction> testWeatherPredictions = MongoDBEnvisionaryUsers.retrieveUserWeatherPredictions("bLapointe");
        // HAVE TO ADD NULL CHECKS
        if (testWeatherPredictions != null) {
            for (WeatherPrediction testWeatherPrediction : testWeatherPredictions) {
                testWeatherPrediction.getPrediction().printPredictionDetails();
            }
        }
        System.out.println();

        // ArrayList<ResolvedPrediction> userResolvedPredictions = MongoDBEnvisionaryUsers.retrieveUserResolvedPredictions(userId);
        // Returns user's ArrayList<ResolvedPrediction>
        ArrayList<ResolvedPrediction> testResolvedPredictions = MongoDBEnvisionaryUsers.retrieveUserResolvedPredictions("bLapointe");
        // HAVE TO ADD NULL CHECKS
        if (testResolvedPredictions != null) {
            for (ResolvedPrediction testResolvedPrediction : testResolvedPredictions) {
                testResolvedPrediction.printPredictionDetails();
            }
        }
        System.out.println();

        // MongoDBEnvisionaryUsers.retrieveUserNotifications(userId);
        // Returns user's ArrayList<Notification>
        ArrayList<Notification> testNotifications = MongoDBEnvisionaryUsers.retrieveUserNotifications("bLapointe");
        // HAVE TO ADD NULL CHECKS
        if (testNotifications != null) {
            for (Notification testNotification : testNotifications) {
                testNotification.printNotification();
            }
        }
        System.out.println();

        // MongoDBEnvisionaryUsers.retrieveUserDescriptiveStatistics(userId);
        // Returns user's UserDescriptiveStatistics Object
        UserDescriptiveStatistics testUserDescriptiveStatistics = MongoDBEnvisionaryUsers.retrieveUserDescriptiveStatistics("bLapointe");
        // HAVE TO ADD NULL CHECKS
        if (testUserDescriptiveStatistics != null) {
            testUserDescriptiveStatistics.printUserDescriptiveStatistics();
        }
        System.out.println();

        // MongoDBEnvisionaryUsers.retrieveUserInferentialStatistics(userId);
        // Returns user's UserInferentialStatistics Object
        UserInferentialStatistics testUserInferentialStatistics = MongoDBEnvisionaryUsers.retrieveUserInferentialStatistics("bLapointe");
        // HAVE TO ADD NULL CHECKS
        if (testUserInferentialStatistics != null) {
            testUserInferentialStatistics.printUserInferentialStatistics();
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
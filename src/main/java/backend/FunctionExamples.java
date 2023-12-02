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

import java.util.ArrayList;


//■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
// FunctionExamples class - Written by Brandon LaPointe
//■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
// Provides functionality examples of all the classes needed for Frontend/Backend interaction.
//
public class FunctionExamples {
    public static void main(String[] args) {
        //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
        // METHODS USED TO RETURN DATA FROM MONGO DB THAT WILL HAVE TO BE MODIFIED FOR UI INPUT
        //
//        CustomPredictionInitializer.createNewCustomPredictionMongoDB("bLapointe");
//
//        CustomPredictionInitializer.removeCustomPredictionMongoDB("bLapointe");
//
//        CustomPredictionInitializer.resolveCustomPredictionMongoDB("bLapointe");

        // For testing
        //FootballMatchPredictionInitializer.createNewFootballMatchYesterdayPredictionMongoDB("bLapointe");

        // For testing
        //FootballMatchPredictionInitializer.createNewFootballMatchTodayPredictionMongoDB("bLapointe");

//        FootballMatchPredictionInitializer.createNewFootballMatchTomorrowPredictionMongoDB("bLapointe");
//
//        FootballMatchPredictionInitializer.createNewFootballMatchUpcomingWeek1PredictionMongoDB("bLapointe");
//
//        FootballMatchPredictionInitializer.createNewFootballMatchUpcomingWeek2PredictionMongoDB("bLapointe");
//
//        FootballMatchPredictionInitializer.createNewFootballMatchUpcomingWeek3PredictionMongoDB("bLapointe");
//
//        FootballMatchPredictionInitializer.removeFootballMatchPredictionMongoDB("bLapointe");
//
//        CelestialBodyPredictionInitializer.createNewCelestialBodyPredictionMongoDB("bLapointe");
//
//        CelestialBodyPredictionInitializer.removeCelestialBodyPredictionMongoDB("bLapointe");

        // Create method for Entertainment Prediction

        // Remove method for Entertainment Prediction

        // Create method for Weather Prediction

        // Remove method for Weather Prediction

//        NotificationUpdater.removeUserNotificationMongoDB("bLapointe");
//
//        NotificationUpdater.removeAllUserNotificationsMongoDB("bLapointe");

        //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
        // ALL METHODS USED TO RETURN DATA FROM MONGO DB THAT FETCH DATA FOR VIEWING
        //
        // Returns ArrayList<CustomPrediction>
        ArrayList<CustomPrediction> testCustomPredictions = MongoDBEnvisionaryUsers.retrieveUserCustomPredictions("bLapointe");
        // TODO: HAVE TO ADD NULL CHECKS
        if (testCustomPredictions != null) {
            for (CustomPrediction testCustomPrediction : testCustomPredictions) {
                testCustomPrediction.getPrediction().printPredictionDetails();
            }
        }

        // Returns ArrayList<CelestialBodyPrediction>
        ArrayList<CelestialBodyPrediction> testCelestialBodyPredictions = MongoDBEnvisionaryUsers.retrieveUserCelestialBodyPredictions("bLapointe");
        // TODO: HAVE TO ADD NULL CHECKS
        if (testCelestialBodyPredictions != null) {
            for (CelestialBodyPrediction testCelestialBodyPrediction : testCelestialBodyPredictions) {
                testCelestialBodyPrediction.getPrediction().printPredictionDetails();
            }
        }
        System.out.println();

        // Returns ArrayList<FootballMatchPrediction>
        ArrayList<FootballMatchPrediction> testFootballMatchPredictions = MongoDBEnvisionaryUsers.retrieveUserFootballMatchPredictions("bLapointe");
        // TODO: HAVE TO ADD NULL CHECKS
        if (testFootballMatchPredictions != null) {
            for (FootballMatchPrediction testFootballMatchPrediction : testFootballMatchPredictions) {
                testFootballMatchPrediction.getPrediction().printPredictionDetails();
            }
        }
        System.out.println();

        // Returns ArrayList<WeatherPrediction>
        ArrayList<WeatherPrediction> testWeatherPredictions = MongoDBEnvisionaryUsers.retrieveUserWeatherPredictions("bLapointe");
        // TODO: HAVE TO ADD NULL CHECKS
        if (testWeatherPredictions != null) {
            for (WeatherPrediction testWeatherPrediction : testWeatherPredictions) {
                testWeatherPrediction.getPrediction().printPredictionDetails();
            }
        }
        System.out.println();

        // Returns ArrayList<ResolvedPrediction>
        ArrayList<ResolvedPrediction> testResolvedPredictions = MongoDBEnvisionaryUsers.retrieveUserResolvedPredictions("bLapointe");
        // TODO: HAVE TO ADD NULL CHECKS
        if (testResolvedPredictions != null) {
            for (ResolvedPrediction testResolvedPrediction : testResolvedPredictions) {
                testResolvedPrediction.printPredictionDetails();
            }
        }
        System.out.println();

        // Returns ArrayList<Notification>
        ArrayList<Notification> testNotifications = MongoDBEnvisionaryUsers.retrieveUserNotifications("bLapointe");
        // TODO: HAVE TO ADD NULL CHECKS
        if (testNotifications != null) {
            for (Notification testNotification : testNotifications) {
                testNotification.printNotification();
            }
        }
        System.out.println();

        // Returns UserDescriptiveStatistics Object
        UserDescriptiveStatistics testUserDescriptiveStatistics = MongoDBEnvisionaryUsers.retrieveUserDescriptiveStatistics("bLapointe");
        // TODO: HAVE TO ADD NULL CHECKS
        if (testUserDescriptiveStatistics != null) {
            testUserDescriptiveStatistics.printUserDescriptiveStatistics();
        }
        System.out.println();

        // Returns UserInferentialStatistics Object
        UserInferentialStatistics testUserInferentialStatistics = MongoDBEnvisionaryUsers.retrieveUserInferentialStatistics("bLapointe");
        // TODO: HAVE TO ADD NULL CHECKS
        if (testUserInferentialStatistics != null) {
            testUserInferentialStatistics.printUserInferentialStatistics();
        }
        System.out.println();
    }
}
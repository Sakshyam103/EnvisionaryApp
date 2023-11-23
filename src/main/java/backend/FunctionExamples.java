package backend;

import backend.CustomPredictions.CustomPredictionInitializer;
import backend.FootballMatchPredictions.FootballMatchPredictionInitializer;
import backend.FootballMatchPredictions.FootballMatchUpdater;
import backend.FootballMatchPredictions.FootballTeamInitializer;
import backend.Notifications.NotificationUpdater;
import backend.OverallStatistics.OverallDescriptiveStatisticsUpdater;
import backend.OverallStatistics.OverallInferentialStatisticsUpdater;
import backend.ResolvedPredictions.ResolvedPredictionInitializer;
import backend.UserStatistics.UserDescriptiveStatisticsUpdater;
import backend.UserStatistics.UserInferentialStatisticsUpdater;

//■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
// FunctionExamples class - Written by Brandon LaPointe
//■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
// Provides functionality examples of all the classes.
//
public class FunctionExamples {

    public static void initializeFilePaths() {
        FootballTeamInitializer.initializeFilePath();
        FootballMatchUpdater.initializeFilePath();
        CustomPredictionInitializer.initializeFilePath();
        FootballMatchPredictionInitializer.initializeFilePath();
        ResolvedPredictionInitializer.initializeFilePath();
        UserDescriptiveStatisticsUpdater.initializeFilePath();
        UserInferentialStatisticsUpdater.initializeFilePath();
        OverallDescriptiveStatisticsUpdater.initializeFilePath();
        OverallInferentialStatisticsUpdater.initializeFilePath();
        NotificationUpdater.initializeFilePath();
    }

    public static void main(String[] args) {
        // Initialize all file paths
        initializeFilePaths();

        //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
        // Football Team Updater Function Examples
        //

        FootballTeamInitializer.initializeFilePath();

        FootballTeamInitializer.updateTeams();

        FootballTeamInitializer.readFileAndPrintTeams();


        //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
        // Custom BasePredictionsObject.Prediction Initializer Function Examples
        //

		CustomPredictionInitializer.initializeFilePath();

        CustomPredictionInitializer.createNewCustomPrediction("bLapointe");

        CustomPredictionInitializer.printAllCustomPredictions("bLapointe");

        CustomPredictionInitializer.removeCustomPrediction("bLapointe");

        CustomPredictionInitializer.resolveCustomPrediction("bLapointe");

        CustomPredictionInitializer.createNewCustomPredictionMongoDB("bLapointe");

        CustomPredictionInitializer.removeCustomPredictionMongoDB("bLapointe");

        CustomPredictionInitializer.resolveCustomPredictionMongoDB("bLapointe");

        //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
        // Football Match BasePredictionsObject.Prediction Initializer Function Examples
        //

		FootballMatchPredictionInitializer.initializeFilePath();

        FootballMatchPredictionInitializer.createNewFootballMatchYesterdayPrediction("bLapointe");

        FootballMatchPredictionInitializer.createNewFootballMatchTodayPrediction("bLapointe");

        FootballMatchPredictionInitializer.createNewFootballMatchTomorrowPrediction("bLapointe");

        FootballMatchPredictionInitializer.createNewFootballMatchUpcomingWeek1Prediction("bLapointe");

        FootballMatchPredictionInitializer.createNewFootballMatchUpcomingWeek2Prediction("bLapointe");

        FootballMatchPredictionInitializer.createNewFootballMatchUpcomingWeek3Prediction("bLapointe");

        FootballMatchPredictionInitializer.printAllFootballMatchPredictions("bLapointe");

        FootballMatchPredictionInitializer.removeFootballMatchPrediction("bLapointe");

        FootballMatchPredictionInitializer.createNewFootballMatchYesterdayPredictionMongoDB("bLapointe");

        FootballMatchPredictionInitializer.createNewFootballMatchTodayPredictionMongoDB("bLapointe");

        FootballMatchPredictionInitializer.createNewFootballMatchTomorrowPredictionMongoDB("bLapointe");

        FootballMatchPredictionInitializer.createNewFootballMatchUpcomingWeek1PredictionMongoDB("bLapointe");

        FootballMatchPredictionInitializer.createNewFootballMatchUpcomingWeek2PredictionMongoDB("bLapointe");

        FootballMatchPredictionInitializer.createNewFootballMatchUpcomingWeek3PredictionMongoDB("bLapointe");

        //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
        // Resolved BasePredictionsObject.Prediction Initializer Function Examples
        //

    	ResolvedPredictionInitializer.initializeFilePath();

        ResolvedPredictionInitializer.printResolvedCustomPredictions("bLapointe");

        ResolvedPredictionInitializer.printResolvedFootballMatchPredictions("bLapointe");

        ResolvedPredictionInitializer.printAllResolvedPredictions("bLapointe");

        ResolvedPredictionInitializer.printResolvedCustomPredictionsMongoDB("bLapointe");

        ResolvedPredictionInitializer.printResolvedSciencePredictions("bLapointe");

        ResolvedPredictionInitializer.printResolvedFootballMatchPredictions("bLapointe");

        //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
        // UserInfo.User Descriptive Statistics Updater Function Examples
        //

        UserDescriptiveStatisticsUpdater.initializeFilePath();

        // Calculate user statistics
        UserDescriptiveStatisticsUpdater.calculateAndSaveUserDescriptiveStatistics("bLapointe");

        // Load and display user statistics
        UserDescriptiveStatisticsUpdater.loadAndDisplayUserDescriptiveStatistics("bLapointe");

        UserDescriptiveStatisticsUpdater.calculateAndSaveUserDescriptiveStatisticsMongoDB("bLapointe");

        //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
        // Overall Descriptive Statistics Updater Function Examples
        //

        // Initialize folder / file path
        OverallDescriptiveStatisticsUpdater.initializeFilePath();

        // Calculate and save overall statistics
        OverallDescriptiveStatisticsUpdater.calculateAndSaveOverallDescriptiveStatistics();

        // Load and display overall statistics
        OverallDescriptiveStatisticsUpdater.loadAndDisplayOverallDescriptiveStatistics();

        OverallInferentialStatisticsUpdater.calculateAndSaveOverallInferentialStatisticsMongoDB();

        //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
        // Notifications.Notification Updater Function Examples
        //

        NotificationUpdater.initializeFilePath();

        // Done within the footballMatchPredictionUpdater automatically when a football match
        // prediction is cancelled because of a match status of CANCELLED, POSTPONED, or SUSPENDED
        //
        //Notifications.NotificationUpdater.newFootballMatchPredictionCancelledNotification(FootballMatchPredictions.FootballMatchPrediction matchPrediction, String userIdentifier);

        // Done within the footballMatchPredictionUpdater automatically when a football match
        // prediction is resolved successfully with a match status of FINISHED
        //
        //Notifications.NotificationUpdater.newFootballMatchPredictionResolvedNotification(FootballMatchPredictions.FootballMatchPrediction matchPrediction, String userIdentifier, Boolean outcome)

        NotificationUpdater.loadAndDisplayUserNotifications("bLapointe");

        NotificationUpdater.removeUserNotification("bLapointe");

        NotificationUpdater.removeAllUserNotifications("bLapointe");

        NotificationUpdater.removeUserNotificationMongoDB("bLapointe");

        NotificationUpdater.removeAllUserNotificationsMongoDB("bLapointe");

        //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
        // UserInfo.User Inferential Statistics Updater Function Examples
        //

        UserInferentialStatisticsUpdater.initializeFilePath();

        UserInferentialStatisticsUpdater.calculateAndSaveUserInferentialStatistics("bLapointe");

        UserInferentialStatisticsUpdater.loadAndDisplayUserInferentialStatistics("bLapointe");

        UserInferentialStatisticsUpdater.calculateAndSaveUserInferentialStatisticsMongoDB("bLapointe");

        //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
        // Overall Inferential Statistics Updater Function Examples
        //

        OverallInferentialStatisticsUpdater.initializeFilePath();

        OverallInferentialStatisticsUpdater.calculateAndSaveOverallInferentialStatistics();

        OverallInferentialStatisticsUpdater.loadAndDisplayOverallInferentialStatistics();

        OverallInferentialStatisticsUpdater.calculateAndSaveOverallInferentialStatisticsMongoDB();
    }
}
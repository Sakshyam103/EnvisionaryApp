package backend;

import backend.CelestialBodyPredictions.CelestialBodyPredictionInitializer;
import backend.CelestialBodyPredictions.CelestialBodyPredictionUpdater;
import backend.CelestialBodyPredictions.CelestialBodyUpdater;
import backend.CustomPredictions.CustomPredictionInitializer;
import backend.FootballMatchPredictions.FootballMatchPredictionInitializer;
import backend.FootballMatchPredictions.FootballMatchPredictionUpdater;
import backend.FootballMatchPredictions.FootballMatchUpdater;
import backend.FootballMatchPredictions.FootballTeamInitializer;
import backend.Notifications.NotificationUpdater;
import backend.OverallStatistics.OverallDescriptiveStatisticsUpdater;
import backend.OverallStatistics.OverallInferentialStatisticsUpdater;
import backend.ResolvedPredictions.ResolvedPredictionInitializer;
import backend.UserInfo.UserInitializer;
import backend.UserStatistics.UserDescriptiveStatisticsUpdater;
import backend.UserStatistics.UserInferentialStatisticsUpdater;
import backend.WeatherPredictions.WeatherPredictionUpdater;
import backend.WeatherPredictions.WeatherUpdater;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

//■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
// BackgroundUpdaters class - Written by Brandon LaPointe
//■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
// Gets football match data from the football-data.org API and updates the match files needed to make
// new football match predictions and automatically resolve football match predictions already made.
//
public class BackgroundUpdaters {
    static final int X = 10;                                 // Minutes between football match file updates

    public static void LocalBackgroundUpdater() {
        // Initialize the file paths
        FootballMatchUpdater.initializeFilePath();
        FootballTeamInitializer.initializeFilePath();
        CustomPredictionInitializer.initializeFilePath();
        FootballMatchPredictionInitializer.initializeFilePath();
        ResolvedPredictionInitializer.initializeFilePath();
        UserDescriptiveStatisticsUpdater.initializeFilePath();
        UserInferentialStatisticsUpdater.initializeFilePath();
        OverallDescriptiveStatisticsUpdater.initializeFilePath();
        OverallInferentialStatisticsUpdater.initializeFilePath();
        NotificationUpdater.initializeFilePath();
        UserInitializer.initializeFilePath();
        CelestialBodyUpdater.initializeFilePath();
        CelestialBodyPredictionInitializer.initializeFilePath();

        // Update the Football Teams file once upon launch
        System.out.print("Updating FootballTeam files...");
        FootballTeamInitializer.updateTeams();
        System.out.println("FootballTeam files have been updated at: " + ZonedDateTime.now());

        // Create a ScheduledExecutorService with a single thread
        ScheduledExecutorService hourlyExecutor = Executors.newSingleThreadScheduledExecutor();
        ScheduledExecutorService xMinutesExecutor = Executors.newSingleThreadScheduledExecutor();

        // Define the task to be executed every X minutes
        Runnable updateEveryXMinutes = () -> {
            boolean updateToday = false;

            // Display update on console
            System.out.print("Updating FootballMatch files...");

            // Call the external method to update the files
            FootballMatchUpdater.updateYesterdayMatchesLocally();
            updateToday = FootballMatchUpdater.updateTodayMatchesLocally();
            FootballMatchUpdater.updateTomorrowMatchesLocally();

            // Display on server that a file update occurred
            //System.out.println("Yesterday's, today's, & tomorrow's football match files updated at: " + ZonedDateTime.now());

            // Call the methods to update the matches
            FootballMatchUpdater.updateUpcomingWeek1MatchesLocally();
            FootballMatchUpdater.updateUpcomingWeek2MatchesLocally();
            FootballMatchUpdater.updateUpcomingWeek3MatchesLocally();

            // Display that all the files have updated for testing
            System.out.println("FootballMatch files have been updated at: " + ZonedDateTime.now());

            // If there was an update to today's matches, call FootballMatchPredictions.FootballMatchPredictionUpdater
            if (updateToday) {
                // Display update on console
                System.out.print("Updating FootballMatchPrediction files...");

                // Call the FootballMatchPredictions.FootballMatchPredictionUpdater to update all users' match predictions based on the newly updated match files
                FootballMatchPredictionUpdater.updateFootballMatchPredictionsLocally();

                // Display that all the files have updated for testing
                System.out.println("FootballMatchPrediction files have been updated at: " + ZonedDateTime.now());
            } else {
                System.out.println("No updates to today's match file. Skipping FootballMatchPrediction file updates.");
            }

            // Call the CelestialBodyPredictions.CelestialBodyUpdater to update the saved list of CelestialBodyPredictions.CelestialBody objects
            CelestialBodyUpdater.updateCelestialBodiesFile();

            // Call the CelestialBodyPredictions.CelestialBodyPredictionUpdater to update all users' celestial body predictions
            CelestialBodyPredictionUpdater.updateCelestialBodyPredictions();
        };

        // Schedule the task to run every X minutes with an initial delay of 0
        xMinutesExecutor.scheduleAtFixedRate(updateEveryXMinutes, 0, X, TimeUnit.MINUTES);
    }

    public static void MongoDBBackgroundUpdater() {
        // Create a ScheduledExecutorService with a single thread
        ScheduledExecutorService xMinutesExecutor = Executors.newSingleThreadScheduledExecutor();
        ScheduledExecutorService hourlyExecutor = Executors.newSingleThreadScheduledExecutor();
        ScheduledExecutorService dailyExecutor = Executors.newSingleThreadScheduledExecutor();

        // Define the task to be executed every X minutes
        Runnable updateEveryXMinutes = () -> {
            boolean updateTodayFootballMatches = false;

            // Display update on console
            System.out.print("Updating FootballMatch files...");

            // Call the external method to update the files
            FootballMatchUpdater.updateYesterdayMatchesMongoDB();
            updateTodayFootballMatches = FootballMatchUpdater.updateTodayMatchesMongoDB();
            FootballMatchUpdater.updateTomorrowMatchesMongoDB();

            // Display on server that a file update occurred
            //System.out.println("Yesterday's, today's, & tomorrow's football match files updated at: " + ZonedDateTime.now());

            // Call the methods to update the matches
            FootballMatchUpdater.updateUpcomingWeek1MatchesMongoDB();
            FootballMatchUpdater.updateUpcomingWeek2MatchesMongoDB();
            FootballMatchUpdater.updateUpcomingWeek3MatchesMongoDB();

            // Display that all the files have updated for testing
            System.out.println("FootballMatch files have been updated at: " + ZonedDateTime.now());

            // If there was an update to today's matches, call FootballMatchPredictions.FootballMatchPredictionUpdater
            if (updateTodayFootballMatches) {
                // Display update on console
                System.out.print("Updating FootballMatchPrediction files...");

                // Call the FootballMatchPredictions.FootballMatchPredictionUpdater to update all users' match predictions based on the newly updated match files
                FootballMatchPredictionUpdater.updateFootballMatchPredictionsMongoDB();

                // Display that all the files have updated for testing
                System.out.println("FootballMatchPrediction files have been updated at: " + ZonedDateTime.now());
            } else {
                System.out.println("No updates to today's match file. Skipping FootballMatchPrediction file updates.");
            }
        };

        // Define the task to be executed every hour
        Runnable updateHourly = () -> {
            // TODO : Fill with updater methods that need running every hour

        };

        // Define the task to be executed every day
        Runnable updateDaily = () -> {
            // Call the CelestialBodyUpdater to update the saved list of CelestialBody objects
            CelestialBodyUpdater.updateCelestialBodiesMongoDB();

            // Call the CelestialBodyPredictionUpdater to update all users' celestial body predictions
            CelestialBodyPredictionUpdater.updateCelestialBodyPredictionsMongoDB();

            // Call the WeatherUpdater to update today's DailyForecast data
            try {
                WeatherUpdater.updateWeatherMongoDB();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            // Call the WeatherPredictionUpdater to update all users' weather predictions
            WeatherPredictionUpdater.updateWeatherPredictionsMongoDB();
        };

        // Schedule the task to run every X minutes with an initial delay of 0
        xMinutesExecutor.scheduleAtFixedRate(updateEveryXMinutes, 0, X, TimeUnit.MINUTES);
        // Schedule the task to run every hour with an initial delay of 0
        //hourlyExecutor.scheduleAtFixedRate(updateHourly, 0, 1, TimeUnit.HOURS);
        // Schedule the task to run every day with an initial delay of 0
        dailyExecutor.scheduleAtFixedRate(updateDaily, 1, 3600, TimeUnit.MINUTES);
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // main
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Updates these files once upon launch:
    //      football_teams.json
    //
    // Updates these files from API calls every X minutes:
    //      football_matches_yesterday.json
    //		football_matches_today.json
    //		football_matches_tomorrow.json
    //      football_matches_upcoming_week.json
    //		football_matches_upcoming_week2.json
    //		football_matches_upcoming_week3.json
    //
    // Resolves predictions of all users every X minutes:
    //      (All Users)_football_match_predictions.json
    //      If (USER_football_match_predictions.json is updated), automatically updates:
    //          USER_user_statistics.json
    //          overall_statistics.json
    //

    public static void main (String[] args) {
        //LocalBackgroundUpdater();
        MongoDBBackgroundUpdater();
    }
}
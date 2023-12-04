package backend.FootballMatchPredictions;

import backend.UserInfo.MongoDBEnvisionaryUsers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

//■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
// FootballMatchPredictionInitializer class - Written by Brandon LaPointe
//■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
// Initializes the file path and folders needed to save the users' football match predictions. Initializes
// football match predictions made by the user, removes football match predictions, and displays football
// match predictions using the String userIdentifier parameter to identify the correct file.
//
public class FootballMatchPredictionInitializer {
    // FootballMatchPredictionUpdater Class Constants
    private static final String userHome = System.getProperty("user.home");
    private static final String folderPath = userHome + File.separator + "EnvisionaryApp" + File.separator + "SportsPredictions" + File.separator + "FootballPredictions" + File.separator + "FootballMatchPredictions";

    // FootballMatchPrediction Object
    private static FootballMatchPrediction userMatchPrediction;

    // Scanner object for reading user input from keyboard
    public static Scanner scan = new Scanner(System.in);

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // initializeFilePath
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Initializes the file path and folders needed to save the football match files.
    //
    public static void initializeFilePath() {
        // Create the directory if it doesn't exist
        File directory = new File(folderPath);
        if (!directory.exists()) {
            if (directory.mkdirs()) {
                System.out.println("Created directory: " + folderPath);
            } else {
                System.err.println("ERROR - Failed to create directory: " + folderPath);
            }
        }
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // getUserChoiceMatchYesterday
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Displays a list of the football matches from the file football_matches_yesterday.json for the user to
    // then select from before setting the FootballMatchPrediction userMatchPrediction's FootballMatch
    // data to the selected match.
    //
    public static void getUserChoiceMatchYesterday() {
        userMatchPrediction = new FootballMatchPrediction();

        // Display list of tomorrow's matches for user to select from
        ArrayList<FootballMatch> yesterdaysMatches = FootballMatchUpdater.readAndReturnYesterdayMatchesFile();
        // Gather user selection
        if (!yesterdaysMatches.isEmpty()) {
            for (FootballMatch match : yesterdaysMatches) {
                System.out.println(yesterdaysMatches.indexOf(match)+1 + " : " + match.getHomeTeam() + " vs. " + match.getAwayTeam());
            }
            // Initialize selectedMatchIndex to 0
            int selectedMatchIndex = 0;

            // Initialize valid boolean flag to true
            boolean valid = true;

            // Get user input of match selection
            do {
                System.out.println("Select a match number from the list of yesterday's matches:");
                try {
                    selectedMatchIndex = scan.nextInt();
                } catch (Exception e) {
                    e.printStackTrace();
                    valid = false;
                }
            } while (!valid && selectedMatchIndex == 0 || selectedMatchIndex-1 < 0 || selectedMatchIndex > yesterdaysMatches.size());

            // Set predictionMatch to the selected match
            userMatchPrediction.setPredictionMatch(yesterdaysMatches.get(selectedMatchIndex-1));
        } else {
            //System.out.println("The list of matches for tomorrow is currently empty.");
            userMatchPrediction.setPredictionMatch(null);
        }
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // getUserChoiceMatchToday
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Displays a list of the football matches from the file football_matches_today.json for the user to
    // then select from before setting the FootballMatchPrediction userMatchPrediction's FootballMatch
    // data to the selected match.
    //
    public static void getUserChoiceMatchToday() {
        userMatchPrediction = new FootballMatchPrediction();

        // Display list of tomorrow's matches for user to select from
        ArrayList<FootballMatch> todayMatches = FootballMatchUpdater.readAndReturnTodayMatchesFile();
        // Gather user selection
        if (!todayMatches.isEmpty()) {
            for (FootballMatch match : todayMatches) {
                System.out.println(todayMatches.indexOf(match)+1 + " : " + match.getHomeTeam() + " vs. " + match.getAwayTeam());
            }
            // Initialize selectedMatchIndex to 0
            int selectedMatchIndex = 0;

            // Initialize valid boolean flag to true
            boolean valid = true;

            // Get user input of match selection
            do {
                System.out.println("Select a match number from the list of today's matches:");
                try {
                    selectedMatchIndex = scan.nextInt();
                } catch (Exception e) {
                    e.printStackTrace();
                    valid = false;
                }
            } while (!valid && selectedMatchIndex == 0 || selectedMatchIndex-1 < 0 || selectedMatchIndex > todayMatches.size());

            // Set predictionMatch to the selected match
            userMatchPrediction.setPredictionMatch(todayMatches.get(selectedMatchIndex-1));
        } else {
            //System.out.println("The list of matches for tomorrow is currently empty.");
            userMatchPrediction.setPredictionMatch(null);
        }
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // getUserChoiceMatchTomorrow
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Displays a list of the football matches from the file football_matches_tomorrow.json for the user to
    // then select from before setting the FootballMatchPrediction userMatchPrediction's FootballMatch
    // data to the selected match.
    //
    public static void getUserChoiceMatchTomorrow() {
        userMatchPrediction = new FootballMatchPrediction();

        // Display list of tomorrow's matches for user to select from
        ArrayList<FootballMatch> tomorrowMatches = FootballMatchUpdater.readAndReturnTomorrowMatchesFile();
        // Gather user selection
        if (!tomorrowMatches.isEmpty()) {
            for (FootballMatch match : tomorrowMatches) {
                System.out.println(tomorrowMatches.indexOf(match)+1 + " : " + match.getHomeTeam() + " vs. " + match.getAwayTeam());
            }
            // Initialize selectedMatchIndex to 0
            int selectedMatchIndex = 0;

            // Initialize valid boolean flag to true
            boolean valid = true;

            // Get user input of match selection
            do {
                System.out.println("Select a match number from the list of tomorrow's matches:");
                try {
                    selectedMatchIndex = scan.nextInt();
                } catch (Exception e) {
                    e.printStackTrace();
                    valid = false;
                }
            } while (!valid && selectedMatchIndex == 0 || selectedMatchIndex-1 < 0 || selectedMatchIndex > tomorrowMatches.size());

            // Set predictionMatch to the selected match
            userMatchPrediction.setPredictionMatch(tomorrowMatches.get(selectedMatchIndex-1));
        } else {
            //System.out.println("The list of matches for tomorrow is currently empty.");
            userMatchPrediction.setPredictionMatch(null);
        }
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // getUserChoiceMatchUpcomingWeek1
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Displays a list of the football matches from the file football_matches_upcoming_week1.json for the
    // user to then select from before setting the FootballMatchPrediction userMatchPrediction's
    // FootballMatch data to the selected match.
    //
    public static void getUserChoiceMatchUpcomingWeek1() {
        userMatchPrediction = new FootballMatchPrediction();

        // Display list of tomorrow's matches for user to select from
        ArrayList<FootballMatch> upcomingWeek1Matches = FootballMatchUpdater.readAndReturnUpcomingWeek1MatchesFile();
        // Gather user selection
        if (!upcomingWeek1Matches.isEmpty()) {
            for (FootballMatch match : upcomingWeek1Matches) {
                System.out.println(upcomingWeek1Matches.indexOf(match)+1 + " : " + match.getHomeTeam() + " vs. " + match.getAwayTeam());
            }
            // Initialize selectedMatchIndex to 0
            int selectedMatchIndex = 0;

            // Initialize valid boolean flag to true
            boolean valid = true;

            // Get user input of match selection
            do {
                System.out.println("Select a match number from the list of the upcoming week's matches:");
                try {
                    selectedMatchIndex = scan.nextInt();
                } catch (Exception e) {
                    e.printStackTrace();
                    valid = false;
                }
            } while (!valid && selectedMatchIndex == 0 || selectedMatchIndex-1 < 0 || selectedMatchIndex > upcomingWeek1Matches.size());

            // Set predictionMatch to the selected match
            userMatchPrediction.setPredictionMatch(upcomingWeek1Matches.get(selectedMatchIndex-1));
        } else {
            //System.out.println("The list of matches for tomorrow is currently empty.");
            userMatchPrediction.setPredictionMatch(null);
        }
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // getUserChoiceMatchUpcomingWeek2
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Displays a list of the football matches from the file football_matches_upcoming_week2.json for the
    // user to then select from before setting the FootballMatchPrediction userMatchPrediction's
    // FootballMatch data to the selected match.
    //
    public static void getUserChoiceMatchUpcomingWeek2() {
        userMatchPrediction = new FootballMatchPrediction();

        // Display list of tomorrow's matches for user to select from
        ArrayList<FootballMatch> upcomingWeek2Matches = FootballMatchUpdater.readAndReturnUpcomingWeek2MatchesFile();
        // Gather user selection
        if (!upcomingWeek2Matches.isEmpty()) {
            for (FootballMatch match : upcomingWeek2Matches) {
                System.out.println(upcomingWeek2Matches.indexOf(match)+1 + " : " + match.getHomeTeam() + " vs. " + match.getAwayTeam());
            }
            // Initialize selectedMatchIndex to 0
            int selectedMatchIndex = 0;

            // Initialize valid boolean flag to true
            boolean valid = true;

            // Get user input of match selection
            do {
                System.out.println("Select a match number from the list of the second upcoming week's matches:");
                try {
                    selectedMatchIndex = scan.nextInt();
                } catch (Exception e) {
                    e.printStackTrace();
                    valid = false;
                }
            } while (!valid && selectedMatchIndex == 0 || selectedMatchIndex-1 < 0 || selectedMatchIndex > upcomingWeek2Matches.size());

            // Set predictionMatch to the selected match
            userMatchPrediction.setPredictionMatch(upcomingWeek2Matches.get(selectedMatchIndex-1));
        } else {
            //System.out.println("The list of matches for tomorrow is currently empty.");
            userMatchPrediction.setPredictionMatch(null);
        }
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // getUserChoiceMatchUpcomingWeek3
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Displays a list of the football matches from the file football_matches_upcoming_week3.json for the
    // user to then select from before setting the FootballMatchPrediction userMatchPrediction's
    // FootballMatch data to the selected match.
    //
    public static void getUserChoiceMatchUpcomingWeek3() {
        userMatchPrediction = new FootballMatchPrediction();

        // Display list of tomorrow's matches for user to select from
        ArrayList<FootballMatch> upcomingWeek3Matches = FootballMatchUpdater.readAndReturnUpcomingWeek3MatchesFile();
        // Gather user selection
        if (!upcomingWeek3Matches.isEmpty()) {
            for (FootballMatch match : upcomingWeek3Matches) {
                System.out.println(upcomingWeek3Matches.indexOf(match)+1 + " : " + match.getHomeTeam() + " vs. " + match.getAwayTeam());
            }
            // Initialize selectedMatchIndex to 0
            int selectedMatchIndex = 0;

            // Initialize valid boolean flag to true
            boolean valid = true;

            // Get user input of match selection
            do {
                System.out.println("Select a match number from the list of the third upcoming week's matches:");
                try {
                    selectedMatchIndex = scan.nextInt();
                } catch (Exception e) {
                    e.printStackTrace();
                    valid = false;
                }
            } while (!valid && selectedMatchIndex == 0 || selectedMatchIndex-1 < 0 || selectedMatchIndex > upcomingWeek3Matches.size());

            // Set predictionMatch to the selected match
            userMatchPrediction.setPredictionMatch(upcomingWeek3Matches.get(selectedMatchIndex-1));
        } else {
            //System.out.println("The list of matches for tomorrow is currently empty.");
            userMatchPrediction.setPredictionMatch(null);
        }
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // getUserMatchPrediction
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Gathers the inputs from the user to set the prediction description of the userMatchPrediction.
    //
    public static void getUserMatchPrediction() {
        // Local variables
        int userTeamSelection = 0;
        int userOutcomeSelection = 0;

        // Have user select team to base the prediction on
        System.out.println("Select a team to make the prediction about:");
        System.out.println("1: " + userMatchPrediction.getPredictionMatch().getHomeTeam());
        System.out.println("2: " + userMatchPrediction.getPredictionMatch().getAwayTeam());

        while (userTeamSelection != 1 && userTeamSelection != 2) {
            if (scan.hasNextInt()) {
                userTeamSelection = scan.nextInt();
                if (userTeamSelection != 1 && userTeamSelection != 2) {
                    System.out.println("ERROR - Invalid input. Please select 1 or 2.");
                }
            } else {
                scan.next(); // Consume the invalid input
                System.out.println("ERROR - Invalid input. Please select 1 or 2.");
            }
        }

        System.out.println("I predict that the match will result in a...");
        System.out.println("1: Win");
        System.out.println("2: Loss");
        System.out.println("3: Draw");

        while (userOutcomeSelection != 1 && userOutcomeSelection != 2 && userOutcomeSelection != 3) {
            if (scan.hasNextInt()) {
                userOutcomeSelection = scan.nextInt();
                if (userOutcomeSelection != 1 && userOutcomeSelection != 2 && userOutcomeSelection != 3) {
                    System.out.println("ERROR - Invalid input. Please select 1, 2, or 3.");
                }
            } else {
                scan.next(); // Consume the invalid input
                System.out.println("ERROR - Invalid input. Please select 1, 2, or 3.");
            }
        }

        // Set Strings based on team selection and prediction outcome selection for display
        if (userTeamSelection == 1)
            userMatchPrediction.setPredictedMatchTeam(userMatchPrediction.getPredictionMatch().getHomeTeam());
        else
            userMatchPrediction.setPredictedMatchTeam(userMatchPrediction.getPredictionMatch().getAwayTeam());

        if (userOutcomeSelection == 1)
            userMatchPrediction.setPredictedMatchOutcome("win");
        else if (userOutcomeSelection == 2)
            userMatchPrediction.setPredictedMatchOutcome("loss");
        else
            userMatchPrediction.setPredictedMatchOutcome("draw");

        // Set the prediction description
        userMatchPrediction.getPrediction().setPredictionContent("I predict that the match between " + userMatchPrediction.getPredictionMatch().getHomeTeam() + " and " + userMatchPrediction.getPredictionMatch().getAwayTeam() + " on " + userMatchPrediction.getPredictionMatch().getUtcDate() + " will result in a " + userMatchPrediction.getPredictedMatchOutcome() + " for " + userMatchPrediction.getPredictedMatchTeam() + ".");
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // createNewFootballMatchYesterdayPrediction
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Validates that the user wants to make a prediction on yesterday's matches and if the user enters a Y,
    // the function calls getUserChoiceMatchTomorrow, getUserMatchPrediction, and then saveMatchPrediction.
    // Used for testing FootballMatchPredictionUpdater to create predictions based off of finished matches.
    //
    public static void createNewFootballMatchYesterdayPrediction(String userIdentifier) {
        boolean valid = true;	// Used for user input validation

        // Predict on yesterday's games?
        String userInput = null;
        do {
            System.out.println("Would you like to make a prediction on yesterday's games? (Y/N)");
            try {
                userInput = scan.next();
            } catch (Exception e) {
                e.printStackTrace();
                valid = false;
            }
        } while (!valid && !userInput.equalsIgnoreCase("Y") && !userInput.equalsIgnoreCase("N"));
        if (userInput.equalsIgnoreCase("Y")) {
            getUserChoiceMatchYesterday();
            if (userMatchPrediction.getPredictionMatch() != null) {
                getUserMatchPrediction();
                saveMatchPrediction(userIdentifier);
            }
            else {
                System.out.println("ERROR - No matches available today for predictions to be made on.");
            }
        }
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // createNewFootballMatchTodayPrediction
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Validates that the user wants to make a prediction on today's matches and if the user enters a Y,
    // the function calls getUserChoiceMatchTomorrow, getUserMatchPrediction, and then saveMatchPrediction.
    // Used for testing FootballMatchPredictionUpdater to create predictions based off of finished matches.
    //
    public static void createNewFootballMatchTodayPrediction(String userIdentifier) {
        boolean valid = true;	// Used for user input validation

        // Predict on today's games?
        String userInput = null;
        do {
            System.out.println("Would you like to make a prediction on today's games? (Y/N)");
            try {
                userInput = scan.next();
            } catch (Exception e) {
                e.printStackTrace();
                valid = false;
            }
        } while (!valid && !userInput.equalsIgnoreCase("Y") && !userInput.equalsIgnoreCase("N"));
        if (userInput.equalsIgnoreCase("Y")) {
            getUserChoiceMatchToday();
            if (userMatchPrediction.getPredictionMatch() != null) {
                getUserMatchPrediction();
                saveMatchPrediction(userIdentifier);
            }
            else {
                System.out.println("ERROR - No matches available today for predictions to be made on.");
            }
        }
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // createNewFootballMatchTomorrowPrediction
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Validates that the user wants to make a prediction on tomorrow's matches and if the user enters a Y,
    // the function calls getUserChoiceMatchTomorrow, getUserMatchPrediction, and then saveMatchPrediction.
    //
    public static void createNewFootballMatchTomorrowPrediction(String userIdentifier) {
        boolean valid = true;	// Used for user input validation

        // Predict on tomorrow's games?
        String userInput = null;
        do {
            System.out.println("Would you like to make a prediction on tomorrow's games? (Y/N)");
            try {
                userInput = scan.next();
            } catch (Exception e) {
                e.printStackTrace();
                valid = false;
            }
        } while (!valid && !userInput.equalsIgnoreCase("Y") && !userInput.equalsIgnoreCase("N"));
        if (userInput.equalsIgnoreCase("Y")) {
            getUserChoiceMatchTomorrow();
            if (userMatchPrediction.getPredictionMatch() != null) {
                getUserMatchPrediction();
                saveMatchPrediction(userIdentifier);
            }
            else {
                System.out.println("ERROR - No matches available tomorrow for predictions to be made on.");
            }
        }
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // createNewFootballMatchUpcomingWeek1Prediction
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Validates that the user wants to make a prediction on the upcoming week's matches and if the user
    // enters a Y, the function calls getUserChoiceMatchTomorrow, getUserMatchPrediction, and then
    // saveMatchPrediction.
    public static void createNewFootballMatchUpcomingWeek1Prediction(String userIdentifier) {
        boolean valid = true;	// Used for user input validation

        // Predict on next weeks games?
        String userInput = null;
        do {
            System.out.println("Would you like to make a prediction on the upcoming week's games? (Y/N)");
            try {
                userInput = scan.next();
            } catch (Exception e) {
                e.printStackTrace();
                valid = false;
            }
        } while (!valid && !userInput.equalsIgnoreCase("Y") && !userInput.equalsIgnoreCase("N"));
        if (userInput.equalsIgnoreCase("Y")) {
            getUserChoiceMatchUpcomingWeek1();
            if (userMatchPrediction.getPredictionMatch() != null) {
                getUserMatchPrediction();
                saveMatchPrediction(userIdentifier);
            }
            else {
                System.out.println("ERROR - No matches available the upcoming week for predictions to be made on.");
            }
        }
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // createNewFootballMatchUpcomingWeek2Prediction
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Validates that the user wants to make a prediction on the second upcoming week's matches and if the
    // user enters a Y, the function calls getUserChoiceMatchTomorrow, getUserMatchPrediction, and then
    // saveMatchPrediction.
    //
    public static void createNewFootballMatchUpcomingWeek2Prediction(String userIdentifier) {
        boolean valid = true;	// Used for user input validation

        // Predict on second upcoming week's games?
        String userInput = null;
        do {
            System.out.println("Would you like to make a prediction on the second upcoming week's games? (Y/N)");
            try {
                userInput = scan.next();
            } catch (Exception e) {
                e.printStackTrace();
                valid = false;
            }
        } while (!valid && !userInput.equalsIgnoreCase("Y") && !userInput.equalsIgnoreCase("N"));
        if (userInput.equalsIgnoreCase("Y")) {
            getUserChoiceMatchUpcomingWeek2();
            if (userMatchPrediction.getPredictionMatch() != null) {
                getUserMatchPrediction();
                saveMatchPrediction(userIdentifier);
            }
            else {
                System.out.println("ERROR - No matches available the second upcoming week for predictions to be made on.");
            }
        }
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // createNewFootballMatchUpcomingWeek3Prediction
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Validates that the user wants to make a prediction on the third upcoming week's matches and if the
    // user enters a Y, the function calls getUserChoiceMatchTomorrow, getUserMatchPrediction, and then
    // saveMatchPrediction.
    public static void createNewFootballMatchUpcomingWeek3Prediction(String userIdentifier) {
        boolean valid = true;	// Used for user input validation

        // Predict on third upcoming week's games?
        String userInput = null;
        do {
            System.out.println("Would you like to make a prediction on the third upcoming week's games? (Y/N)");
            try {
                userInput = scan.next();
            } catch (Exception e) {
                e.printStackTrace();
                valid = false;
            }
        } while (!valid && !userInput.equalsIgnoreCase("Y") && !userInput.equalsIgnoreCase("N"));
        if (userInput.equalsIgnoreCase("Y")) {
            getUserChoiceMatchUpcomingWeek3();
            // If there is not already a prediction on the selected match
            if (userMatchPrediction.getPredictionMatch() != null) {
                // Gather the football match prediction from the user and save to file
                getUserMatchPrediction();
                saveMatchPrediction(userIdentifier);
            }
            else {
                System.out.println("ERROR - No matches available the third upcoming week for predictions to be made on.");
            }
        }
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // savePrediction
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Saves the football match prediction to the given String parameter username's football match
    // prediction file.
    //
    public static void saveMatchPrediction(String userIdentifier) {
        // Set prediction type
        userMatchPrediction.getPrediction().setPredictionType("FootballMatch");

        // Set prediction end date (considering it as UTC)
        DateTimeFormatter matchDateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'").withZone(ZoneId.of("UTC"));
        ZonedDateTime matchDate = ZonedDateTime.parse(userMatchPrediction.getPredictionMatch().getUtcDate(), matchDateFormatter);
        userMatchPrediction.getPrediction().setPredictionEndDate(matchDate.toString());

        // Set prediction made date
        userMatchPrediction.getPrediction().setPredictionMadeDate(ZonedDateTime.now().toString());

        // Initialize boolean flag previouslyPredictedMatch to false
        boolean previouslyPredictedMatch = false;

        // Initialize new array list of FootballMatchPrediction to load into and save from
        ArrayList<FootballMatchPrediction> loadedMatchPredictions = new ArrayList<>();

        // Check if the file exists before attempting to load it
        String filePath = folderPath + File.separator + userIdentifier + "_football_match_predictions.json";
        File file = new File(filePath);
        if (file.exists()) {
            // Load the file
            try {
                String json = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(filePath)));
                Gson gson = new Gson();
                Type listType = new TypeToken<ArrayList<FootballMatchPrediction>>() {}.getType();
                loadedMatchPredictions = gson.fromJson(json, listType);
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Check for a prediction already made on the selected match
            for (FootballMatchPrediction prediction : loadedMatchPredictions) {
                if (prediction.getPredictionMatch().getMatchId() == userMatchPrediction.getPredictionMatch().getMatchId()) {
                    previouslyPredictedMatch = true;
                    //System.out.println("A match with the same ID was found in the loaded predictions.");
                    break;
                }
            }
        }
        // If boolean flag for a previously predicted match is false
        if (!previouslyPredictedMatch) {
            // Add the newly made football match prediction to the array list
            loadedMatchPredictions.add(userMatchPrediction);

            // Save the list to a JSON file in the specified directory
            try (FileWriter writer = new FileWriter(filePath)) {
                Gson gson = new Gson();
                Type listType = new TypeToken<ArrayList<FootballMatchPrediction>>() {}.getType();
                String json = gson.toJson(loadedMatchPredictions, listType);
                writer.write(json);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("ERROR - The chosen match already has a prediction made on it. Remove the football match prediction and try again.");
        }
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // createNewFootballMatchYesterdayPredictionMongoDB
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Validates that the user wants to make a prediction on yesterday's matches and if the user enters a Y,
    // the function calls getUserChoiceMatchTomorrow, getUserMatchPrediction, and then saveMatchPrediction.
    // Used for testing FootballMatchPredictionUpdater to create predictions based off of finished matches.
    //
    public static void createNewFootballMatchYesterdayPredictionMongoDB(String userIdentifier) {
        boolean valid = true;	// Used for user input validation

        // Predict on yesterday's games?
        String userInput = null;
        do {
            System.out.println("Would you like to make a prediction on yesterday's games? (Y/N)");
            try {
                userInput = scan.next();
            } catch (Exception e) {
                e.printStackTrace();
                valid = false;
            }
        } while (!valid && !userInput.equalsIgnoreCase("Y") && !userInput.equalsIgnoreCase("N"));
        if (userInput.equalsIgnoreCase("Y")) {
            getUserChoiceMatchYesterday();
            if (userMatchPrediction.getPredictionMatch() != null) {
                getUserMatchPrediction();
                saveMatchPredictionMongoDB(userIdentifier);
            }
            else {
                System.out.println("ERROR - No matches available today for predictions to be made on.");
            }
        }
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // createNewFootballMatchTodayPredictionMongoDB
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Validates that the user wants to make a prediction on today's matches and if the user enters a Y,
    // the function calls getUserChoiceMatchTomorrow, getUserMatchPrediction, and then saveMatchPrediction.
    // Used for testing FootballMatchPredictionUpdater to create predictions based off of finished matches.
    //
    public static void createNewFootballMatchTodayPredictionMongoDB(String userIdentifier) {
        boolean valid = true;	// Used for user input validation

        // Predict on today's games?
        String userInput = null;
        do {
            System.out.println("Would you like to make a prediction on today's games? (Y/N)");
            try {
                userInput = scan.next();
            } catch (Exception e) {
                e.printStackTrace();
                valid = false;
            }
        } while (!valid && !userInput.equalsIgnoreCase("Y") && !userInput.equalsIgnoreCase("N"));
        if (userInput.equalsIgnoreCase("Y")) {
            getUserChoiceMatchToday();
            if (userMatchPrediction.getPredictionMatch() != null) {
                getUserMatchPrediction();
                saveMatchPredictionMongoDB(userIdentifier);
            }
            else {
                System.out.println("ERROR - No matches available today for predictions to be made on.");
            }
        }
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // createNewFootballMatchTomorrowPredictionMongoDB
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Validates that the user wants to make a prediction on tomorrow's matches and if the user enters a Y,
    // the function calls getUserChoiceMatchTomorrow, getUserMatchPrediction, and then saveMatchPrediction.
    //
    public static void createNewFootballMatchTomorrowPredictionMongoDB(String userIdentifier) {
        boolean valid = true;	// Used for user input validation

        // Predict on tomorrow's games?
        String userInput = null;
        do {
            System.out.println("Would you like to make a prediction on tomorrow's games? (Y/N)");
            try {
                userInput = scan.next();
            } catch (Exception e) {
                e.printStackTrace();
                valid = false;
            }
        } while (!valid && !userInput.equalsIgnoreCase("Y") && !userInput.equalsIgnoreCase("N"));
        if (userInput.equalsIgnoreCase("Y")) {
            getUserChoiceMatchTomorrow();
            if (userMatchPrediction.getPredictionMatch() != null) {
                getUserMatchPrediction();
                saveMatchPredictionMongoDB(userIdentifier);
            }
            else {
                System.out.println("ERROR - No matches available tomorrow for predictions to be made on.");
            }
        }
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // createNewFootballMatchUpcomingWeek1PredictionMongoDB
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Validates that the user wants to make a prediction on the upcoming week's matches and if the user
    // enters a Y, the function calls getUserChoiceMatchTomorrow, getUserMatchPrediction, and then
    // saveMatchPrediction.
    public static void createNewFootballMatchUpcomingWeek1PredictionMongoDB(String userIdentifier) {
        boolean valid = true;	// Used for user input validation

        // Predict on next weeks games?
        String userInput = null;
        do {
            System.out.println("Would you like to make a prediction on the upcoming week's games? (Y/N)");
            try {
                userInput = scan.next();
            } catch (Exception e) {
                e.printStackTrace();
                valid = false;
            }
        } while (!valid && !userInput.equalsIgnoreCase("Y") && !userInput.equalsIgnoreCase("N"));
        if (userInput.equalsIgnoreCase("Y")) {
            getUserChoiceMatchUpcomingWeek1();
            if (userMatchPrediction.getPredictionMatch() != null) {
                getUserMatchPrediction();
                saveMatchPredictionMongoDB(userIdentifier);
            }
            else {
                System.out.println("ERROR - No matches available the upcoming week for predictions to be made on.");
            }
        }
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // createNewFootballMatchUpcomingWeek2PredictionMongoDB
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Validates that the user wants to make a prediction on the second upcoming week's matches and if the
    // user enters a Y, the function calls getUserChoiceMatchTomorrow, getUserMatchPrediction, and then
    // saveMatchPrediction.
    //
    public static void createNewFootballMatchUpcomingWeek2PredictionMongoDB(String userIdentifier) {
        boolean valid = true;	// Used for user input validation

        // Predict on second upcoming week's games?
        String userInput = null;
        do {
            System.out.println("Would you like to make a prediction on the second upcoming week's games? (Y/N)");
            try {
                userInput = scan.next();
            } catch (Exception e) {
                e.printStackTrace();
                valid = false;
            }
        } while (!valid && !userInput.equalsIgnoreCase("Y") && !userInput.equalsIgnoreCase("N"));
        if (userInput.equalsIgnoreCase("Y")) {
            getUserChoiceMatchUpcomingWeek2();
            if (userMatchPrediction.getPredictionMatch() != null) {
                getUserMatchPrediction();
                saveMatchPredictionMongoDB(userIdentifier);
            }
            else {
                System.out.println("ERROR - No matches available the second upcoming week for predictions to be made on.");
            }
        }
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // createNewFootballMatchUpcomingWeek3PredictionMongoDB
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Validates that the user wants to make a prediction on the third upcoming week's matches and if the
    // user enters a Y, the function calls getUserChoiceMatchTomorrow, getUserMatchPrediction, and then
    // saveMatchPrediction.
    public static void createNewFootballMatchUpcomingWeek3PredictionMongoDB(String userIdentifier) {
        boolean valid = true;	// Used for user input validation

        // Predict on third upcoming week's games?
        String userInput = null;
        do {
            System.out.println("Would you like to make a prediction on the third upcoming week's games? (Y/N)");
            try {
                userInput = scan.next();
            } catch (Exception e) {
                e.printStackTrace();
                valid = false;
            }
        } while (!valid && !userInput.equalsIgnoreCase("Y") && !userInput.equalsIgnoreCase("N"));
        if (userInput.equalsIgnoreCase("Y")) {
            getUserChoiceMatchUpcomingWeek3();
            // If there is not already a prediction on the selected match
            if (userMatchPrediction.getPredictionMatch() != null) {
                // Gather the football match prediction from the user and save to file
                getUserMatchPrediction();
                saveMatchPredictionMongoDB(userIdentifier);
            }
            else {
                System.out.println("ERROR - No matches available the third upcoming week for predictions to be made on.");
            }
        }
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // savePredictionMongoDB
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Saves the football match prediction to the given String parameter username's football match
    // prediction file.
    //
    public static void saveMatchPredictionMongoDB(String userIdentifier) {
        // Set prediction type
        userMatchPrediction.getPrediction().setPredictionType("FootballMatch");

        // Set prediction end date (considering it as UTC)
        DateTimeFormatter matchDateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'").withZone(ZoneId.of("UTC"));
        ZonedDateTime matchDate = ZonedDateTime.parse(userMatchPrediction.getPredictionMatch().getUtcDate(), matchDateFormatter);
        userMatchPrediction.getPrediction().setPredictionEndDate(matchDate.toString());

        // Set prediction made date
        userMatchPrediction.getPrediction().setPredictionMadeDate(ZonedDateTime.now().toString());

        // Initialize boolean flag previouslyPredictedMatch to false
        boolean previouslyPredictedMatch = false;

        // Initialize new array list of FootballMatchPrediction to update
        ArrayList<FootballMatchPrediction> loadedMatchPredictions = MongoDBEnvisionaryUsers.retrieveUserFootballMatchPredictions(userIdentifier);

        if (loadedMatchPredictions == null) {
           loadedMatchPredictions = new ArrayList<>();
        }

        // Check for a prediction already made on the selected match
        for (FootballMatchPrediction prediction : loadedMatchPredictions) {
            if (prediction.getPredictionMatch().getMatchId() == userMatchPrediction.getPredictionMatch().getMatchId()) {
                previouslyPredictedMatch = true;
                //System.out.println("A match with the same ID was found in the loaded predictions.");
                break;
            }
        }

        // If boolean flag for a previously predicted match is false
        if (!previouslyPredictedMatch) {
            // Add the newly made football match prediction to the array list
            loadedMatchPredictions.add(userMatchPrediction);

            // Save the list to a JSON file in the specified directory
            MongoDBEnvisionaryUsers.updateUserFootballMatchPredictions(userIdentifier, loadedMatchPredictions);

        } else {
            System.out.println("ERROR - The chosen match already has a prediction made on it. Remove the football match prediction and try again.");
        }
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // removeFootballMatchPrediction
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Lists the football match predictions stored within the given userIdentifier's football match
    // prediction file with indexes starting at 1 and asks the user to input the index of the football
    // match prediction that they want to remove before confirming and removing the selected football match
    // prediction from the ArrayList of FootballMatchPrediction objects and saving the new list to the file.
    //
    public static void removeFootballMatchPrediction(String userIdentifier) {
        Scanner scan = new Scanner(System.in);

        // Initialize new array list of FootballMatchPrediction to load into and save from
        ArrayList<FootballMatchPrediction> loadedMatchPredictions = new ArrayList<>();

        // Load the list of user predictions from the file
        String filePath = folderPath + File.separator + userIdentifier + "_football_match_predictions.json";
        File file = new File(filePath);

        if (file.exists()) {
            try {
                String json = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(filePath)));
                Gson gson = new Gson();
                Type listType = new TypeToken<ArrayList<FootballMatchPrediction>>() {}.getType();
                loadedMatchPredictions = gson.fromJson(json, listType);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (loadedMatchPredictions.isEmpty()) {
            System.out.println("ERROR - No football match predictions available to remove.\n");
            return; // Exit the function
        }

        // Display the list of football match predictions for the user to select from
        System.out.println("Select a prediction to remove from the list of your previously made football match predictions:");
        int predictionIndex = 1;
        for (FootballMatchPrediction matchPrediction : loadedMatchPredictions) {
            System.out.println(predictionIndex + " : " + matchPrediction.getPrediction().getPredictionContent());
            ++predictionIndex;
        }
        int predictionRemovalSelection = -1; // Initialize the selection variable

        // Get user input from the list of predictions to remove with input validation
        boolean validInput = false;
        while (!validInput) {
            try {
                System.out.print("Enter the number of the prediction to remove: ");
                predictionRemovalSelection = scan.nextInt();
                validInput = true; // The input is valid if no exception is thrown
            } catch (InputMismatchException e) {
                System.out.println("ERROR - Invalid input. Please enter a valid number.");
                scan.nextLine(); // Consume the invalid input
            }
        }

        if (predictionRemovalSelection >= 1 && predictionRemovalSelection <= loadedMatchPredictions.size()) {
            // Ask if the user is sure about the choice
            System.out.print("Are you sure you want to remove this prediction? (Y/N):\n" + loadedMatchPredictions.get(predictionRemovalSelection - 1).getPrediction().getPredictionContent() + "\n");
            String confirmation = scan.next();

            if (confirmation.equalsIgnoreCase("Y")) {
                // Remove the prediction from the list
                loadedMatchPredictions.remove(predictionRemovalSelection - 1);

                // Save over the old user prediction file
                try (FileWriter writer = new FileWriter(filePath)) {
                    Gson gson = new Gson();
                    Type listType = new TypeToken<ArrayList<FootballMatchPrediction>>() {}.getType();
                    String json = gson.toJson(loadedMatchPredictions, listType);
                    writer.write(json);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Prediction removal canceled.");
            }
        } else {
            System.out.println("ERROR - Invalid prediction number.");
        }
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // removeFootballMatchPredictionMongoDB
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Lists the football match predictions stored within the given userIdentifier's football match
    // prediction file with indexes starting at 1 and asks the user to input the index of the football
    // match prediction that they want to remove before confirming and removing the selected football match
    // prediction from the ArrayList of FootballMatchPrediction objects and saving the new list to the file.
    //
    public static void removeFootballMatchPredictionMongoDB(String userIdentifier) {
        Scanner scan = new Scanner(System.in);

        // Initialize new array list of FootballMatchPrediction to load into and save from
        ArrayList<FootballMatchPrediction> loadedMatchPredictions = MongoDBEnvisionaryUsers.retrieveUserFootballMatchPredictions(userIdentifier);

        if (loadedMatchPredictions == null) {
            loadedMatchPredictions = new ArrayList<>();
            System.out.println("ERROR - No football match predictions available to remove.\n");
            return; // Exit the function
        }

        // Display the list of football match predictions for the user to select from
        System.out.println("Select a prediction to remove from the list of your previously made football match predictions:");
        int predictionIndex = 1;
        for (FootballMatchPrediction matchPrediction : loadedMatchPredictions) {
            System.out.println(predictionIndex + " : " + matchPrediction.getPrediction().getPredictionContent());
            ++predictionIndex;
        }
        int predictionRemovalSelection = -1; // Initialize the selection variable

        // Get user input from the list of predictions to remove with input validation
        boolean validInput = false;
        while (!validInput) {
            try {
                System.out.print("Enter the number of the prediction to remove: ");
                predictionRemovalSelection = scan.nextInt();
                validInput = true; // The input is valid if no exception is thrown
            } catch (InputMismatchException e) {
                System.out.println("ERROR - Invalid input. Please enter a valid number.");
                scan.nextLine(); // Consume the invalid input
            }
        }

        if (predictionRemovalSelection >= 1 && predictionRemovalSelection <= loadedMatchPredictions.size()) {
            // Ask if the user is sure about the choice
            System.out.print("Are you sure you want to remove this prediction? (Y/N):\n" + loadedMatchPredictions.get(predictionRemovalSelection - 1).getPrediction().getPredictionContent() + "\n");
            String confirmation = scan.next();

            if (confirmation.equalsIgnoreCase("Y")) {
                // Remove the prediction from the list
                loadedMatchPredictions.remove(predictionRemovalSelection - 1);

                // Save over the old user prediction file
                MongoDBEnvisionaryUsers.updateUserFootballMatchPredictions(userIdentifier, loadedMatchPredictions);
            } else {
                System.out.println("Prediction removal canceled.");
            }
        } else {
            System.out.println("ERROR - Invalid prediction number.");
        }
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // printAllUserMatchPredictions
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Lists all the football match predictions stored within the given userIdentifier's football match
    // prediction file.
    //
    public static void printAllFootballMatchPredictions(String userIdentifier) {
        // Initialize new array list of FootballMatchPrediction to load file data into
        ArrayList<FootballMatchPrediction> loadedMatchPredictions = new ArrayList<>();

        // Check if the file exists before attempting to load it
        String filePath = folderPath + File.separator + userIdentifier + "_football_match_predictions.json";
        File file = new File(filePath);

        // Check if the file exists before attempting to load it
        if (file.exists()) {
            // Load the file
            try {
                String json = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(filePath)));
                Gson gson = new Gson();
                Type listType = new TypeToken<ArrayList<FootballMatchPrediction>>() {}.getType();
                loadedMatchPredictions = gson.fromJson(json, listType);
                System.out.println("\n====================================================================================================");
                System.out.println("                                 " + userIdentifier + "'s Football Match Predictions");
                // For each match prediction within the array list of loaded match predictions
                for (FootballMatchPrediction matchPrediction : loadedMatchPredictions) {
                    System.out.println(matchPrediction.getPrediction().getPredictionMadeDate() + " \"" + matchPrediction.getPrediction().getPredictionContent() + "\"" + " -" + userIdentifier);
                }
                System.out.println();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("ERROR - Football match prediction file does not exist for user: " + userIdentifier + "\n");
        }
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // printAllUserMatchPredictionsMongoDB
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Lists all the football match predictions stored within the given userIdentifier's football match
    // prediction file.
    //
    public static void printAllFootballMatchPredictionsMongoDB(String userIdentifier) {
        // Initialize new array list of FootballMatchPrediction to update
        ArrayList<FootballMatchPrediction> loadedMatchPredictions = MongoDBEnvisionaryUsers.retrieveUserFootballMatchPredictions(userIdentifier);
        if (loadedMatchPredictions == null) {
            loadedMatchPredictions = new ArrayList<>();
        }
        System.out.println("\n====================================================================================================");
        System.out.println("                                 " + userIdentifier + "'s Football Match Predictions");
        // For each match prediction within the array list of loaded match predictions
        for (FootballMatchPrediction matchPrediction : loadedMatchPredictions) {
            System.out.println(matchPrediction.getPrediction().getPredictionMadeDate() + " \"" + matchPrediction.getPrediction().getPredictionContent() + "\"" + " -" + userIdentifier);
        }
        System.out.println();
    }
}
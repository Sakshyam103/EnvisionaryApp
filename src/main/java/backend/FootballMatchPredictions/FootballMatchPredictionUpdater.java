package backend.FootballMatchPredictions;

import backend.Notifications.NotificationUpdater;
import backend.ResolvedPredictions.ResolvedPrediction;
import backend.UserStatistics.UserDescriptiveStatisticsUpdater;
import backend.UserStatistics.UserInferentialStatisticsUpdater;
import backend.OverallStatistics.OverallDescriptiveStatisticsUpdater;
import backend.OverallStatistics.OverallInferentialStatisticsUpdater;
import backend.UserInfo.EnvisionaryUser;
import backend.UserInfo.MongoDBEnvisionaryUsers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.ZonedDateTime;
import java.util.ArrayList;

//■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
// FootballMatchPredictionUpdater class - Written by Brandon LaPointe
//■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
// Updates the football match predictions when called by loading the list of today's football matches
// and loading each user's football match prediction file before comparing each football match
// prediction's football match ID to each of today's match IDs. If the updater finds a matching ID
// number and today's match's status is equal to "FINISHED", the football match prediction will be
// converted into a resolved match prediction and the user's resolved predictions will be loaded and
// stored within an ArrayList of ResolvedPredictions.ResolvedPrediction objects, the newly converted resolved football
// match prediction will then be added to the ArrayList of resolved predictions and saved to the
// user's resolved prediction file before the updater removes the football match prediction from the
// user's football match prediction file and saves the updated football match prediction file, creating
// a new notification on the resolved football match prediction including its outcome and the user
// descriptive statistics, user inferential statistics, overall descriptive, and overall inferential
// statistics are updated. If the updater finds a matching ID number and today's match's status is equal
// to "POSTPONED", "CANCELLED", or "SUSPENDED" the updater will automatically remove the football match
// prediction and send a notification to the user on the cancellation of their football match prediction
// due to the football match status.
//
public class FootballMatchPredictionUpdater {
    // FootballMatchPredictionUpdater Class Constants & Variables
    private static final String userHome = System.getProperty("user.home");
    private static final String folderPath = userHome + File.separator + "EnvisionaryApp" + File.separator + "SportsPredictions" + File.separator + "FootballPredictions";
    private static final String resolvedPredictionFolderPath = userHome + File.separator + "EnvisionaryApp" + File.separator + "ResolvedPredictions";


    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // updateFootballMatchPredictionsLocally
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Updates the football match predictions when called by loading the list of today's football matches
    // and loading each user's football match prediction file before comparing each football match
    // prediction's football match ID to each of today's match IDs.
    //
    public static void updateFootballMatchPredictionsLocally() {
        // userIdentifier collected from the file path of the current football match prediction file being checked
        String userIdentifier;

        // Initialize array lists of FootballMatchPrediction and FootballMatch for comparison
        ArrayList<FootballMatchPrediction> loadedUserMatchPredictions = new ArrayList<>();
        ArrayList<FootballMatch> loadedTodaysMatches = new ArrayList<>();

        // Check if the today's matches file exists before attempting to load it
        String todayMatchesFilePath = folderPath + File.separator + "FootballMatchData" + File.separator + "football_matches_today.json";       // TEST USING YESTERDAY'S MATCHES
        File todayMatchesFile = new File(todayMatchesFilePath);
        if (todayMatchesFile.exists()) {
            // Load the file
            try {
                String json = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(todayMatchesFilePath)));
                Gson gson = new Gson();
                Type listType = new TypeToken<ArrayList<FootballMatch>>() {}.getType();
                loadedTodaysMatches = gson.fromJson(json, listType);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("ERROR - Football match file for today's matches does not exist.");
            return;
        }

        // Initialize predictionFilesFolder as a new File
        File predictionFilesFolder = new File(folderPath + File.separator + "FootballMatchPredictions");

        // If the prediction files folder exists and is a directory
        if (predictionFilesFolder.exists() && predictionFilesFolder.isDirectory()) {
            // Initialize the file array of user football match prediction files
            File[] footballMatchPredictionFiles = predictionFilesFolder.listFiles();
            // If the file array is not null
            if (footballMatchPredictionFiles != null) {
                // For each file within the user football match prediction folder
                for (File userPredictionFile : footballMatchPredictionFiles) {
                    // Gather userIdentifier from the file path
                    String fileNameWithoutExtension = userPredictionFile.getName(); // Get the file name without path and extension
                    userIdentifier = fileNameWithoutExtension.replace("_football_match_predictions.json", "");
                    //System.out.println("UserInfo.User Identifier: " + userIdentifier);

                    // Load the file
                    try {
                        String json = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(userPredictionFile.getAbsolutePath())));
                        Gson gson = new Gson();
                        Type listType = new TypeToken<ArrayList<FootballMatchPrediction>>() {}.getType();
                        loadedUserMatchPredictions = gson.fromJson(json, listType);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    // Initialize a new array list of football match prediction to store predictions that need to be removed
                    ArrayList<FootballMatchPrediction> predictionsToRemove = new ArrayList<>();

                    // For each prediction within array list of FootballMatchPrediction
                    for (FootballMatchPrediction userMatchPrediction : loadedUserMatchPredictions) {
                        // Initialize remove prediction boolean flag
                        boolean removePrediction = false;

                        // Compare to each of today's football matches
                        for (FootballMatch todaysMatch : loadedTodaysMatches) {
                            // If prediction match ID == today's match ID && status == POSTPONED || status == CANCELLED || status == SUSPENDED
                            if (userMatchPrediction.getPredictionMatch().getMatchId() == todaysMatch.getMatchId() && todaysMatch.getStatus().equalsIgnoreCase("POSTPONED") ||
                                    todaysMatch.getStatus().equalsIgnoreCase("CANCELLED")||
                                    todaysMatch.getStatus().equalsIgnoreCase("SUSPENDED")) {
                                // Send notification to the user
                                NotificationUpdater.newFootballMatchPredictionCancelledNotification(userMatchPrediction, userIdentifier);

                                // Set remove prediction flag to true
                                removePrediction = true;
                            }

                            // If prediction match ID == today's match ID && status == FINISHED
                            if (userMatchPrediction.getPredictionMatch().getMatchId() == todaysMatch.getMatchId() && todaysMatch.getStatus().equalsIgnoreCase("FINISHED")) {

                                // Initialize new resolved prediction
                                ResolvedPrediction resolvedFootballMatchPrediction = new ResolvedPrediction();

                                // Copy over football match prediction values to the new resolved prediction
                                resolvedFootballMatchPrediction.setPredictionType(userMatchPrediction.getPrediction().getPredictionType());
                                resolvedFootballMatchPrediction.setPredictionContent(userMatchPrediction.getPrediction().getPredictionContent());
                                resolvedFootballMatchPrediction.setPredictionMadeDate(userMatchPrediction.getPrediction().getPredictionMadeDate());
                                resolvedFootballMatchPrediction.setPredictionEndDate(userMatchPrediction.getPrediction().getPredictionEndDate());

                                // If winner was accurately predicted, set resolution to true
                                if (userMatchPrediction.getPredictedMatchTeam().equals(todaysMatch.getHomeTeam()) && todaysMatch.getWinner().equalsIgnoreCase("HOME_TEAM") && userMatchPrediction.getPredictedMatchOutcome().equalsIgnoreCase("win"))
                                    resolvedFootballMatchPrediction.setResolution(true);
                                else if (userMatchPrediction.getPredictedMatchTeam().equals(todaysMatch.getAwayTeam()) && todaysMatch.getWinner().equalsIgnoreCase("AWAY_TEAM") && userMatchPrediction.getPredictedMatchOutcome().equalsIgnoreCase("win"))
                                    resolvedFootballMatchPrediction.setResolution(true);
                                    // Else if loser was accurately predicted, set resolution to true
                                else if (userMatchPrediction.getPredictedMatchTeam().equals(todaysMatch.getHomeTeam()) && todaysMatch.getWinner().equalsIgnoreCase("AWAY_TEAM") && userMatchPrediction.getPredictedMatchOutcome().equalsIgnoreCase("loss"))
                                    resolvedFootballMatchPrediction.setResolution(true);
                                else if (userMatchPrediction.getPredictedMatchTeam().equals(todaysMatch.getAwayTeam()) && todaysMatch.getWinner().equalsIgnoreCase("HOME_TEAM") && userMatchPrediction.getPredictedMatchOutcome().equalsIgnoreCase("loss"))
                                    resolvedFootballMatchPrediction.setResolution(true);
                                    // Else if a draw was accurately predicted, set resolution to true
                                else if (todaysMatch.getWinner().equalsIgnoreCase("DRAW") && userMatchPrediction.getPredictedMatchOutcome().equalsIgnoreCase("DRAW"))
                                    resolvedFootballMatchPrediction.setResolution(true);
                                else // set resolution to false
                                    resolvedFootballMatchPrediction.setResolution(false);

                                // Set the resolved date of the ResolvedPredictions.ResolvedPrediction
                                resolvedFootballMatchPrediction.setResolvedDate(ZonedDateTime.now().toString());

                                // Initialize new array list of ResolvedPredictions.ResolvedPrediction to load into and save from
                                ArrayList<ResolvedPrediction> loadedResolvedPredictions = new ArrayList<>();

                                // Load the list of resolved predictions from the file
                                // Check if the file exists before attempting to load it
                                String resolvedFilePath = resolvedPredictionFolderPath + File.separator + userIdentifier + "_resolved_predictions.json";
                                File resolvedFile = new File(resolvedFilePath);
                                if (resolvedFile.exists()) {
                                    // Load the file
                                    try {
                                        String json = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(resolvedFilePath)));
                                        Gson gson = new Gson();
                                        Type listType = new TypeToken<ArrayList<ResolvedPrediction>>() {}.getType();
                                        loadedResolvedPredictions = gson.fromJson(json, listType);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }

                                // Add the new resolved prediction to the file
                                loadedResolvedPredictions.add(resolvedFootballMatchPrediction);

                                // Save the resolved prediction to the user's resolved prediction list
                                try (FileWriter writer = new FileWriter(resolvedFilePath)) {
                                    Gson gson = new Gson();
                                    Type listType = new TypeToken<ArrayList<ResolvedPrediction>>() {}.getType();
                                    String json = gson.toJson(loadedResolvedPredictions, listType);
                                    writer.write(json);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                // Send a notification to the user
                                NotificationUpdater.newFootballMatchPredictionResolvedNotification(userMatchPrediction, userIdentifier, resolvedFootballMatchPrediction.resolution);

                                // Set removePrediction boolean flag to true
                                removePrediction = true;

                                // Calculate statistics of user and update UserStatistics, UserStatistics.UserInferentialStatistics, and OverallStatistics
                                UserDescriptiveStatisticsUpdater.calculateAndSaveUserDescriptiveStatistics(userIdentifier);
                                UserInferentialStatisticsUpdater.calculateAndSaveUserInferentialStatistics(userIdentifier);
                                OverallDescriptiveStatisticsUpdater.calculateAndSaveOverallDescriptiveStatistics();
                                OverallInferentialStatisticsUpdater.calculateAndSaveOverallInferentialStatistics();
                            }
                        }
                        // If removePrediction boolean flag is true
                        if (removePrediction) {
                            // Add the prediction to the array list of football match predictions to remove
                            predictionsToRemove.add(userMatchPrediction);
                        }
                    }
                    // Remove the resolved and cancelled predictions
                    loadedUserMatchPredictions.removeAll(predictionsToRemove);

                    // Save the updated list to the user's prediction file
                    try (FileWriter writer = new FileWriter(userPredictionFile)) {
                        Gson gson = new Gson();
                        Type listType = new TypeToken<ArrayList<FootballMatchPrediction>>() {}.getType();
                        String json = gson.toJson(loadedUserMatchPredictions, listType);
                        writer.write(json);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // updateFootballMatchPredictionsMongoDB
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Updates the football match predictions when called by loading the list of today's football matches
    // and loading each user's football match prediction file before comparing each football match
    // prediction's football match ID to each of today's match IDs.
    //
    public static void updateFootballMatchPredictionsMongoDB() {
        // Retrieve collection of EnvisionaryUsers
        ArrayList<EnvisionaryUser> envisionaryUsers = MongoDBEnvisionaryUsers.retrieveCollection();

        // Retrieve collection of today's football matches
        ArrayList<FootballMatch> todaysMatches = MongoDBFootballMatchData.retrieveCollectionTimeFrameAndReturn("Today");

        // For each Envisionary UserInfo.User
        for (EnvisionaryUser user : envisionaryUsers) {
            // Initialize a new boolean predictionRemoved flag for each Envisionary User
            boolean removedPredictions = false;

            // Initialize an array list of the user's football match predictions
            ArrayList<FootballMatchPrediction> userFootballMatchPredictions = user.getFootballMatchPredictions();

            // Initialize a new array list of football match prediction to store predictions that need to be removed
            ArrayList<FootballMatchPrediction> predictionsToRemove = new ArrayList<>();

            // For each of the user's football match predictions
            for (FootballMatchPrediction userFootballMatchPrediction : userFootballMatchPredictions) {
                boolean removePrediction = false;

                for (FootballMatch todaysMatch : todaysMatches) {
                    // If prediction match ID == today's match ID && status == POSTPONED || status == CANCELLED || status == SUSPENDED
                    if (userFootballMatchPrediction.getPredictionMatch().getMatchId() == todaysMatch.getMatchId() && todaysMatch.getStatus().equalsIgnoreCase("POSTPONED") || todaysMatch.getStatus().equalsIgnoreCase("CANCELLED") || todaysMatch.getStatus().equalsIgnoreCase("SUSPENDED")) {
                        // Send notification to the user
                        NotificationUpdater.newFootballMatchPredictionCancelledNotificationMongoDB(userFootballMatchPrediction, user.getUserID());

                        // Set remove prediction flag to true
                        removePrediction = true;
                        removedPredictions = true;
                    }

                    // If prediction match ID == today's match ID && status == FINISHED
                    if (userFootballMatchPrediction.getPredictionMatch().getMatchId() == todaysMatch.getMatchId() && todaysMatch.getStatus().equalsIgnoreCase("FINISHED")) {

                        // Initialize new resolved prediction
                        ResolvedPrediction resolvedFootballMatchPrediction = new ResolvedPrediction();

                        // Copy over football match prediction values to the new resolved prediction
                        resolvedFootballMatchPrediction.setPredictionType(userFootballMatchPrediction.getPrediction().getPredictionType());
                        resolvedFootballMatchPrediction.setPredictionContent(userFootballMatchPrediction.getPrediction().getPredictionContent());
                        resolvedFootballMatchPrediction.setPredictionMadeDate(userFootballMatchPrediction.getPrediction().getPredictionMadeDate());
                        resolvedFootballMatchPrediction.setPredictionEndDate(userFootballMatchPrediction.getPrediction().getPredictionEndDate());

                        // If winner was accurately predicted, set resolution to true
                        if (userFootballMatchPrediction.getPredictedMatchTeam().equals(todaysMatch.getHomeTeam()) && todaysMatch.getWinner().equalsIgnoreCase("HOME_TEAM") && userFootballMatchPrediction.getPredictedMatchOutcome().equalsIgnoreCase("win"))
                            resolvedFootballMatchPrediction.setResolution(true);
                        else if (userFootballMatchPrediction.getPredictedMatchTeam().equals(todaysMatch.getAwayTeam()) && todaysMatch.getWinner().equalsIgnoreCase("AWAY_TEAM") && userFootballMatchPrediction.getPredictedMatchOutcome().equalsIgnoreCase("win"))
                            resolvedFootballMatchPrediction.setResolution(true);
                            // Else if loser was accurately predicted, set resolution to true
                        else if (userFootballMatchPrediction.getPredictedMatchTeam().equals(todaysMatch.getHomeTeam()) && todaysMatch.getWinner().equalsIgnoreCase("AWAY_TEAM") && userFootballMatchPrediction.getPredictedMatchOutcome().equalsIgnoreCase("loss"))
                            resolvedFootballMatchPrediction.setResolution(true);
                        else if (userFootballMatchPrediction.getPredictedMatchTeam().equals(todaysMatch.getAwayTeam()) && todaysMatch.getWinner().equalsIgnoreCase("HOME_TEAM") && userFootballMatchPrediction.getPredictedMatchOutcome().equalsIgnoreCase("loss"))
                            resolvedFootballMatchPrediction.setResolution(true);
                            // Else if a draw was accurately predicted, set resolution to true
                        else if (todaysMatch.getWinner().equalsIgnoreCase("DRAW") && userFootballMatchPrediction.getPredictedMatchOutcome().equalsIgnoreCase("DRAW"))
                            resolvedFootballMatchPrediction.setResolution(true);
                        else // set resolution to false
                            resolvedFootballMatchPrediction.setResolution(false);

                        // Set the resolved date of the ResolvedPredictions.ResolvedPrediction
                        resolvedFootballMatchPrediction.setResolvedDate(ZonedDateTime.now().toString());

                        // Initialize new array list of ResolvedPredictions.ResolvedPrediction to update
                        ArrayList<ResolvedPrediction> loadedResolvedPredictions = user.getResolvedPredictions();

                        // Add the new resolved prediction to the list
                        loadedResolvedPredictions.add(resolvedFootballMatchPrediction);

                        // Update the user's resolved predictions list
                        MongoDBEnvisionaryUsers.updateUserResolvedPredictions(user.getUserID(), loadedResolvedPredictions);

                        // Send a notification to the user
                        NotificationUpdater.newFootballMatchPredictionResolvedNotificationMongoDB(userFootballMatchPrediction, user.getUserID(), resolvedFootballMatchPrediction.resolution);

                        // Set removePrediction boolean flag to true
                        removePrediction = true;
                        removedPredictions = true;

                        // Calculate statistics of user and update UserStatistics, UserStatistics.UserInferentialStatistics, and OverallStatistics
                        UserDescriptiveStatisticsUpdater.calculateAndSaveUserDescriptiveStatisticsMongoDB(user.getUserID());
                        UserInferentialStatisticsUpdater.calculateAndSaveUserInferentialStatisticsMongoDB(user.getUserID());
                        OverallDescriptiveStatisticsUpdater.calculateAndSaveOverallDescriptiveStatisticsMongoDB();
                        OverallInferentialStatisticsUpdater.calculateAndSaveOverallInferentialStatisticsMongoDB();
                    }
                }
                // If removePrediction boolean flag is true
                if (removePrediction) {
                    // Add the prediction to the array list of football match predictions to remove
                    predictionsToRemove.add(userFootballMatchPrediction);
                }
            }
            if (removedPredictions) {
                // Remove the resolved and cancelled predictions
                userFootballMatchPredictions.removeAll(predictionsToRemove);

                // Save the updated user's football match predictions list
                MongoDBEnvisionaryUsers.updateUserFootballMatchPredictions(user.getUserID(), userFootballMatchPredictions);
            }
        }
    }
}
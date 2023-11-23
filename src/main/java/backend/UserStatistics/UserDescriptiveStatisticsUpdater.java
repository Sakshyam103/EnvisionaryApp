package backend.UserStatistics;

import backend.ResolvedPredictions.ResolvedPrediction;
import backend.UserInfo.MongoDBEnvisionaryUsers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;

//■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
// UserStatistics.UserDescriptiveStatisticsUpdater class - Written by Brandon LaPointe
//■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
// Initializes the file path and folders needed to save the UserStatistics.UserDescriptiveStatistics data. Allows
// for calculation and saving of the user descriptive statistics and the loading and displaying of
// the user descriptive statistics file to the console.
//
public class UserDescriptiveStatisticsUpdater {
    private static final String userHome = System.getProperty("user.home");
    private static final String resolvedPredictionFolderPath = userHome + File.separator + "EnvisionaryApp" + File.separator + "ResolvedPredictions";
    private static final String userDescriptiveStatisticsFolderPath = userHome + File.separator + "EnvisionaryApp" + File.separator + "UserStatistics" + File.separator + "Descriptive";

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // initializeFilePath
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Initializes the file path and folders needed to save the user's descriptive statistics.
    //
    public static void initializeFilePath() {
        // Create the directory if it doesn't exist
        File directory = new File(userDescriptiveStatisticsFolderPath);
        if (!directory.exists()) {
            if (directory.mkdirs()) {
                System.out.println("Created directory: " + userDescriptiveStatisticsFolderPath);
            } else {
                System.err.println("Failed to create directory: " + userDescriptiveStatisticsFolderPath);
            }
        }
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // calculateAndSaveUserDescriptiveStatistics
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Calculates the given userIdentifier's user statistics by reading each of the resolved prediction
    // resolutions and dividing the number of correct predictions by the total number of resolved
    // predictions and then subtracting that from 1 to get the percent of incorrect predictions before
    // saving the data to the userIdentifier's user statistics file.
    //
    public static void calculateAndSaveUserDescriptiveStatistics(String userIdentifier) {
        // Initialize an array list of resolved predictions to store the user's resolved predictions
        ArrayList<ResolvedPrediction> loadedResolvedPredictions = new ArrayList<>();

        // Check if the file exists before attempting to load it
        String resolvedFilePath = resolvedPredictionFolderPath + File.separator + userIdentifier + "_resolved_predictions.json";
        File resolvedFile = new File(resolvedFilePath);

        // Check if the file exists before attempting to load it
        if (resolvedFile.exists()) {
            // Load the file
            try {
                String json = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(resolvedFilePath)));
                Gson gson = new Gson();
                Type listType = new TypeToken<ArrayList<ResolvedPrediction>>() {}.getType();
                loadedResolvedPredictions = gson.fromJson(json, listType);

                // Initialize a new UserStatistics
                UserDescriptiveStatistics userStatistics = new UserDescriptiveStatistics();

                // For each resolved prediction within the file gather correct, incorrect, and total prediction counts
                for (ResolvedPrediction resolvedPrediction : loadedResolvedPredictions) {
                    if (resolvedPrediction.getResolution()) {
                        userStatistics.setCorrectPredictions(userStatistics.getCorrectPredictions() + 1);
                        userStatistics.setPredictionCount(userStatistics.getPredictionCount() + 1);
                    } else {
                        userStatistics.setIncorrectPredictions(userStatistics.getIncorrectPredictions() + 1);
                        userStatistics.setPredictionCount(userStatistics.getPredictionCount() + 1);
                    }
                }

                // Calculate the percentage of correct and incorrect predictions
                double percentCorrect = ((double) userStatistics.getCorrectPredictions() / userStatistics.getPredictionCount()) * 100.0;
                double percentIncorrect = 100.0 - percentCorrect;

                // Format the percentage values to two decimal places
                DecimalFormat df = new DecimalFormat("0.00");
                userStatistics.setPercentCorrect(Double.parseDouble(df.format(percentCorrect)));
                userStatistics.setPercentIncorrect(Double.parseDouble(df.format(percentIncorrect)));

                // Save the user statistics
                String userDescriptiveStatisticsFilePath = userDescriptiveStatisticsFolderPath + File.separator + userIdentifier + "_user_descriptive_statistics.json";
                try (FileWriter writer = new FileWriter(userDescriptiveStatisticsFilePath)) {
                    Gson gson2 = new Gson();
                    Type listType2 = new TypeToken<UserDescriptiveStatistics>() {}.getType();
                    String json2 = gson2.toJson(userStatistics, listType2);
                    writer.write(json2);
                    //System.out.println("UserInfo.User descriptive statistics saved successfully.");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // calculateAndSaveUserDescriptiveStatisticsMongoDB
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Calculates the given userIdentifier's user statistics by reading each of the resolved prediction
    // resolutions and dividing the number of correct predictions by the total number of resolved
    // predictions and then subtracting that from 1 to get the percent of incorrect predictions before
    // saving the data to MongoDB.
    //
    public static void calculateAndSaveUserDescriptiveStatisticsMongoDB(String userIdentifier) {
        // Initialize an array list of resolved predictions to store the user's resolved predictions
        ArrayList<ResolvedPrediction> userResolvedPredictions = MongoDBEnvisionaryUsers.retrieveUserResolvedPredictions(userIdentifier);

        // Initialize a new UserStatistics
        UserDescriptiveStatistics userStatistics = new UserDescriptiveStatistics();

        // For each resolved prediction within the file gather correct, incorrect, and total prediction counts
        for (ResolvedPrediction resolvedPrediction : userResolvedPredictions) {
            if (resolvedPrediction.getResolution()) {
                userStatistics.setCorrectPredictions(userStatistics.getCorrectPredictions() + 1);
                userStatistics.setPredictionCount(userStatistics.getPredictionCount() + 1);
            } else {
                userStatistics.setIncorrectPredictions(userStatistics.getIncorrectPredictions() + 1);
                userStatistics.setPredictionCount(userStatistics.getPredictionCount() + 1);
            }
        }

        // Calculate the percentage of correct and incorrect predictions
        double percentCorrect = ((double) userStatistics.getCorrectPredictions() / userStatistics.getPredictionCount()) * 100.0;
        double percentIncorrect = 100.0 - percentCorrect;

        // Format the percentage values to two decimal places
        DecimalFormat df = new DecimalFormat("0.00");
        userStatistics.setPercentCorrect(Double.parseDouble(df.format(percentCorrect)));
        userStatistics.setPercentIncorrect(Double.parseDouble(df.format(percentIncorrect)));

        // Save the user statistics
        MongoDBEnvisionaryUsers.updateUserDescriptiveStatistics(userIdentifier, userStatistics);
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // loadAndDisplayUserDescriptiveStatistics
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Loads the given user identifier's user descriptive statistics file and displays the data to the console.
    //
    public static void loadAndDisplayUserDescriptiveStatistics(String userIdentifier) {
        // Initialize the resolvedFilePath using the userIdentifier parameter
        String userStatisticsFilePath = userDescriptiveStatisticsFolderPath + File.separator + userIdentifier + "_user_descriptive_statistics.json";
        File userStatisticsFile = new File(userStatisticsFilePath);

        // Check if the file exists before attempting to load it
        if (userStatisticsFile.exists()) {
            // Load the file and print
            try {
                String json = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(userStatisticsFilePath)));
                Gson gson = new Gson();
                Type listType = new TypeToken<UserDescriptiveStatistics>() {}.getType();
                UserDescriptiveStatistics loadedUserStatistics = gson.fromJson(json, listType);
                System.out.println("====================================================================================================");
                System.out.println("                                 " + userIdentifier + "'s UserInfo.User Descriptive Statistics");
                loadedUserStatistics.printUserDescriptiveStatistics();
                System.out.println();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("ERROR - UserInfo.User statistics file does not exist for user: " + userIdentifier + "\n");
        }
    }
}
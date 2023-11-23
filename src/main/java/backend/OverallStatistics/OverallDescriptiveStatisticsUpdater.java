package backend.OverallStatistics;

import backend.UserStatistics.UserDescriptiveStatistics;
import backend.UserInfo.EnvisionaryUser;
import backend.UserInfo.MongoDBEnvisionaryUsers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
// OverallStatistics.OverallDescriptiveStatisticsUpdater class - Written by Brandon LaPointe
//■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
// Initializes the file path and folders needed to save the OverallStatistics.OverallDescriptiveStatistics data. Allows
// for calculation and saving of the overall descriptive statistics and the loading and displaying of
// the overall descriptive statistics file to the console.
//
public class OverallDescriptiveStatisticsUpdater {
    private static final String userHome = System.getProperty("user.home");
    private static final String userDescriptiveStatisticsFolderPath = userHome + File.separator + "EnvisionaryApp" + File.separator + "UserStatistics" + File.separator + "Descriptive";
    private static final String overallStatisticsFolderPath = userHome + File.separator + "EnvisionaryApp" + File.separator + "OverallStatistics";

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // initializeFilePath
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Initializes the file path and folders needed to save the overall descriptive statistics.
    //
    public static void initializeFilePath() {
        // Create the directory if it doesn't exist
        File directory = new File(overallStatisticsFolderPath);
        if (!directory.exists()) {
            if (directory.mkdirs()) {
                System.out.println("Created directory: " + overallStatisticsFolderPath);
            } else {
                System.err.println("Failed to create directory: " + overallStatisticsFolderPath);
            }
        }
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // calculateAndSaveOverallStatistics
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Calculates the overall statistics by going through each user's user statistics file and collecting
    // values of the min correct percent, max correct percent, and total numbers of correct predictions
    // and predictions overall before saving to the specified file path.
    //
    public static void calculateAndSaveOverallDescriptiveStatistics() {
        // Initialize userStatisticsFilesFolder as a new File
        File userStatisticsFilesFolder = new File(userDescriptiveStatisticsFolderPath);

        // If the prediction files folder exists and is a directory
        if (userStatisticsFilesFolder.exists() && userStatisticsFilesFolder.isDirectory()) {
            // Initialize the file array of user statistics files
            File[] userStatisticsFiles = userStatisticsFilesFolder.listFiles();

            // Flag to check if there are valid files
            boolean hasValidFiles = false;

            // If the file array is not null
            if (userStatisticsFiles != null) {
                // Initialize a GsonBuilder with support for special floating-point values
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.serializeSpecialFloatingPointValues();
                Gson gson = gsonBuilder.create();

                // Initialize new OverallStatistics object
                OverallDescriptiveStatistics overallDescriptiveStatistics = new OverallDescriptiveStatistics();
                overallDescriptiveStatistics.setOverallPredictionCount(0);
                overallDescriptiveStatistics.setOverallCorrectPredictions(0);
                overallDescriptiveStatistics.setOverallIncorrectPredictions(0);
                overallDescriptiveStatistics.setOverallPercentCorrect(0);
                overallDescriptiveStatistics.setOverallPercentIncorrect(0);
                overallDescriptiveStatistics.setMinPercentCorrect(100);
                overallDescriptiveStatistics.setMaxPercentCorrect(0);

                // Set the decimal format for saving percentage calculations at 2 decimal places
                DecimalFormat df = new DecimalFormat("0.00");

                // Create a list to store the percentages of correct predictions for all users
                ArrayList<Double> correctPercentages = new ArrayList<>();

                // Initialize overall counters
                int overallPredictionCount = 0;
                int overallCorrectPredictionCount = 0;

                // For each user statistics file within the user statistics folder
                for (File userStatisticsFile : userStatisticsFiles) {
                    // Load the file
                    try {
                        String json = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(userStatisticsFile.getAbsolutePath())));

                        // Use the Gson instance created with the GsonBuilder
                        Type listType = new TypeToken<UserDescriptiveStatistics>() {}.getType();
                        UserDescriptiveStatistics loadedUserStatistics = gson.fromJson(json, listType);

                        // Check if user's percentage of correct predictions is less than the current min
                        if (loadedUserStatistics.getPercentCorrect() < overallDescriptiveStatistics.getMinPercentCorrect()) {
                            overallDescriptiveStatistics.setMinPercentCorrect(loadedUserStatistics.getPercentCorrect());
                        }

                        // Check if user's percentage of correct predictions is greater than the current max
                        if (loadedUserStatistics.getPercentCorrect() > overallDescriptiveStatistics.getMaxPercentCorrect()) {
                            overallDescriptiveStatistics.setMaxPercentCorrect(loadedUserStatistics.getPercentCorrect());
                        }

                        // Add user's prediction count to overall prediction count
                        overallPredictionCount += loadedUserStatistics.getPredictionCount();

                        // Add user's correct prediction count to overall correct prediction count
                        overallCorrectPredictionCount += loadedUserStatistics.getCorrectPredictions();

                        // Calculate percentage of user's correct predictions
                        double userCorrectPercentage = (double) loadedUserStatistics.getCorrectPredictions() / loadedUserStatistics.getPredictionCount() * 100.0;
                        correctPercentages.add(userCorrectPercentage);

                        // Set the flag to indicate there are valid files
                        hasValidFiles = true;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                // Calculate the mean (average) of correct percentages
                double sum = 0;
                for (double percentage : correctPercentages) {
                    sum += percentage;
                }
                double meanCorrectPercentage = sum / correctPercentages.size();

                // Calculate the median of correct percentages
                correctPercentages.sort(null);

                double medianCorrectPercentage;
                int size = correctPercentages.size();

                if (size % 2 == 0) {
                    // Even number of elements
                    int middle1 = size / 2;
                    int middle2 = middle1 - 1;
                    medianCorrectPercentage = (correctPercentages.get(middle1) + correctPercentages.get(middle2)) / 2.0;
                } else {
                    // Odd number of elements
                    medianCorrectPercentage = correctPercentages.get(size / 2);
                }

                // Calculate the mode of correct percentages
                // Create a map to store the frequency of each correct percentage
                Map<Double, Integer> frequencyMap = new HashMap<>();

                // Calculate the frequency of each correct percentage
                for (double percentage : correctPercentages) {
                    frequencyMap.put(percentage, frequencyMap.getOrDefault(percentage, 0) + 1);
                }

                // Initialize an array list to store mode(s)
                ArrayList<Double> modes = new ArrayList<>();

                // Find the mode (correct percentages with the highest frequency)
                int maxFrequency = 0;

                for (Map.Entry<Double, Integer> entry : frequencyMap.entrySet()) {
                    if (entry.getValue() > maxFrequency) {
                        maxFrequency = entry.getValue();
                        modes.clear(); // Clear previous modes
                        modes.add(entry.getKey()); // Set the new mode
                    } else if (entry.getValue() == maxFrequency) {
                        modes.add(entry.getKey()); // Add to modes if it has the same frequency as maxFrequency
                    }
                }

                // If all elements occur an equal amount of times
                if (modes.size() == frequencyMap.size()) {
                    modes.clear();
                    modes.add(-1.0); // Set it to -1 to indicate no distinct modes
                }

                // Format each mode in the list
                ArrayList<Double> formattedModes = new ArrayList<>();
                for (Double mode : modes) {
                    formattedModes.add(Double.parseDouble(df.format(mode)));
                }

                // Calculate the standard deviation of correct percentages
                double sumOfSquaredDifferences = 0;
                for (double percentage : correctPercentages) {
                    double diff = percentage - meanCorrectPercentage;
                    sumOfSquaredDifferences += diff * diff;
                }
                double standardDeviation = Math.sqrt(sumOfSquaredDifferences / correctPercentages.size());

                // Format the calculated values
                String formattedMeanCorrectPercentage = df.format(meanCorrectPercentage);
                String formattedStandardDeviation = df.format(standardDeviation);

                // Set the mean and standard deviation in your overall statistics object
                overallDescriptiveStatistics.setMeanCorrectPercentage(Double.parseDouble(formattedMeanCorrectPercentage));
                overallDescriptiveStatistics.setStandardDeviation(Double.parseDouble(formattedStandardDeviation));

                // Set the mode value as the formatted array list if it exists
                if (!formattedModes.isEmpty() && !formattedModes.get(0).equals(-1.0)) {
                    overallDescriptiveStatistics.setModeCorrectPercentage(formattedModes);
                } else {
                    ArrayList<Double> noModes = new ArrayList<>();
                    noModes.add(Double.NaN);
                    overallDescriptiveStatistics.setModeCorrectPercentage(noModes);
                }

                // Format and set the median value to two decimal places
                overallDescriptiveStatistics.setMedianCorrectPercentage(Double.parseDouble(df.format(medianCorrectPercentage)));

                // Set overall prediction count
                overallDescriptiveStatistics.setOverallPredictionCount(overallPredictionCount);

                // Set overall correct prediction count
                overallDescriptiveStatistics.setOverallCorrectPredictions(overallCorrectPredictionCount);

                overallDescriptiveStatistics.setOverallIncorrectPredictions(overallPredictionCount - overallCorrectPredictionCount);

                // Calculate percentage of overall correct predictions (overall correct predictions count / overall prediction count)
                double overallPercentCorrect = (double) overallCorrectPredictionCount / overallPredictionCount * 100.0;
                String formattedOverallPercentCorrect = df.format(overallPercentCorrect);
                overallDescriptiveStatistics.setOverallPercentCorrect(Double.parseDouble(formattedOverallPercentCorrect));

                // Calculate percentage of incorrect overall predictions
                double overallPercentIncorrect = (double) (overallPredictionCount - overallCorrectPredictionCount) / overallPredictionCount * 100.0;
                String formattedOverallPercentIncorrect = df.format(overallPercentIncorrect);
                overallDescriptiveStatistics.setOverallPercentIncorrect(Double.parseDouble(formattedOverallPercentIncorrect));

                // Save overall statistics only if there are valid files
                if (hasValidFiles) {
                    String overallStatisticsFilePath = overallStatisticsFolderPath + File.separator + "overall_descriptive_statistics.json";
                    try (FileWriter writer = new FileWriter(overallStatisticsFilePath)) {
                        Gson gson2 = gsonBuilder.create();
                        Type listType2 = new TypeToken<OverallDescriptiveStatistics>() {}.getType();
                        String json2 = gson2.toJson(overallDescriptiveStatistics, listType2);
                        writer.write(json2);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("ERROR - UserInfo.User statistics folder is empty - Resolve a prediction to initialize.");
                }
            } else {
                System.out.println("ERROR - UserInfo.User statistics file is null - Resolve a prediction to initialize.");
            }
        }
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // calculateAndSaveOverallStatisticsMongoDB
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Calculates the overall statistics by going through each user's user statistics file and collecting
    // values of the min correct percent, max correct percent, and total numbers of correct predictions
    // and predictions overall before saving to MongoDB.
    //
    public static void calculateAndSaveOverallDescriptiveStatisticsMongoDB() {
        // Retrieve collection of EnvisionaryUsers
        ArrayList<EnvisionaryUser> envisionaryUsers = MongoDBEnvisionaryUsers.retrieveCollection();

        // Initialize new OverallStatistics object
        OverallDescriptiveStatistics overallDescriptiveStatistics = new OverallDescriptiveStatistics();
        overallDescriptiveStatistics.setOverallPredictionCount(0);
        overallDescriptiveStatistics.setOverallCorrectPredictions(0);
        overallDescriptiveStatistics.setOverallIncorrectPredictions(0);
        overallDescriptiveStatistics.setOverallPercentCorrect(0);
        overallDescriptiveStatistics.setOverallPercentIncorrect(0);
        overallDescriptiveStatistics.setMinPercentCorrect(100);
        overallDescriptiveStatistics.setMaxPercentCorrect(0);

        // Set the decimal format for saving percentage calculations at 2 decimal places
        DecimalFormat df = new DecimalFormat("0.00");

        // Create a list to store the percentages of correct predictions for all users
        ArrayList<Double> correctPercentages = new ArrayList<>();

        // Initialize overall counters
        int overallPredictionCount = 0;
        int overallCorrectPredictionCount = 0;

        for (EnvisionaryUser user : envisionaryUsers) {
            // Load the user's descriptive statistics
            UserDescriptiveStatistics loadedUserDescriptiveStatistics = user.getUserDescriptiveStatistics();

            // Check if user's percentage of correct predictions is less than the current min
            if (loadedUserDescriptiveStatistics.getPercentCorrect() < overallDescriptiveStatistics.getMinPercentCorrect()) {
                overallDescriptiveStatistics.setMinPercentCorrect(loadedUserDescriptiveStatistics.getPercentCorrect());
            }

            // Check if user's percentage of correct predictions is greater than the current max
            if (loadedUserDescriptiveStatistics.getPercentCorrect() > overallDescriptiveStatistics.getMaxPercentCorrect()) {
                overallDescriptiveStatistics.setMaxPercentCorrect(loadedUserDescriptiveStatistics.getPercentCorrect());
            }

            // Add user's prediction count to overall prediction count
            overallPredictionCount += loadedUserDescriptiveStatistics.getPredictionCount();

            // Add user's correct prediction count to overall correct prediction count
            overallCorrectPredictionCount += loadedUserDescriptiveStatistics.getCorrectPredictions();

            // Calculate percentage of user's correct predictions
            double userCorrectPercentage = (double) loadedUserDescriptiveStatistics.getCorrectPredictions() / loadedUserDescriptiveStatistics.getPredictionCount() * 100.0;
            correctPercentages.add(userCorrectPercentage);
        }

        // Calculate the mean (average) of correct percentages
        double sum = 0;
        for (double percentage : correctPercentages) {
            sum += percentage;
        }
        double meanCorrectPercentage = sum / correctPercentages.size();

        // Calculate the median of correct percentages
        correctPercentages.sort(null);

        double medianCorrectPercentage;
        int size = correctPercentages.size();

        if (size % 2 == 0) {
            // Even number of elements
            int middle1 = size / 2;
            int middle2 = middle1 - 1;
            medianCorrectPercentage = (correctPercentages.get(middle1) + correctPercentages.get(middle2)) / 2.0;
        } else {
            // Odd number of elements
            medianCorrectPercentage = correctPercentages.get(size / 2);
        }

        // Calculate the mode of correct percentages
        // Create a map to store the frequency of each correct percentage
        Map<Double, Integer> frequencyMap = new HashMap<>();

        // Calculate the frequency of each correct percentage
        for (double percentage : correctPercentages) {
            frequencyMap.put(percentage, frequencyMap.getOrDefault(percentage, 0) + 1);
        }

        // Initialize an array list to store mode(s)
        ArrayList<Double> modes = new ArrayList<>();

        // Find the mode (correct percentages with the highest frequency)
        int maxFrequency = 0;

        for (Map.Entry<Double, Integer> entry : frequencyMap.entrySet()) {
            if (entry.getValue() > maxFrequency) {
                maxFrequency = entry.getValue();
                modes.clear(); // Clear previous modes
                modes.add(entry.getKey()); // Set the new mode
            } else if (entry.getValue() == maxFrequency) {
                modes.add(entry.getKey()); // Add to modes if it has the same frequency as maxFrequency
            }
        }

        // If all elements occur an equal amount of times
        if (modes.size() == frequencyMap.size()) {
            modes.clear();
            modes.add(-1.0); // Set it to -1 to indicate no distinct modes
        }

        // Format each mode in the list
        ArrayList<Double> formattedModes = new ArrayList<>();
        for (Double mode : modes) {
            formattedModes.add(Double.parseDouble(df.format(mode)));
        }

        // Calculate the standard deviation of correct percentages
        double sumOfSquaredDifferences = 0;
        for (double percentage : correctPercentages) {
            double diff = percentage - meanCorrectPercentage;
            sumOfSquaredDifferences += diff * diff;
        }
        double standardDeviation = Math.sqrt(sumOfSquaredDifferences / correctPercentages.size());

        // Format the calculated values
        String formattedMeanCorrectPercentage = df.format(meanCorrectPercentage);
        String formattedStandardDeviation = df.format(standardDeviation);

        // Set the mean and standard deviation in your overall statistics object
        overallDescriptiveStatistics.setMeanCorrectPercentage(Double.parseDouble(formattedMeanCorrectPercentage));
        overallDescriptiveStatistics.setStandardDeviation(Double.parseDouble(formattedStandardDeviation));

        // Set the mode value as the formatted array list if it exists
        if (!formattedModes.isEmpty() && !formattedModes.get(0).equals(-1.0)) {
            overallDescriptiveStatistics.setModeCorrectPercentage(formattedModes);
        } else {
            ArrayList<Double> noModes = new ArrayList<>();
            noModes.add(Double.NaN);
            overallDescriptiveStatistics.setModeCorrectPercentage(noModes);
        }

        // Format and set the median value to two decimal places
        overallDescriptiveStatistics.setMedianCorrectPercentage(Double.parseDouble(df.format(medianCorrectPercentage)));

        // Set overall prediction count
        overallDescriptiveStatistics.setOverallPredictionCount(overallPredictionCount);

        // Set overall correct prediction count
        overallDescriptiveStatistics.setOverallCorrectPredictions(overallCorrectPredictionCount);

        // Set overall incorrect prediction count
        overallDescriptiveStatistics.setOverallIncorrectPredictions(overallPredictionCount - overallCorrectPredictionCount);

        // Calculate percentage of overall correct predictions (overall correct predictions count / overall prediction count)
        double overallPercentCorrect = (double) overallCorrectPredictionCount / overallPredictionCount * 100.0;
        String formattedOverallPercentCorrect = df.format(overallPercentCorrect);
        overallDescriptiveStatistics.setOverallPercentCorrect(Double.parseDouble(formattedOverallPercentCorrect));

        // Calculate percentage of incorrect overall predictions
        double overallPercentIncorrect = (double) (overallPredictionCount - overallCorrectPredictionCount) / overallPredictionCount * 100.0;
        String formattedOverallPercentIncorrect = df.format(overallPercentIncorrect);
        overallDescriptiveStatistics.setOverallPercentIncorrect(Double.parseDouble(formattedOverallPercentIncorrect));

        // Save overall statistics only if there are valid files
        MongoDBOverallDescriptiveStatistics.updateDocument(overallDescriptiveStatistics);
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // loadAndDisplayOverallDescriptiveStatistics
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Loads the overall descriptive user statistics file and displays to the console.
    //
    public static void loadAndDisplayOverallDescriptiveStatistics() {
        // Initialize the overallStatisticsFilePath
        String overallStatisticsFilePath = overallStatisticsFolderPath + File.separator + "overall_descriptive_statistics.json";
        File overallStatisticsFile = new File(overallStatisticsFilePath);

        // Check if the file exists before attempting to load it
        if (overallStatisticsFile.exists()) {
            // Load the file and print
            try {
                String json = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(overallStatisticsFilePath)));
                Gson gson = new Gson();
                Type listType = new TypeToken<OverallDescriptiveStatistics>() {}.getType();
                OverallDescriptiveStatistics loadedOverallStatistics = gson.fromJson(json, listType);
                // Check if loadedOverallStatistics is not null before printing
                if (loadedOverallStatistics != null) {
                    System.out.println("====================================================================================================");
                    System.out.println("                                 Overall Descriptive Statistics");
                    loadedOverallStatistics.printOverallDescriptiveStatistics();
                    System.out.println();
                } else {
                    System.out.println("ERROR - Loaded Overall Statistics is empty - Resolve a prediction to initialize.");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("ERROR - Overall Statistics file does not exist - Resolve a prediction to initialize");
        }
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // loadOverallDescriptiveStatistics
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Loads the overall descriptive user statistics file and displays to the console.
    //
    public static OverallDescriptiveStatistics loadOverallDescriptiveStatistics() {
        // Initialize the overallStatisticsFilePath
        String overallStatisticsFilePath = overallStatisticsFolderPath + File.separator + "overall_descriptive_statistics.json";
        File overallStatisticsFile = new File(overallStatisticsFilePath);

        // Check if the file exists before attempting to load it
        if (overallStatisticsFile.exists()) {
            // Load the file and print
            try {
                String json = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(overallStatisticsFilePath)));
                Gson gson = new Gson();
                Type listType = new TypeToken<OverallDescriptiveStatistics>() {}.getType();
                OverallDescriptiveStatistics loadedOverallStatistics = gson.fromJson(json, listType);
                // Check if loadedOverallStatistics is not null before printing
                if (loadedOverallStatistics != null) {
                    return loadedOverallStatistics;
                } else {
                    System.out.println("ERROR - Loaded Overall Statistics is empty - Resolve a prediction to initialize.");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("ERROR - Overall Statistics file does not exist - Resolve a prediction to initialize");
        }
        return null;
    }
}
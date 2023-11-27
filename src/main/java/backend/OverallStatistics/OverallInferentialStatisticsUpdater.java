package backend.OverallStatistics;

import backend.UserStatistics.UserInferentialStatistics;
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
// OverallStatistics.OverallInferentialStatisticsUpdater class - Written by Brandon LaPointe
//■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
// Initializes the file path and folders needed to save the OverallStatistics.OverallInferentialStatistics data. Allows
// for calculation and saving of the overall inferential statistics and the loading and displaying of
// the overall inferential statistics file to the console.
//
public class OverallInferentialStatisticsUpdater {
    private static final String userHome = System.getProperty("user.home");
    private static final String userInferentialStatisticsFolderPath = userHome + File.separator + "EnvisionaryApp" + File.separator + "UserStatistics" + File.separator + "Inferential";
    private static final String overallStatisticsFolderPath = userHome + File.separator + "EnvisionaryApp" + File.separator + "OverallStatistics";

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // initializeFilePath
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Initializes the file path and folders needed to save the overall inferential statistics.
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
    // calculateAndSaveOverallInferentialStatistics
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Calculates the overall inferential statistics by going through each user's inferential statistics
    // file and collecting values of the min correct percent, max correct percent, and total numbers of
    // correct predictions and predictions overall before saving to the specified file path.
    //
    public static void calculateAndSaveOverallInferentialStatistics() {
        // Initialize userInferentialStatisticsFilesFolder as a new File
        File userInferentialStatisticsFilesFolder = new File(userInferentialStatisticsFolderPath);

        // If the prediction files folder exists and is a directory
        if (userInferentialStatisticsFilesFolder.exists() && userInferentialStatisticsFilesFolder.isDirectory()) {
            // Initialize the file array of user inferential statistics files
            File[] userInferentialStatisticsFiles = userInferentialStatisticsFilesFolder.listFiles();

            // Flag to check if there are valid files
            boolean hasValidFiles = false;

            // If the file array is not null
            if (userInferentialStatisticsFiles != null) {
                // Initialize a GsonBuilder with support for special floating-point values
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.serializeSpecialFloatingPointValues();
                Gson gson = gsonBuilder.create();

                // Initialize new OverallStatistics.OverallInferentialStatistics object
                OverallInferentialStatistics overallInferentialStatistics = new OverallInferentialStatistics();
                overallInferentialStatistics.setMaxPercentCorrectCustom(-7.7);
                overallInferentialStatistics.setMaxPercentCorrectSports(-7.7);
                overallInferentialStatistics.setMaxPercentCorrectScience(-7.7);
                overallInferentialStatistics.setMaxPercentCorrectWeather(-7.7);
                overallInferentialStatistics.setMaxPercentCorrectEntertainment(-7.7);
                overallInferentialStatistics.setMinPercentCorrectCustom(777.777);
                overallInferentialStatistics.setMinPercentCorrectSports(777.777);
                overallInferentialStatistics.setMinPercentCorrectScience(777.777);
                overallInferentialStatistics.setMinPercentCorrectWeather(777.777);
                overallInferentialStatistics.setMinPercentCorrectEntertainment(777.777);

                // Set the decimal format for saving percentage calculations at 2 decimal places
                DecimalFormat df = new DecimalFormat("0.00");

                // Create lists to store each of the category percentages of correct predictions for all users
                ArrayList<Double> correctCustomPercentages = new ArrayList<>();
                ArrayList<Double> correctSportsPercentages = new ArrayList<>();
                ArrayList<Double> correctSciencePercentages = new ArrayList<>();
                ArrayList<Double> correctWeatherPercentages = new ArrayList<>();
                ArrayList<Double> correctEntertainmentPercentages = new ArrayList<>();

                // Initialize overall counters
                int overallCustomPredictionCount = 0;
                int overallSportsPredictionCount = 0;
                int overallSciencePredictionCount = 0;
                int overallWeatherPredictionCount = 0;
                int overallEntertainmentPredictionCount = 0;
                int overallCustomCorrectPredictionCount = 0;
                int overallSportsCorrectPredictionCount = 0;
                int overallScienceCorrectPredictionCount = 0;
                int overallWeatherCorrectPredictionCount = 0;
                int overallEntertainmentCorrectPredictionCount = 0;

                // For each user statistics file within the user inferential statistics folder
                for (File userInferentialStatisticsFile : userInferentialStatisticsFiles) {
                    // Load the file
                    try {
                        String json = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(userInferentialStatisticsFile.getAbsolutePath())));

                        // Use the Gson instance created with the GsonBuilder
                        Type listType = new TypeToken<UserInferentialStatistics>() {}.getType();
                        UserInferentialStatistics loadedUserInferentialStatistics = gson.fromJson(json, listType);

                        // Check if user's percentage of correct custom predictions is less than the current custom min
                        if (loadedUserInferentialStatistics.getCustomPredictionCount() > 0 && loadedUserInferentialStatistics.getCustomPercentCorrect() < overallInferentialStatistics.getMinPercentCorrectCustom()) {
                            overallInferentialStatistics.setMinPercentCorrectCustom(loadedUserInferentialStatistics.getCustomPercentCorrect());
                        }
                        // Check if user's percentage of correct sports predictions is less than the current sports min
                        if (loadedUserInferentialStatistics.getSportsPredictionCount() > 0 && loadedUserInferentialStatistics.getSportsPercentCorrect() < overallInferentialStatistics.getMinPercentCorrectSports()) {
                            overallInferentialStatistics.setMinPercentCorrectSports(loadedUserInferentialStatistics.getSportsPercentCorrect());
                        }
                        // Check if user's percentage of correct science predictions is less than the current science min
                        if (loadedUserInferentialStatistics.getSciencePredictionCount() > 0 && loadedUserInferentialStatistics.getSciencePercentCorrect() < overallInferentialStatistics.getMinPercentCorrectScience()) {
                            overallInferentialStatistics.setMinPercentCorrectScience(loadedUserInferentialStatistics.getSciencePercentCorrect());
                        }
                        // Check if user's percentage of correct weather predictions is less than the current weather min
                        if (loadedUserInferentialStatistics.getWeatherPredictionCount() > 0 && loadedUserInferentialStatistics.getWeatherPercentCorrect() < overallInferentialStatistics.getMinPercentCorrectWeather()) {
                            overallInferentialStatistics.setMinPercentCorrectWeather(loadedUserInferentialStatistics.getWeatherPercentCorrect());
                        }
                        // Check if user's percentage of correct entertainment predictions is less than the current entertainment min
                        if (loadedUserInferentialStatistics.getEntertainmentPredictionCount() > 0 && loadedUserInferentialStatistics.getEntertainmentPercentCorrect() < overallInferentialStatistics.getMinPercentCorrectEntertainment()) {
                            overallInferentialStatistics.setMinPercentCorrectEntertainment(loadedUserInferentialStatistics.getEntertainmentPercentCorrect());
                        }

                        // Check if user's percentage of correct custom predictions is greater than the current custom max
                        if (loadedUserInferentialStatistics.getCustomPredictionCount() > 0 && loadedUserInferentialStatistics.getCustomPercentCorrect() > overallInferentialStatistics.getMaxPercentCorrectCustom()) {
                            overallInferentialStatistics.setMaxPercentCorrectCustom(loadedUserInferentialStatistics.getCustomPercentCorrect());
                        }
                        // Check if user's percentage of correct sports predictions is greater than the current sports max
                        if (loadedUserInferentialStatistics.getSportsPredictionCount() > 0 && loadedUserInferentialStatistics.getSportsPercentCorrect() > overallInferentialStatistics.getMaxPercentCorrectSports()) {
                            overallInferentialStatistics.setMaxPercentCorrectSports(loadedUserInferentialStatistics.getSportsPercentCorrect());
                        }
                        // Check if user's percentage of correct science predictions is greater than the current science max
                        if (loadedUserInferentialStatistics.getSciencePredictionCount() > 0 && loadedUserInferentialStatistics.getSciencePercentCorrect() > overallInferentialStatistics.getMaxPercentCorrectScience()) {
                            overallInferentialStatistics.setMaxPercentCorrectScience(loadedUserInferentialStatistics.getSciencePercentCorrect());
                        }
                        // Check if user's percentage of correct weather predictions is greater than the current weather max
                        if (loadedUserInferentialStatistics.getWeatherPredictionCount() > 0 && loadedUserInferentialStatistics.getWeatherPercentCorrect() > overallInferentialStatistics.getMaxPercentCorrectWeather()) {
                            overallInferentialStatistics.setMaxPercentCorrectWeather(loadedUserInferentialStatistics.getWeatherPercentCorrect());
                        }
                        // Check if user's percentage of correct entertainment predictions is greater than the current entertainment max
                        if (loadedUserInferentialStatistics.getEntertainmentPredictionCount() > 0 && loadedUserInferentialStatistics.getEntertainmentPercentCorrect() > overallInferentialStatistics.getMaxPercentCorrectEntertainment()) {
                            overallInferentialStatistics.setMaxPercentCorrectEntertainment(loadedUserInferentialStatistics.getEntertainmentPercentCorrect());
                        }

                        // Add user's prediction counts to overall category prediction counts
                        overallCustomPredictionCount += loadedUserInferentialStatistics.getCustomPredictionCount();
                        overallSportsPredictionCount += loadedUserInferentialStatistics.getSportsPredictionCount();
                        overallSciencePredictionCount += loadedUserInferentialStatistics.getSciencePredictionCount();
                        overallWeatherPredictionCount += loadedUserInferentialStatistics.getWeatherPredictionCount();
                        overallEntertainmentPredictionCount += loadedUserInferentialStatistics.getEntertainmentPredictionCount();

                        // Add user's correct prediction count to overall correct prediction count
                        overallCustomCorrectPredictionCount += loadedUserInferentialStatistics.getCustomCorrectPredictions();
                        overallSportsCorrectPredictionCount += loadedUserInferentialStatistics.getSportsCorrectPredictions();
                        overallScienceCorrectPredictionCount += loadedUserInferentialStatistics.getScienceCorrectPredictions();
                        overallWeatherCorrectPredictionCount += loadedUserInferentialStatistics.getWeatherCorrectPredictions();
                        overallEntertainmentCorrectPredictionCount += loadedUserInferentialStatistics.getEntertainmentCorrectPredictions();

                        // Calculate percentages for each category of user's correct predictions
                        Double userCorrectCustomPercentage = (double) loadedUserInferentialStatistics.getCustomCorrectPredictions() / loadedUserInferentialStatistics.getCustomPredictionCount() * 100.0;
                        if (!userCorrectCustomPercentage.isNaN())
                            correctCustomPercentages.add(userCorrectCustomPercentage);
                        Double userCorrectSportsPercentage = (double) loadedUserInferentialStatistics.getSportsCorrectPredictions() / loadedUserInferentialStatistics.getSportsPredictionCount() * 100.0;
                        if (!userCorrectSportsPercentage.isNaN())
                            correctSportsPercentages.add(userCorrectSportsPercentage);
                        Double userCorrectSciencePercentage = (double) loadedUserInferentialStatistics.getScienceCorrectPredictions() / loadedUserInferentialStatistics.getSciencePredictionCount() * 100.0;
                        if (!userCorrectSciencePercentage.isNaN())
                            correctSciencePercentages.add(userCorrectSciencePercentage);
                        Double userCorrectWeatherPercentage = (double) loadedUserInferentialStatistics.getWeatherCorrectPredictions() / loadedUserInferentialStatistics.getWeatherPredictionCount() * 100.0;
                        if (!userCorrectWeatherPercentage.isNaN())
                            correctWeatherPercentages.add(userCorrectWeatherPercentage);
                        Double userCorrectEntertainmentPercentage = (double) loadedUserInferentialStatistics.getEntertainmentCorrectPredictions() / loadedUserInferentialStatistics.getEntertainmentPredictionCount() * 100.0;
                        if (!userCorrectEntertainmentPercentage.isNaN())
                            correctEntertainmentPercentages.add(userCorrectEntertainmentPercentage);

                        // Add each category prediction count to the total predictions made count of the overall inferential statistics
                        overallInferentialStatistics.setPredictionsMadeCount(overallInferentialStatistics.getPredictionsMadeCount() + loadedUserInferentialStatistics.getCustomPredictionCount());
                        overallInferentialStatistics.setPredictionsMadeCount(overallInferentialStatistics.getPredictionsMadeCount() + loadedUserInferentialStatistics.getSportsPredictionCount());
                        overallInferentialStatistics.setPredictionsMadeCount(overallInferentialStatistics.getPredictionsMadeCount() + loadedUserInferentialStatistics.getSciencePredictionCount());
                        overallInferentialStatistics.setPredictionsMadeCount(overallInferentialStatistics.getPredictionsMadeCount() + loadedUserInferentialStatistics.getWeatherPredictionCount());
                        overallInferentialStatistics.setPredictionsMadeCount(overallInferentialStatistics.getPredictionsMadeCount() + loadedUserInferentialStatistics.getEntertainmentPredictionCount());

                        // Set the flag to indicate there are valid files
                        hasValidFiles = true;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                // If minimum percent correct is still set to initial value, set to NaN
                if (overallInferentialStatistics.getMinPercentCorrectCustom() == 777.777) {
                    overallInferentialStatistics.setMinPercentCorrectCustom(Double.NaN);
                }
                if (overallInferentialStatistics.getMinPercentCorrectSports() == 777.777) {
                    overallInferentialStatistics.setMinPercentCorrectSports(Double.NaN);
                }
                if (overallInferentialStatistics.getMinPercentCorrectScience() == 777.777) {
                    overallInferentialStatistics.setMinPercentCorrectScience(Double.NaN);
                }
                if (overallInferentialStatistics.getMinPercentCorrectWeather() == 777.777) {
                    overallInferentialStatistics.setMinPercentCorrectWeather(Double.NaN);
                }
                if (overallInferentialStatistics.getMinPercentCorrectEntertainment() == 777.777) {
                    overallInferentialStatistics.setMinPercentCorrectEntertainment(Double.NaN);
                }

                // If maximum percent correct is still set to initial value, set to NaN
                if (overallInferentialStatistics.getMaxPercentCorrectCustom() == -7.7) {
                    overallInferentialStatistics.setMaxPercentCorrectCustom(Double.NaN);
                }
                if (overallInferentialStatistics.getMaxPercentCorrectSports() == -7.7) {
                    overallInferentialStatistics.setMaxPercentCorrectSports(Double.NaN);
                }
                if (overallInferentialStatistics.getMaxPercentCorrectScience() == -7.7) {
                    overallInferentialStatistics.setMaxPercentCorrectScience(Double.NaN);
                }
                if (overallInferentialStatistics.getMaxPercentCorrectWeather() == -7.7) {
                    overallInferentialStatistics.setMaxPercentCorrectWeather(Double.NaN);
                }
                if (overallInferentialStatistics.getMaxPercentCorrectEntertainment() == -7.7) {
                    overallInferentialStatistics.setMaxPercentCorrectEntertainment(Double.NaN);
                }

                // Calculate the mean (average) of correct percentages for each category
                Double sum = 0.0;
                for (Double percentage : correctCustomPercentages) {
                    sum += percentage;
                }
                Double meanCorrectCustomPercentage = sum / correctCustomPercentages.size();
                sum = 0.0;
                for (Double percentage : correctSportsPercentages) {
                    sum += percentage;
                }
                Double meanCorrectSportsPercentage = sum / correctSportsPercentages.size();
                sum = 0.0;
                for (Double percentage : correctSciencePercentages) {
                    sum += percentage;
                }
                Double meanCorrectSciencePercentage = sum / correctSciencePercentages.size();
                sum = 0.0;
                for (Double percentage : correctWeatherPercentages) {
                    sum += percentage;
                }
                Double meanCorrectWeatherPercentage = sum / correctWeatherPercentages.size();
                sum = 0.0;
                for (Double percentage : correctEntertainmentPercentages) {
                    sum += percentage;
                }
                Double meanCorrectEntertainmentPercentage = sum / correctEntertainmentPercentages.size();

                // Calculate the median of correct percentages for each category
                correctCustomPercentages.sort(null);
                double medianCustomCorrectPercentage;
                int size = correctCustomPercentages.size();
                if (size > 0) {
                    if (size % 2 == 0) {
                        // Even number of elements
                        int middle1 = size / 2;
                        int middle2 = middle1 - 1;
                        medianCustomCorrectPercentage = (correctCustomPercentages.get(middle1) + correctCustomPercentages.get(middle2)) / 2.0;
                    } else {
                        // Odd number of elements
                        medianCustomCorrectPercentage = correctCustomPercentages.get(size / 2);
                    }
                } else {
                    medianCustomCorrectPercentage = 0.0; // Set a default value or handle the case when the list is empty.
                }

                correctSportsPercentages.sort(null);
                double medianSportsCorrectPercentage;
                size = correctSportsPercentages.size();
                if (size > 0) {
                    if (size % 2 == 0) {
                        // Even number of elements
                        int middle1 = size / 2;
                        int middle2 = middle1 - 1;
                        medianSportsCorrectPercentage = (correctSportsPercentages.get(middle1) + correctSportsPercentages.get(middle2)) / 2.0;
                    } else {
                        // Odd number of elements
                        medianSportsCorrectPercentage = correctSportsPercentages.get(size / 2);
                    }
                } else {
                    medianSportsCorrectPercentage = 0.0; // Set a default value or handle the case when the list is empty.
                }

                correctSciencePercentages.sort(null);
                double medianScienceCorrectPercentage;
                size = correctSciencePercentages.size();
                if (size > 0) {
                    if (size % 2 == 0) {
                        // Even number of elements
                        int middle1 = size / 2;
                        int middle2 = middle1 - 1;
                        medianScienceCorrectPercentage = (correctSciencePercentages.get(middle1) + correctSciencePercentages.get(middle2)) / 2.0;
                    } else {
                        // Odd number of elements
                        medianScienceCorrectPercentage = correctSciencePercentages.get(size / 2);
                    }
                } else {
                    medianScienceCorrectPercentage = 0.0; // Set a default value or handle the case when the list is empty.
                }

                correctWeatherPercentages.sort(null);
                double medianWeatherCorrectPercentage;
                size = correctWeatherPercentages.size();
                if (size > 0) {
                    if (size % 2 == 0) {
                        // Even number of elements
                        int middle1 = size / 2;
                        int middle2 = middle1 - 1;
                        medianWeatherCorrectPercentage = (correctWeatherPercentages.get(middle1) + correctWeatherPercentages.get(middle2)) / 2.0;
                    } else {
                        // Odd number of elements
                        medianWeatherCorrectPercentage = correctWeatherPercentages.get(size / 2);
                    }
                } else {
                    medianWeatherCorrectPercentage = 0.0; // Set a default value or handle the case when the list is empty.
                }

                correctEntertainmentPercentages.sort(null);
                double medianEntertainmentCorrectPercentage;
                size = correctEntertainmentPercentages.size();
                if (size > 0) {
                    if (size % 2 == 0) {
                        // Even number of elements
                        int middle1 = size / 2;
                        int middle2 = middle1 - 1;
                        medianEntertainmentCorrectPercentage = (correctEntertainmentPercentages.get(middle1) + correctEntertainmentPercentages.get(middle2)) / 2.0;
                    } else {
                        // Odd number of elements
                        medianEntertainmentCorrectPercentage = correctEntertainmentPercentages.get(size / 2);
                    }
                } else {
                    medianEntertainmentCorrectPercentage = 0.0; // Set a default value or handle the case when the list is empty.
                }

                // Calculate the mode(s) of correct percentages for each category
                // Create a map to store the frequency of each correct percentage
                Map<Double, Integer> frequencyMapCustom = new HashMap<>();
                Map<Double, Integer> frequencyMapSports = new HashMap<>();
                Map<Double, Integer> frequencyMapScience = new HashMap<>();
                Map<Double, Integer> frequencyMapWeather = new HashMap<>();
                Map<Double, Integer> frequencyMapEntertainment = new HashMap<>();

                // Initialize an array list to store mode(s)
                ArrayList<Double> customModes = new ArrayList<>();
                ArrayList<Double> sportsModes = new ArrayList<>();
                ArrayList<Double> scienceModes = new ArrayList<>();
                ArrayList<Double> weatherModes = new ArrayList<>();
                ArrayList<Double> entertainmentModes = new ArrayList<>();


                // Calculate the frequency of each custom prediction correct percentage
                for (double percentage : correctCustomPercentages) {
                    frequencyMapCustom.put(percentage, frequencyMapCustom.getOrDefault(percentage, 0) + 1);
                }

                // Find the mode (correct percentages with the highest frequency)
                int maxCustomFrequency = 0;

                for (Map.Entry<Double, Integer> entry : frequencyMapCustom.entrySet()) {
                    if (entry.getValue() > maxCustomFrequency) {
                        maxCustomFrequency = entry.getValue();
                        customModes.clear(); // Clear previous modes
                        customModes.add(entry.getKey()); // Set the new mode
                    } else if (entry.getValue() == maxCustomFrequency) {
                        customModes.add(entry.getKey()); // Add to modes if it has the same frequency as maxFrequency
                    }
                }
                // If all elements occur an equal amount of times
                if (customModes.size() == frequencyMapCustom.size()) {
                    customModes.clear();
                    customModes.add(-1.0); // Set it to -1 to indicate no distinct modes
                }
                // Format each mode in the list
                ArrayList<Double> formattedCustomModes = new ArrayList<>();
                for (Double mode : customModes) {
                    formattedCustomModes.add(Double.parseDouble(df.format(mode)));
                }

                // Calculate the frequency of each sports prediction correct percentage
                for (double percentage : correctSportsPercentages) {
                    frequencyMapSports.put(percentage, frequencyMapSports.getOrDefault(percentage, 0) + 1);
                }
                // Find the mode (correct percentages with the highest frequency)
                int maxSportsFrequency = 0;
                for (Map.Entry<Double, Integer> entry : frequencyMapSports.entrySet()) {
                    if (entry.getValue() > maxSportsFrequency) {
                        maxSportsFrequency = entry.getValue();
                        sportsModes.clear(); // Clear previous modes
                        sportsModes.add(entry.getKey()); // Set the new mode
                    } else if (entry.getValue() == maxSportsFrequency) {
                        sportsModes.add(entry.getKey()); // Add to modes if it has the same frequency as maxFrequency
                    }
                }
                // If all elements occur an equal amount of times
                if (sportsModes.size() == frequencyMapSports.size()) {
                    sportsModes.clear();
                    sportsModes.add(-1.0); // Set it to -1 to indicate no distinct modes
                }
                // Format each mode in the list
                ArrayList<Double> formattedSportsModes = new ArrayList<>();
                for (Double mode : sportsModes) {
                    formattedSportsModes.add(Double.parseDouble(df.format(mode)));
                }

                // Calculate the frequency of each science correct percentage
                for (double percentage : correctSciencePercentages) {
                    frequencyMapScience.put(percentage, frequencyMapScience.getOrDefault(percentage, 0) + 1);
                }
                // Find the mode (correct percentages with the highest frequency)
                int maxScienceFrequency = 0;
                for (Map.Entry<Double, Integer> entry : frequencyMapScience.entrySet()) {
                    if (entry.getValue() > maxScienceFrequency) {
                        maxScienceFrequency = entry.getValue();
                        scienceModes.clear(); // Clear previous modes
                        scienceModes.add(entry.getKey()); // Set the new mode
                    } else if (entry.getValue() == maxScienceFrequency) {
                        scienceModes.add(entry.getKey()); // Add to modes if it has the same frequency as maxFrequency
                    }
                }
                // If all elements occur an equal amount of times
                if (scienceModes.size() == frequencyMapScience.size()) {
                    scienceModes.clear();
                    scienceModes.add(-1.0); // Set it to -1 to indicate no distinct modes
                }
                // Format each mode in the list
                ArrayList<Double> formattedScienceModes = new ArrayList<>();
                for (Double mode : scienceModes) {
                    formattedScienceModes.add(Double.parseDouble(df.format(mode)));
                }

                // Calculate the frequency of each weather correct percentage
                for (double percentage : correctWeatherPercentages) {
                    frequencyMapWeather.put(percentage, frequencyMapWeather.getOrDefault(percentage, 0) + 1);
                }

                // Find the mode (correct percentages with the highest frequency)
                int maxWeatherFrequency = 0;

                for (Map.Entry<Double, Integer> entry : frequencyMapWeather.entrySet()) {
                    if (entry.getValue() > maxWeatherFrequency) {
                        maxWeatherFrequency = entry.getValue();
                        weatherModes.clear(); // Clear previous modes
                        weatherModes.add(entry.getKey()); // Set the new mode
                    } else if (entry.getValue() == maxWeatherFrequency) {
                        weatherModes.add(entry.getKey()); // Add to modes if it has the same frequency as maxFrequency
                    }
                }
                // If all elements occur an equal amount of times
                if (weatherModes.size() == frequencyMapWeather.size()) {
                    weatherModes.clear();
                    weatherModes.add(-1.0); // Set it to -1 to indicate no distinct modes
                }
                // Format each mode in the list
                ArrayList<Double> formattedWeatherModes = new ArrayList<>();
                for (Double mode : weatherModes) {
                    formattedWeatherModes.add(Double.parseDouble(df.format(mode)));
                }

                // Calculate the frequency of each entertainment correct percentage
                for (double percentage : correctEntertainmentPercentages) {
                    frequencyMapEntertainment.put(percentage, frequencyMapEntertainment.getOrDefault(percentage, 0) + 1);
                }
                // Find the mode (correct percentages with the highest frequency)
                int maxFrequency = 0;
                for (Map.Entry<Double, Integer> entry : frequencyMapEntertainment.entrySet()) {
                    if (entry.getValue() > maxFrequency) {
                        maxFrequency = entry.getValue();
                        entertainmentModes.clear(); // Clear previous modes
                        entertainmentModes.add(entry.getKey()); // Set the new mode
                    } else if (entry.getValue() == maxFrequency) {
                        entertainmentModes.add(entry.getKey()); // Add to modes if it has the same frequency as maxFrequency
                    }
                }
                // If all elements occur an equal amount of times
                if (entertainmentModes.size() == frequencyMapEntertainment.size()) {
                    entertainmentModes.clear();
                    entertainmentModes.add(-1.0); // Set it to -1 to indicate no distinct modes
                }
                // Format each mode in the list
                ArrayList<Double> formattedEntertainmentModes = new ArrayList<>();
                for (Double mode : entertainmentModes) {
                    formattedEntertainmentModes.add(Double.parseDouble(df.format(mode)));
                }

                // Calculate the standard deviation of correct custom percentages
                Double sumOfSquaredDifferences = 0.0;
                for (Double percentage : correctCustomPercentages) {
                    Double diff = percentage - meanCorrectCustomPercentage;
                    sumOfSquaredDifferences += diff * diff;
                }
                Double standardDeviationCustom = Math.sqrt(sumOfSquaredDifferences / correctCustomPercentages.size());

                sumOfSquaredDifferences = 0.0;
                for (Double percentage : correctSportsPercentages) {
                    Double diff = percentage - meanCorrectSportsPercentage;
                    sumOfSquaredDifferences += diff * diff;
                }
                Double standardDeviationSports = Math.sqrt(sumOfSquaredDifferences / correctSportsPercentages.size());

                sumOfSquaredDifferences = 0.0;
                for (Double percentage : correctSciencePercentages) {
                    Double diff = percentage - meanCorrectSciencePercentage;
                    sumOfSquaredDifferences += diff * diff;
                }
                Double standardDeviationScience = Math.sqrt(sumOfSquaredDifferences / correctSciencePercentages.size());

                sumOfSquaredDifferences = 0.0;
                for (Double percentage : correctWeatherPercentages) {
                    Double diff = percentage - meanCorrectWeatherPercentage;
                    sumOfSquaredDifferences += diff * diff;
                }
                Double standardDeviationWeather = Math.sqrt(sumOfSquaredDifferences / correctWeatherPercentages.size());

                sumOfSquaredDifferences = 0.0;
                for (Double percentage : correctEntertainmentPercentages) {
                    Double diff = percentage - meanCorrectEntertainmentPercentage;
                    sumOfSquaredDifferences += diff * diff;
                }
                Double standardDeviationEntertainment = Math.sqrt(sumOfSquaredDifferences / correctEntertainmentPercentages.size());

                // Format the calculated values
                String formattedCustomMeanCorrectPercentage = df.format(meanCorrectCustomPercentage);
                String formattedCustomStandardDeviation = df.format(standardDeviationCustom);
                String formattedSportsMeanCorrectPercentage = df.format(meanCorrectSportsPercentage);
                String formattedSportsStandardDeviation = df.format(standardDeviationSports);
                String formattedScienceMeanCorrectPercentage = df.format(meanCorrectSciencePercentage);
                String formattedScienceStandardDeviation = df.format(standardDeviationScience);
                String formattedWeatherMeanCorrectPercentage = df.format(meanCorrectWeatherPercentage);
                String formattedWeatherStandardDeviation = df.format(standardDeviationWeather);
                String formattedEntertainmentMeanCorrectPercentage = df.format(meanCorrectEntertainmentPercentage);
                String formattedEntertainmentStandardDeviation = df.format(standardDeviationEntertainment);

                // Set the mean of correct percentages of each category
                overallInferentialStatistics.setMeanCorrectPercentageCustom(Double.parseDouble(formattedCustomMeanCorrectPercentage));
                overallInferentialStatistics.setMeanCorrectPercentageSports(Double.parseDouble(formattedSportsMeanCorrectPercentage));
                overallInferentialStatistics.setMeanCorrectPercentageScience(Double.parseDouble(formattedScienceMeanCorrectPercentage));
                overallInferentialStatistics.setMeanCorrectPercentageWeather(Double.parseDouble(formattedWeatherMeanCorrectPercentage));
                overallInferentialStatistics.setMeanCorrectPercentageEntertainment(Double.parseDouble(formattedEntertainmentMeanCorrectPercentage));

                // Set the mode value of each category as the formatted array list if it exists
                if (!formattedCustomModes.isEmpty() && !formattedCustomModes.get(0).equals(-1.0)) {
                    overallInferentialStatistics.setModeCorrectPercentageCustom(formattedCustomModes);
                } else {
                    ArrayList<Double> noCustomModes = new ArrayList<>();
                    noCustomModes.add(Double.NaN);
                    overallInferentialStatistics.setModeCorrectPercentageCustom(noCustomModes);
                }
                if (!formattedSportsModes.isEmpty() && !formattedSportsModes.get(0).equals(-1.0)) {
                    overallInferentialStatistics.setModeCorrectPercentageSports(formattedSportsModes);
                } else {
                    ArrayList<Double> noSportsModes = new ArrayList<>();
                    noSportsModes.add(Double.NaN);
                    overallInferentialStatistics.setModeCorrectPercentageSports(noSportsModes);
                }
                if (!formattedScienceModes.isEmpty() && !formattedScienceModes.get(0).equals(-1.0)) {
                    overallInferentialStatistics.setModeCorrectPercentageScience(formattedScienceModes);
                } else {
                    ArrayList<Double> noScienceModes = new ArrayList<>();
                    noScienceModes.add(Double.NaN);
                    overallInferentialStatistics.setModeCorrectPercentageScience(noScienceModes);
                }
                if (!formattedWeatherModes.isEmpty() && !formattedWeatherModes.get(0).equals(-1.0)) {
                    overallInferentialStatistics.setModeCorrectPercentageWeather(formattedWeatherModes);
                } else {
                    ArrayList<Double> noWeatherModes = new ArrayList<>();
                    noWeatherModes.add(Double.NaN);
                    overallInferentialStatistics.setModeCorrectPercentageWeather(noWeatherModes);
                }
                if (!formattedEntertainmentModes.isEmpty() && !formattedEntertainmentModes.get(0).equals(-1.0)) {
                    overallInferentialStatistics.setModeCorrectPercentageEntertainment(formattedEntertainmentModes);
                } else {
                    ArrayList<Double> noEntertainmentModes = new ArrayList<>();
                    noEntertainmentModes.add(Double.NaN);
                    overallInferentialStatistics.setModeCorrectPercentageEntertainment(noEntertainmentModes);
                }

                // Format and set the median value to two decimal places
                overallInferentialStatistics.setMedianCorrectPercentageCustom(Double.parseDouble(df.format(medianCustomCorrectPercentage)));
                overallInferentialStatistics.setMedianCorrectPercentageSports(Double.parseDouble(df.format(medianSportsCorrectPercentage)));
                overallInferentialStatistics.setMedianCorrectPercentageScience(Double.parseDouble(df.format(medianScienceCorrectPercentage)));
                overallInferentialStatistics.setMedianCorrectPercentageWeather(Double.parseDouble(df.format(medianWeatherCorrectPercentage)));
                overallInferentialStatistics.setMedianCorrectPercentageEntertainment(Double.parseDouble(df.format(medianEntertainmentCorrectPercentage)));

                // Set standard deviation of the percentage of correct predictions for each category
                overallInferentialStatistics.setStandardDeviationCorrectPercentageCustom(Double.parseDouble(formattedCustomStandardDeviation));
                overallInferentialStatistics.setStandardDeviationCorrectPercentageSports(Double.parseDouble(formattedSportsStandardDeviation));
                overallInferentialStatistics.setStandardDeviationCorrectPercentageScience(Double.parseDouble(formattedScienceStandardDeviation));
                overallInferentialStatistics.setStandardDeviationCorrectPercentageWeather(Double.parseDouble(formattedWeatherStandardDeviation));
                overallInferentialStatistics.setStandardDeviationCorrectPercentageEntertainment(Double.parseDouble(formattedEntertainmentStandardDeviation));

                // Set overall prediction counts for each category
                overallInferentialStatistics.setCustomPredictionCount(overallCustomPredictionCount);
                overallInferentialStatistics.setSportsPredictionCount(overallSportsPredictionCount);
                overallInferentialStatistics.setSciencePredictionCount(overallSciencePredictionCount);
                overallInferentialStatistics.setWeatherPredictionCount(overallWeatherPredictionCount);
                overallInferentialStatistics.setEntertainmentPredictionCount(overallEntertainmentPredictionCount);

                // Set overall correct and incorrect prediction counts for each category
                overallInferentialStatistics.setCustomCorrectPredictions(overallCustomCorrectPredictionCount);
                overallInferentialStatistics.setCustomIncorrectPredictions(overallCustomPredictionCount - overallCustomCorrectPredictionCount);
                overallInferentialStatistics.setSportsCorrectPredictions(overallSportsCorrectPredictionCount);
                overallInferentialStatistics.setSportsIncorrectPredictions(overallSportsPredictionCount - overallSportsCorrectPredictionCount);
                overallInferentialStatistics.setScienceCorrectPredictions(overallScienceCorrectPredictionCount);
                overallInferentialStatistics.setScienceIncorrectPredictions(overallSciencePredictionCount - overallScienceCorrectPredictionCount);
                overallInferentialStatistics.setWeatherCorrectPredictions(overallWeatherCorrectPredictionCount);
                overallInferentialStatistics.setWeatherIncorrectPredictions(overallWeatherPredictionCount - overallWeatherCorrectPredictionCount);
                overallInferentialStatistics.setEntertainmentCorrectPredictions(overallEntertainmentCorrectPredictionCount);
                overallInferentialStatistics.setEntertainmentIncorrectPredictions(overallEntertainmentPredictionCount - overallEntertainmentCorrectPredictionCount);

                // Calculate percentage of overall correct predictions (overall correct predictions count / overall prediction count) for each category
                Double overallCustomPercentCorrect;
                Double overallCustomPercentIncorrect;
                if (overallInferentialStatistics.getCustomPredictionCount() > 0) {
                    overallCustomPercentCorrect = (double) overallCustomCorrectPredictionCount / overallCustomPredictionCount * 100.0;
                    overallCustomPercentIncorrect = 100.0 - overallCustomPercentCorrect;
                } else {
                    overallCustomPercentCorrect = Double.NaN;
                    overallCustomPercentIncorrect = Double.NaN;
                }
                Double overallSportsPercentCorrect;
                Double overallSportsPercentIncorrect;
                if (overallInferentialStatistics.getSportsPredictionCount() > 0) {
                    overallSportsPercentCorrect = (double) overallSportsCorrectPredictionCount / overallSportsPredictionCount * 100.0;
                    overallSportsPercentIncorrect = 100.0 - overallSportsPercentCorrect;
                } else {
                    overallSportsPercentCorrect = Double.NaN;
                    overallSportsPercentIncorrect = Double.NaN;
                }
                Double overallSciencePercentCorrect;
                Double overallSciencePercentIncorrect;
                if (overallInferentialStatistics.getSciencePredictionCount() > 0) {
                    overallSciencePercentCorrect = (double) overallScienceCorrectPredictionCount / overallSciencePredictionCount * 100.0;
                    overallSciencePercentIncorrect = 100.0 - overallSciencePercentCorrect;
                } else {
                    overallSciencePercentCorrect = Double.NaN;
                    overallSciencePercentIncorrect = Double.NaN;
                }
                Double overallWeatherPercentCorrect;
                Double overallWeatherPercentIncorrect;
                if (overallInferentialStatistics.getWeatherPredictionCount() > 0) {
                    overallWeatherPercentCorrect = (double) overallWeatherCorrectPredictionCount / overallWeatherPredictionCount * 100.0;
                    overallWeatherPercentIncorrect = 100.0 - overallWeatherPercentCorrect;
                } else {
                    overallWeatherPercentCorrect = Double.NaN;
                    overallWeatherPercentIncorrect = Double.NaN;
                }
                Double overallEntertainmentPercentCorrect;
                Double overallEntertainmentPercentIncorrect;
                if (overallInferentialStatistics.getEntertainmentPredictionCount() > 0) {
                    overallEntertainmentPercentCorrect = (double) overallEntertainmentCorrectPredictionCount / overallEntertainmentPredictionCount * 100.0;
                    overallEntertainmentPercentIncorrect = 100.0 - overallEntertainmentPercentCorrect;
                } else {
                    overallEntertainmentPercentCorrect = Double.NaN;
                    overallEntertainmentPercentIncorrect = Double.NaN;
                }

                // Format and set the percentage values to two decimal places
                overallInferentialStatistics.setCustomPercentCorrect(Double.parseDouble(df.format(overallCustomPercentCorrect)));
                overallInferentialStatistics.setCustomPercentIncorrect(Double.parseDouble(df.format(overallCustomPercentIncorrect)));
                overallInferentialStatistics.setSportsPercentCorrect(Double.parseDouble(df.format(overallSportsPercentCorrect)));
                overallInferentialStatistics.setSportsPercentIncorrect(Double.parseDouble(df.format(overallSportsPercentIncorrect)));
                overallInferentialStatistics.setSciencePercentCorrect(Double.parseDouble(df.format(overallSciencePercentCorrect)));
                overallInferentialStatistics.setSciencePercentIncorrect(Double.parseDouble(df.format(overallSciencePercentIncorrect)));
                overallInferentialStatistics.setWeatherPercentCorrect(Double.parseDouble(df.format(overallWeatherPercentCorrect)));
                overallInferentialStatistics.setWeatherPercentIncorrect(Double.parseDouble(df.format(overallWeatherPercentIncorrect)));
                overallInferentialStatistics.setEntertainmentPercentCorrect(Double.parseDouble(df.format(overallEntertainmentPercentCorrect)));
                overallInferentialStatistics.setEntertainmentPercentIncorrect(Double.parseDouble(df.format(overallEntertainmentPercentIncorrect)));

                // Calculate the percentages of each prediction type made out of all predictions
                Double customPredictionsMadePercent;
                if (overallCustomPredictionCount > 0) {
                    customPredictionsMadePercent = ((double) overallInferentialStatistics.getCustomPredictionCount() / overallInferentialStatistics.getPredictionsMadeCount()) * 100;
                    String formattedCustomPredictionsMadePercent = df.format(customPredictionsMadePercent);
                    customPredictionsMadePercent = Double.parseDouble(formattedCustomPredictionsMadePercent);
                } else {
                    customPredictionsMadePercent = 0.0;
                }
                Double sportsPredictionsMadePercent;
                if (overallSportsPredictionCount > 0) {
                    sportsPredictionsMadePercent = ((double) overallInferentialStatistics.getSportsPredictionCount() / overallInferentialStatistics.getPredictionsMadeCount()) * 100;
                    String formattedSportsPredictionsMadePercent = df.format(sportsPredictionsMadePercent);
                    sportsPredictionsMadePercent = Double.parseDouble(formattedSportsPredictionsMadePercent);
                } else {
                    sportsPredictionsMadePercent = 0.0;
                }
                Double sciencePredictionsMadePercent;
                if (overallSciencePredictionCount > 0) {
                    sciencePredictionsMadePercent = ((double) overallInferentialStatistics.getSciencePredictionCount() / overallInferentialStatistics.getPredictionsMadeCount()) * 100;
                    String formattedSciencePredictionsMadePercent = df.format(sciencePredictionsMadePercent);
                    sciencePredictionsMadePercent = Double.parseDouble(formattedSciencePredictionsMadePercent);
                } else {
                    sciencePredictionsMadePercent = 0.0;
                }
                Double weatherPredictionsMadePercent;
                if (overallWeatherPredictionCount > 0) {
                    weatherPredictionsMadePercent = ((double) overallInferentialStatistics.getWeatherPredictionCount() / overallInferentialStatistics.getPredictionsMadeCount()) * 100;
                    String formattedWeatherPredictionsMadePercent = df.format(weatherPredictionsMadePercent);
                    weatherPredictionsMadePercent = Double.parseDouble(formattedWeatherPredictionsMadePercent);
                } else {
                    weatherPredictionsMadePercent = 0.0;
                }
                Double entertainmentPredictionsMadePercent;
                if (overallEntertainmentPredictionCount > 0) {
                    entertainmentPredictionsMadePercent = ((double) overallInferentialStatistics.getEntertainmentPredictionCount() / overallInferentialStatistics.getPredictionsMadeCount()) * 100;
                    String formattedEntertainmentPredictionsMadePercent = df.format(entertainmentPredictionsMadePercent);
                    entertainmentPredictionsMadePercent = Double.parseDouble(formattedEntertainmentPredictionsMadePercent);
                } else {
                    entertainmentPredictionsMadePercent = 0.0;
                }

                // Set the percentages of each prediction type made
                overallInferentialStatistics.setCustomPercentPredictionsMade(customPredictionsMadePercent);
                overallInferentialStatistics.setSportsPercentPredictionsMade(sportsPredictionsMadePercent);
                overallInferentialStatistics.setSciencePercentPredictionsMade(sciencePredictionsMadePercent);
                overallInferentialStatistics.setWeatherPercentPredictionsMade(weatherPredictionsMadePercent);
                overallInferentialStatistics.setEntertainmentPercentPredictionsMade(entertainmentPredictionsMadePercent);

                // Initialize String for mostCorrectPredictionType and leastCorrectPredictionType
                String greatestCorrectPredictionType = "No Predictions Made Yet";
                String leastCorrectPredictionType = "No Predictions Made Yet";

                // Get the user's most correct prediction type
                if (overallInferentialStatistics.getCustomPredictionCount() > 0) {
                    greatestCorrectPredictionType = "Custom";
                }
                if (overallInferentialStatistics.getSportsPredictionCount() > 0 && overallSportsPercentCorrect > overallCustomPercentCorrect) {
                    greatestCorrectPredictionType = "Sports";
                }
                if (overallInferentialStatistics.getSciencePredictionCount() > 0 && overallSciencePercentCorrect > overallCustomPercentCorrect && overallSciencePercentCorrect > overallSportsPercentCorrect) {
                    greatestCorrectPredictionType = "Science";
                }
                if (overallInferentialStatistics.getWeatherPredictionCount() > 0 && overallWeatherPercentCorrect > overallCustomPercentCorrect && overallWeatherPercentCorrect > overallSportsPercentCorrect && overallWeatherPercentCorrect > overallSciencePercentCorrect) {
                    greatestCorrectPredictionType = "Weather";
                }
                if (overallInferentialStatistics.getEntertainmentPredictionCount() > 0 && overallEntertainmentPercentCorrect > overallCustomPercentCorrect && overallEntertainmentPercentCorrect > overallSportsPercentCorrect && overallEntertainmentPercentCorrect > overallSciencePercentCorrect && overallEntertainmentPercentCorrect > overallWeatherPercentCorrect) {
                    greatestCorrectPredictionType = "Entertainment";
                }

                // Get the user's least correct prediction type
                if (overallInferentialStatistics.getCustomPredictionCount() > 0) {
                    leastCorrectPredictionType = "Custom";
                }
                if (overallInferentialStatistics.getSportsPredictionCount() > 0 && overallSportsPercentIncorrect > overallCustomPercentIncorrect) {
                    leastCorrectPredictionType = "Sports";
                }
                if (overallInferentialStatistics.getSciencePredictionCount() > 0 && overallSciencePercentIncorrect > overallCustomPercentIncorrect && overallSciencePercentIncorrect > overallSportsPercentIncorrect) {
                    leastCorrectPredictionType = "Science";
                }
                if (overallInferentialStatistics.getWeatherPredictionCount() > 0 && overallWeatherPercentIncorrect > overallCustomPercentIncorrect && overallWeatherPercentIncorrect > overallSportsPercentIncorrect && overallWeatherPercentIncorrect > overallSciencePercentIncorrect) {
                    leastCorrectPredictionType = "Weather";
                }
                if (overallInferentialStatistics.getEntertainmentPredictionCount() > 0 && overallEntertainmentPercentIncorrect > overallCustomPercentIncorrect && overallEntertainmentPercentIncorrect > overallSportsPercentIncorrect && overallEntertainmentPercentIncorrect > overallSciencePercentIncorrect && overallEntertainmentPercentIncorrect > overallWeatherPercentIncorrect) {
                    leastCorrectPredictionType = "Entertainment";
                }

                // Set the most correct prediction type and least correct prediction type
                overallInferentialStatistics.setMostCorrectPredictionType(greatestCorrectPredictionType);
                overallInferentialStatistics.setLeastCorrectPredictionType(leastCorrectPredictionType);

                // Save overall inferential statistics only if there are valid files
                if (hasValidFiles) {
                    String overallStatisticsFilePath = overallStatisticsFolderPath + File.separator + "overall_inferential_statistics.json";
                    try (FileWriter writer = new FileWriter(overallStatisticsFilePath)) {
                        Gson gson2 = gsonBuilder.create();
                        Type listType2 = new TypeToken<OverallInferentialStatistics>() {}.getType();
                        String json2 = gson2.toJson(overallInferentialStatistics, listType2);
                        writer.write(json2);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("ERROR - UserInfo.User inferential statistics folder is empty - Resolve a prediction to initialize.");
                }
            } else {
                System.out.println("ERROR - UserInfo.User inferential statistics file is null - Resolve a prediction to initialize.");
            }
        }
    }
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // calculateAndSaveOverallInferentialStatisticsMongoDB
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Calculates the overall inferential statistics by going through each user's inferential statistics
    // file and collecting values of the min correct percent, max correct percent, and total numbers of
    // correct predictions and predictions overall before saving to MongoDB.
    //
    public static void calculateAndSaveOverallInferentialStatisticsMongoDB() {
        // Initialize userInferentialStatisticsFilesFolder as a new File
        ArrayList<EnvisionaryUser> envisionaryUsers = MongoDBEnvisionaryUsers.retrieveCollection();

        // Initialize new OverallStatistics.OverallInferentialStatistics object
        OverallInferentialStatistics overallInferentialStatistics = new OverallInferentialStatistics();
        overallInferentialStatistics.setMaxPercentCorrectCustom(-7.7);
        overallInferentialStatistics.setMaxPercentCorrectSports(-7.7);
        overallInferentialStatistics.setMaxPercentCorrectScience(-7.7);
        overallInferentialStatistics.setMaxPercentCorrectWeather(-7.7);
        overallInferentialStatistics.setMaxPercentCorrectEntertainment(-7.7);
        overallInferentialStatistics.setMinPercentCorrectCustom(777.777);
        overallInferentialStatistics.setMinPercentCorrectSports(777.777);
        overallInferentialStatistics.setMinPercentCorrectScience(777.777);
        overallInferentialStatistics.setMinPercentCorrectWeather(777.777);
        overallInferentialStatistics.setMinPercentCorrectEntertainment(777.777);

        // Set the decimal format for saving percentage calculations at 2 decimal places
        DecimalFormat df = new DecimalFormat("0.00");

        // Create lists to store each of the category percentages of correct predictions for all users
        ArrayList<Double> correctCustomPercentages = new ArrayList<>();
        ArrayList<Double> correctSportsPercentages = new ArrayList<>();
        ArrayList<Double> correctSciencePercentages = new ArrayList<>();
        ArrayList<Double> correctWeatherPercentages = new ArrayList<>();
        ArrayList<Double> correctEntertainmentPercentages = new ArrayList<>();

        // Initialize overall counters
        int overallCustomPredictionCount = 0;
        int overallSportsPredictionCount = 0;
        int overallSciencePredictionCount = 0;
        int overallWeatherPredictionCount = 0;
        int overallEntertainmentPredictionCount = 0;
        int overallCustomCorrectPredictionCount = 0;
        int overallSportsCorrectPredictionCount = 0;
        int overallScienceCorrectPredictionCount = 0;
        int overallWeatherCorrectPredictionCount = 0;
        int overallEntertainmentCorrectPredictionCount = 0;

        // For each user statistics file within the user inferential statistics folder
        for (EnvisionaryUser user : envisionaryUsers) {
            // Load the user's inferential statistics
            UserInferentialStatistics loadedUserInferentialStatistics = user.getUserInferentialStatistics();

            // Check if user's percentage of correct custom predictions is less than the current custom min
            if (loadedUserInferentialStatistics.getCustomPredictionCount() > 0 && loadedUserInferentialStatistics.getCustomPercentCorrect() < overallInferentialStatistics.getMinPercentCorrectCustom()) {
                overallInferentialStatistics.setMinPercentCorrectCustom(loadedUserInferentialStatistics.getCustomPercentCorrect());
            }
            // Check if user's percentage of correct sports predictions is less than the current sports min
            if (loadedUserInferentialStatistics.getSportsPredictionCount() > 0 && loadedUserInferentialStatistics.getSportsPercentCorrect() < overallInferentialStatistics.getMinPercentCorrectSports()) {
                overallInferentialStatistics.setMinPercentCorrectSports(loadedUserInferentialStatistics.getSportsPercentCorrect());
            }
            // Check if user's percentage of correct science predictions is less than the current science min
            if (loadedUserInferentialStatistics.getSciencePredictionCount() > 0 && loadedUserInferentialStatistics.getSciencePercentCorrect() < overallInferentialStatistics.getMinPercentCorrectScience()) {
                overallInferentialStatistics.setMinPercentCorrectScience(loadedUserInferentialStatistics.getSciencePercentCorrect());
            }
            // Check if user's percentage of correct weather predictions is less than the current weather min
            if (loadedUserInferentialStatistics.getWeatherPredictionCount() > 0 && loadedUserInferentialStatistics.getWeatherPercentCorrect() < overallInferentialStatistics.getMinPercentCorrectWeather()) {
                overallInferentialStatistics.setMinPercentCorrectWeather(loadedUserInferentialStatistics.getWeatherPercentCorrect());
            }
            // Check if user's percentage of correct entertainment predictions is less than the current entertainment min
            if (loadedUserInferentialStatistics.getEntertainmentPredictionCount() > 0 && loadedUserInferentialStatistics.getEntertainmentPercentCorrect() < overallInferentialStatistics.getMinPercentCorrectEntertainment()) {
                overallInferentialStatistics.setMinPercentCorrectEntertainment(loadedUserInferentialStatistics.getEntertainmentPercentCorrect());
            }

            // Check if user's percentage of correct custom predictions is greater than the current custom max
            if (loadedUserInferentialStatistics.getCustomPredictionCount() > 0 && loadedUserInferentialStatistics.getCustomPercentCorrect() > overallInferentialStatistics.getMaxPercentCorrectCustom()) {
                overallInferentialStatistics.setMaxPercentCorrectCustom(loadedUserInferentialStatistics.getCustomPercentCorrect());
            }
            // Check if user's percentage of correct sports predictions is greater than the current sports max
            if (loadedUserInferentialStatistics.getSportsPredictionCount() > 0 && loadedUserInferentialStatistics.getSportsPercentCorrect() > overallInferentialStatistics.getMaxPercentCorrectSports()) {
                overallInferentialStatistics.setMaxPercentCorrectSports(loadedUserInferentialStatistics.getSportsPercentCorrect());
            }
            // Check if user's percentage of correct science predictions is greater than the current science max
            if (loadedUserInferentialStatistics.getSciencePredictionCount() > 0 && loadedUserInferentialStatistics.getSciencePercentCorrect() > overallInferentialStatistics.getMaxPercentCorrectScience()) {
                overallInferentialStatistics.setMaxPercentCorrectScience(loadedUserInferentialStatistics.getSciencePercentCorrect());
            }
            // Check if user's percentage of correct weather predictions is greater than the current weather max
            if (loadedUserInferentialStatistics.getWeatherPredictionCount() > 0 && loadedUserInferentialStatistics.getWeatherPercentCorrect() > overallInferentialStatistics.getMaxPercentCorrectWeather()) {
                overallInferentialStatistics.setMaxPercentCorrectWeather(loadedUserInferentialStatistics.getWeatherPercentCorrect());
            }
            // Check if user's percentage of correct entertainment predictions is greater than the current entertainment max
            if (loadedUserInferentialStatistics.getEntertainmentPredictionCount() > 0 && loadedUserInferentialStatistics.getEntertainmentPercentCorrect() > overallInferentialStatistics.getMaxPercentCorrectEntertainment()) {
                overallInferentialStatistics.setMaxPercentCorrectEntertainment(loadedUserInferentialStatistics.getEntertainmentPercentCorrect());
            }

            // Add user's prediction counts to overall category prediction counts
            overallCustomPredictionCount += loadedUserInferentialStatistics.getCustomPredictionCount();
            overallSportsPredictionCount += loadedUserInferentialStatistics.getSportsPredictionCount();
            overallSciencePredictionCount += loadedUserInferentialStatistics.getSciencePredictionCount();
            overallWeatherPredictionCount += loadedUserInferentialStatistics.getWeatherPredictionCount();
            overallEntertainmentPredictionCount += loadedUserInferentialStatistics.getEntertainmentPredictionCount();

            // Add user's correct prediction count to overall correct prediction count
            overallCustomCorrectPredictionCount += loadedUserInferentialStatistics.getCustomCorrectPredictions();
            overallSportsCorrectPredictionCount += loadedUserInferentialStatistics.getSportsCorrectPredictions();
            overallScienceCorrectPredictionCount += loadedUserInferentialStatistics.getScienceCorrectPredictions();
            overallWeatherCorrectPredictionCount += loadedUserInferentialStatistics.getWeatherCorrectPredictions();
            overallEntertainmentCorrectPredictionCount += loadedUserInferentialStatistics.getEntertainmentCorrectPredictions();

            // Calculate percentages for each category of user's correct predictions
            Double userCorrectCustomPercentage = (double) loadedUserInferentialStatistics.getCustomCorrectPredictions() / loadedUserInferentialStatistics.getCustomPredictionCount() * 100.0;
            if (!userCorrectCustomPercentage.isNaN())
                correctCustomPercentages.add(userCorrectCustomPercentage);
            Double userCorrectSportsPercentage = (double) loadedUserInferentialStatistics.getSportsCorrectPredictions() / loadedUserInferentialStatistics.getSportsPredictionCount() * 100.0;
            if (!userCorrectSportsPercentage.isNaN())
                correctSportsPercentages.add(userCorrectSportsPercentage);
            Double userCorrectSciencePercentage = (double) loadedUserInferentialStatistics.getScienceCorrectPredictions() / loadedUserInferentialStatistics.getSciencePredictionCount() * 100.0;
            if (!userCorrectSciencePercentage.isNaN())
                correctSciencePercentages.add(userCorrectSciencePercentage);
            Double userCorrectWeatherPercentage = (double) loadedUserInferentialStatistics.getWeatherCorrectPredictions() / loadedUserInferentialStatistics.getWeatherPredictionCount() * 100.0;
            if (!userCorrectWeatherPercentage.isNaN())
                correctWeatherPercentages.add(userCorrectWeatherPercentage);
            Double userCorrectEntertainmentPercentage = (double) loadedUserInferentialStatistics.getEntertainmentCorrectPredictions() / loadedUserInferentialStatistics.getEntertainmentPredictionCount() * 100.0;
            if (!userCorrectEntertainmentPercentage.isNaN())
                correctEntertainmentPercentages.add(userCorrectEntertainmentPercentage);

            // Add each category prediction count to the total predictions made count of the overall inferential statistics
            overallInferentialStatistics.setPredictionsMadeCount(overallInferentialStatistics.getPredictionsMadeCount() + loadedUserInferentialStatistics.getCustomPredictionCount());
            overallInferentialStatistics.setPredictionsMadeCount(overallInferentialStatistics.getPredictionsMadeCount() + loadedUserInferentialStatistics.getSportsPredictionCount());
            overallInferentialStatistics.setPredictionsMadeCount(overallInferentialStatistics.getPredictionsMadeCount() + loadedUserInferentialStatistics.getSciencePredictionCount());
            overallInferentialStatistics.setPredictionsMadeCount(overallInferentialStatistics.getPredictionsMadeCount() + loadedUserInferentialStatistics.getWeatherPredictionCount());
            overallInferentialStatistics.setPredictionsMadeCount(overallInferentialStatistics.getPredictionsMadeCount() + loadedUserInferentialStatistics.getEntertainmentPredictionCount());
        }

        // If minimum percent correct is still set to initial value, set to NaN
        if (overallInferentialStatistics.getMinPercentCorrectCustom() == 777.777) {
            overallInferentialStatistics.setMinPercentCorrectCustom(Double.NaN);
        }
        if (overallInferentialStatistics.getMinPercentCorrectSports() == 777.777) {
            overallInferentialStatistics.setMinPercentCorrectSports(Double.NaN);
        }
        if (overallInferentialStatistics.getMinPercentCorrectScience() == 777.777) {
            overallInferentialStatistics.setMinPercentCorrectScience(Double.NaN);
        }
        if (overallInferentialStatistics.getMinPercentCorrectWeather() == 777.777) {
            overallInferentialStatistics.setMinPercentCorrectWeather(Double.NaN);
        }
        if (overallInferentialStatistics.getMinPercentCorrectEntertainment() == 777.777) {
            overallInferentialStatistics.setMinPercentCorrectEntertainment(Double.NaN);
        }

        // If maximum percent correct is still set to initial value, set to NaN
        if (overallInferentialStatistics.getMaxPercentCorrectCustom() == -7.7) {
            overallInferentialStatistics.setMaxPercentCorrectCustom(Double.NaN);
        }
        if (overallInferentialStatistics.getMaxPercentCorrectSports() == -7.7) {
            overallInferentialStatistics.setMaxPercentCorrectSports(Double.NaN);
        }
        if (overallInferentialStatistics.getMaxPercentCorrectScience() == -7.7) {
            overallInferentialStatistics.setMaxPercentCorrectScience(Double.NaN);
        }
        if (overallInferentialStatistics.getMaxPercentCorrectWeather() == -7.7) {
            overallInferentialStatistics.setMaxPercentCorrectWeather(Double.NaN);
        }
        if (overallInferentialStatistics.getMaxPercentCorrectEntertainment() == -7.7) {
            overallInferentialStatistics.setMaxPercentCorrectEntertainment(Double.NaN);
        }

        // Calculate the mean (average) of correct percentages for each category
        Double sum = 0.0;
        for (Double percentage : correctCustomPercentages) {
            sum += percentage;
        }
        Double meanCorrectCustomPercentage = sum / correctCustomPercentages.size();
        sum = 0.0;
        for (Double percentage : correctSportsPercentages) {
            sum += percentage;
        }
        Double meanCorrectSportsPercentage = sum / correctSportsPercentages.size();
        sum = 0.0;
        for (Double percentage : correctSciencePercentages) {
            sum += percentage;
        }
        Double meanCorrectSciencePercentage = sum / correctSciencePercentages.size();
        sum = 0.0;
        for (Double percentage : correctWeatherPercentages) {
            sum += percentage;
        }
        Double meanCorrectWeatherPercentage = sum / correctWeatherPercentages.size();
        sum = 0.0;
        for (Double percentage : correctEntertainmentPercentages) {
            sum += percentage;
        }
        Double meanCorrectEntertainmentPercentage = sum / correctEntertainmentPercentages.size();

        // Calculate the median of correct percentages for each category
        correctCustomPercentages.sort(null);
        double medianCustomCorrectPercentage;
        int size = correctCustomPercentages.size();
        if (size > 0) {
            if (size % 2 == 0) {
                // Even number of elements
                int middle1 = size / 2;
                int middle2 = middle1 - 1;
                medianCustomCorrectPercentage = (correctCustomPercentages.get(middle1) + correctCustomPercentages.get(middle2)) / 2.0;
            } else {
                // Odd number of elements
                medianCustomCorrectPercentage = correctCustomPercentages.get(size / 2);
            }
        } else {
            medianCustomCorrectPercentage = 0.0; // Set a default value or handle the case when the list is empty.
        }

        correctSportsPercentages.sort(null);
        double medianSportsCorrectPercentage;
        size = correctSportsPercentages.size();
        if (size > 0) {
            if (size % 2 == 0) {
                // Even number of elements
                int middle1 = size / 2;
                int middle2 = middle1 - 1;
                medianSportsCorrectPercentage = (correctSportsPercentages.get(middle1) + correctSportsPercentages.get(middle2)) / 2.0;
            } else {
                // Odd number of elements
                medianSportsCorrectPercentage = correctSportsPercentages.get(size / 2);
            }
        } else {
            medianSportsCorrectPercentage = 0.0; // Set a default value or handle the case when the list is empty.
        }

        correctSciencePercentages.sort(null);
        double medianScienceCorrectPercentage;
        size = correctSciencePercentages.size();
        if (size > 0) {
            if (size % 2 == 0) {
                // Even number of elements
                int middle1 = size / 2;
                int middle2 = middle1 - 1;
                medianScienceCorrectPercentage = (correctSciencePercentages.get(middle1) + correctSciencePercentages.get(middle2)) / 2.0;
            } else {
                // Odd number of elements
                medianScienceCorrectPercentage = correctSciencePercentages.get(size / 2);
            }
        } else {
            medianScienceCorrectPercentage = 0.0; // Set a default value or handle the case when the list is empty.
        }

        correctWeatherPercentages.sort(null);
        double medianWeatherCorrectPercentage;
        size = correctWeatherPercentages.size();
        if (size > 0) {
            if (size % 2 == 0) {
                // Even number of elements
                int middle1 = size / 2;
                int middle2 = middle1 - 1;
                medianWeatherCorrectPercentage = (correctWeatherPercentages.get(middle1) + correctWeatherPercentages.get(middle2)) / 2.0;
            } else {
                // Odd number of elements
                medianWeatherCorrectPercentage = correctWeatherPercentages.get(size / 2);
            }
        } else {
            medianWeatherCorrectPercentage = 0.0; // Set a default value or handle the case when the list is empty.
        }

        correctEntertainmentPercentages.sort(null);
        double medianEntertainmentCorrectPercentage;
        size = correctEntertainmentPercentages.size();
        if (size > 0) {
            if (size % 2 == 0) {
                // Even number of elements
                int middle1 = size / 2;
                int middle2 = middle1 - 1;
                medianEntertainmentCorrectPercentage = (correctEntertainmentPercentages.get(middle1) + correctEntertainmentPercentages.get(middle2)) / 2.0;
            } else {
                // Odd number of elements
                medianEntertainmentCorrectPercentage = correctEntertainmentPercentages.get(size / 2);
            }
        } else {
            medianEntertainmentCorrectPercentage = 0.0; // Set a default value or handle the case when the list is empty.
        }

        // Calculate the mode(s) of correct percentages for each category
        // Create a map to store the frequency of each correct percentage
        Map<Double, Integer> frequencyMapCustom = new HashMap<>();
        Map<Double, Integer> frequencyMapSports = new HashMap<>();
        Map<Double, Integer> frequencyMapScience = new HashMap<>();
        Map<Double, Integer> frequencyMapWeather = new HashMap<>();
        Map<Double, Integer> frequencyMapEntertainment = new HashMap<>();

        // Initialize an array list to store mode(s)
        ArrayList<Double> customModes = new ArrayList<>();
        ArrayList<Double> sportsModes = new ArrayList<>();
        ArrayList<Double> scienceModes = new ArrayList<>();
        ArrayList<Double> weatherModes = new ArrayList<>();
        ArrayList<Double> entertainmentModes = new ArrayList<>();


        // Calculate the frequency of each custom prediction correct percentage
        for (double percentage : correctCustomPercentages) {
            frequencyMapCustom.put(percentage, frequencyMapCustom.getOrDefault(percentage, 0) + 1);
        }

        // Find the mode (correct percentages with the highest frequency)
        int maxCustomFrequency = 0;

        for (Map.Entry<Double, Integer> entry : frequencyMapCustom.entrySet()) {
            if (entry.getValue() > maxCustomFrequency) {
                maxCustomFrequency = entry.getValue();
                customModes.clear(); // Clear previous modes
                customModes.add(entry.getKey()); // Set the new mode
            } else if (entry.getValue() == maxCustomFrequency) {
                customModes.add(entry.getKey()); // Add to modes if it has the same frequency as maxFrequency
            }
        }
        // If all elements occur an equal amount of times
        if (customModes.size() == frequencyMapCustom.size()) {
            customModes.clear();
            customModes.add(-1.0); // Set it to -1 to indicate no distinct modes
        }
        // Format each mode in the list
        ArrayList<Double> formattedCustomModes = new ArrayList<>();
        for (Double mode : customModes) {
            formattedCustomModes.add(Double.parseDouble(df.format(mode)));
        }

        // Calculate the frequency of each sports prediction correct percentage
        for (double percentage : correctSportsPercentages) {
            frequencyMapSports.put(percentage, frequencyMapSports.getOrDefault(percentage, 0) + 1);
        }
        // Find the mode (correct percentages with the highest frequency)
        int maxSportsFrequency = 0;
        for (Map.Entry<Double, Integer> entry : frequencyMapSports.entrySet()) {
            if (entry.getValue() > maxSportsFrequency) {
                maxSportsFrequency = entry.getValue();
                sportsModes.clear(); // Clear previous modes
                sportsModes.add(entry.getKey()); // Set the new mode
            } else if (entry.getValue() == maxSportsFrequency) {
                sportsModes.add(entry.getKey()); // Add to modes if it has the same frequency as maxFrequency
            }
        }
        // If all elements occur an equal amount of times
        if (sportsModes.size() == frequencyMapSports.size()) {
            sportsModes.clear();
            sportsModes.add(-1.0); // Set it to -1 to indicate no distinct modes
        }
        // Format each mode in the list
        ArrayList<Double> formattedSportsModes = new ArrayList<>();
        for (Double mode : sportsModes) {
            formattedSportsModes.add(Double.parseDouble(df.format(mode)));
        }

        // Calculate the frequency of each science correct percentage
        for (double percentage : correctSciencePercentages) {
            frequencyMapScience.put(percentage, frequencyMapScience.getOrDefault(percentage, 0) + 1);
        }
        // Find the mode (correct percentages with the highest frequency)
        int maxScienceFrequency = 0;
        for (Map.Entry<Double, Integer> entry : frequencyMapScience.entrySet()) {
            if (entry.getValue() > maxScienceFrequency) {
                maxScienceFrequency = entry.getValue();
                scienceModes.clear(); // Clear previous modes
                scienceModes.add(entry.getKey()); // Set the new mode
            } else if (entry.getValue() == maxScienceFrequency) {
                scienceModes.add(entry.getKey()); // Add to modes if it has the same frequency as maxFrequency
            }
        }
        // If all elements occur an equal amount of times
        if (scienceModes.size() == frequencyMapScience.size()) {
            scienceModes.clear();
            scienceModes.add(-1.0); // Set it to -1 to indicate no distinct modes
        }
        // Format each mode in the list
        ArrayList<Double> formattedScienceModes = new ArrayList<>();
        for (Double mode : scienceModes) {
            formattedScienceModes.add(Double.parseDouble(df.format(mode)));
        }

        // Calculate the frequency of each weather correct percentage
        for (double percentage : correctWeatherPercentages) {
            frequencyMapWeather.put(percentage, frequencyMapWeather.getOrDefault(percentage, 0) + 1);
        }

        // Find the mode (correct percentages with the highest frequency)
        int maxWeatherFrequency = 0;

        for (Map.Entry<Double, Integer> entry : frequencyMapWeather.entrySet()) {
            if (entry.getValue() > maxWeatherFrequency) {
                maxWeatherFrequency = entry.getValue();
                weatherModes.clear(); // Clear previous modes
                weatherModes.add(entry.getKey()); // Set the new mode
            } else if (entry.getValue() == maxWeatherFrequency) {
                weatherModes.add(entry.getKey()); // Add to modes if it has the same frequency as maxFrequency
            }
        }
        // If all elements occur an equal amount of times
        if (weatherModes.size() == frequencyMapWeather.size()) {
            weatherModes.clear();
            weatherModes.add(-1.0); // Set it to -1 to indicate no distinct modes
        }
        // Format each mode in the list
        ArrayList<Double> formattedWeatherModes = new ArrayList<>();
        for (Double mode : weatherModes) {
            formattedWeatherModes.add(Double.parseDouble(df.format(mode)));
        }

        // Calculate the frequency of each entertainment correct percentage
        for (double percentage : correctEntertainmentPercentages) {
            frequencyMapEntertainment.put(percentage, frequencyMapEntertainment.getOrDefault(percentage, 0) + 1);
        }
        // Find the mode (correct percentages with the highest frequency)
        int maxFrequency = 0;
        for (Map.Entry<Double, Integer> entry : frequencyMapEntertainment.entrySet()) {
            if (entry.getValue() > maxFrequency) {
                maxFrequency = entry.getValue();
                entertainmentModes.clear(); // Clear previous modes
                entertainmentModes.add(entry.getKey()); // Set the new mode
            } else if (entry.getValue() == maxFrequency) {
                entertainmentModes.add(entry.getKey()); // Add to modes if it has the same frequency as maxFrequency
            }
        }
        // If all elements occur an equal amount of times
        if (entertainmentModes.size() == frequencyMapEntertainment.size()) {
            entertainmentModes.clear();
            entertainmentModes.add(-1.0); // Set it to -1 to indicate no distinct modes
        }
        // Format each mode in the list
        ArrayList<Double> formattedEntertainmentModes = new ArrayList<>();
        for (Double mode : entertainmentModes) {
            formattedEntertainmentModes.add(Double.parseDouble(df.format(mode)));
        }

        // Calculate the standard deviation of correct custom percentages
        Double sumOfSquaredDifferences = 0.0;
        for (Double percentage : correctCustomPercentages) {
            Double diff = percentage - meanCorrectCustomPercentage;
            sumOfSquaredDifferences += diff * diff;
        }
        Double standardDeviationCustom = Math.sqrt(sumOfSquaredDifferences / correctCustomPercentages.size());

        sumOfSquaredDifferences = 0.0;
        for (Double percentage : correctSportsPercentages) {
            Double diff = percentage - meanCorrectSportsPercentage;
            sumOfSquaredDifferences += diff * diff;
        }
        Double standardDeviationSports = Math.sqrt(sumOfSquaredDifferences / correctSportsPercentages.size());

        sumOfSquaredDifferences = 0.0;
        for (Double percentage : correctSciencePercentages) {
            Double diff = percentage - meanCorrectSciencePercentage;
            sumOfSquaredDifferences += diff * diff;
        }
        Double standardDeviationScience = Math.sqrt(sumOfSquaredDifferences / correctSciencePercentages.size());

        sumOfSquaredDifferences = 0.0;
        for (Double percentage : correctWeatherPercentages) {
            Double diff = percentage - meanCorrectWeatherPercentage;
            sumOfSquaredDifferences += diff * diff;
        }
        Double standardDeviationWeather = Math.sqrt(sumOfSquaredDifferences / correctWeatherPercentages.size());

        sumOfSquaredDifferences = 0.0;
        for (Double percentage : correctEntertainmentPercentages) {
            Double diff = percentage - meanCorrectEntertainmentPercentage;
            sumOfSquaredDifferences += diff * diff;
        }
        Double standardDeviationEntertainment = Math.sqrt(sumOfSquaredDifferences / correctEntertainmentPercentages.size());

        // Format the calculated values
        String formattedCustomMeanCorrectPercentage = df.format(meanCorrectCustomPercentage);
        String formattedCustomStandardDeviation = df.format(standardDeviationCustom);
        String formattedSportsMeanCorrectPercentage = df.format(meanCorrectSportsPercentage);
        String formattedSportsStandardDeviation = df.format(standardDeviationSports);
        String formattedScienceMeanCorrectPercentage = df.format(meanCorrectSciencePercentage);
        String formattedScienceStandardDeviation = df.format(standardDeviationScience);
        String formattedWeatherMeanCorrectPercentage = df.format(meanCorrectWeatherPercentage);
        String formattedWeatherStandardDeviation = df.format(standardDeviationWeather);
        String formattedEntertainmentMeanCorrectPercentage = df.format(meanCorrectEntertainmentPercentage);
        String formattedEntertainmentStandardDeviation = df.format(standardDeviationEntertainment);

        // Set the mean of correct percentages of each category
        overallInferentialStatistics.setMeanCorrectPercentageCustom(Double.parseDouble(formattedCustomMeanCorrectPercentage));
        overallInferentialStatistics.setMeanCorrectPercentageSports(Double.parseDouble(formattedSportsMeanCorrectPercentage));
        overallInferentialStatistics.setMeanCorrectPercentageScience(Double.parseDouble(formattedScienceMeanCorrectPercentage));
        overallInferentialStatistics.setMeanCorrectPercentageWeather(Double.parseDouble(formattedWeatherMeanCorrectPercentage));
        overallInferentialStatistics.setMeanCorrectPercentageEntertainment(Double.parseDouble(formattedEntertainmentMeanCorrectPercentage));

        // Set the mode value of each category as the formatted array list if it exists
        if (!formattedCustomModes.isEmpty() && !formattedCustomModes.get(0).equals(-1.0)) {
            overallInferentialStatistics.setModeCorrectPercentageCustom(formattedCustomModes);
        } else {
            ArrayList<Double> noCustomModes = new ArrayList<>();
            noCustomModes.add(Double.NaN);
            overallInferentialStatistics.setModeCorrectPercentageCustom(noCustomModes);
        }
        if (!formattedSportsModes.isEmpty() && !formattedSportsModes.get(0).equals(-1.0)) {
            overallInferentialStatistics.setModeCorrectPercentageSports(formattedSportsModes);
        } else {
            ArrayList<Double> noSportsModes = new ArrayList<>();
            noSportsModes.add(Double.NaN);
            overallInferentialStatistics.setModeCorrectPercentageSports(noSportsModes);
        }
        if (!formattedScienceModes.isEmpty() && !formattedScienceModes.get(0).equals(-1.0)) {
            overallInferentialStatistics.setModeCorrectPercentageScience(formattedScienceModes);
        } else {
            ArrayList<Double> noScienceModes = new ArrayList<>();
            noScienceModes.add(Double.NaN);
            overallInferentialStatistics.setModeCorrectPercentageScience(noScienceModes);
        }
        if (!formattedWeatherModes.isEmpty() && !formattedWeatherModes.get(0).equals(-1.0)) {
            overallInferentialStatistics.setModeCorrectPercentageWeather(formattedWeatherModes);
        } else {
            ArrayList<Double> noWeatherModes = new ArrayList<>();
            noWeatherModes.add(Double.NaN);
            overallInferentialStatistics.setModeCorrectPercentageWeather(noWeatherModes);
        }
        if (!formattedEntertainmentModes.isEmpty() && !formattedEntertainmentModes.get(0).equals(-1.0)) {
            overallInferentialStatistics.setModeCorrectPercentageEntertainment(formattedEntertainmentModes);
        } else {
            ArrayList<Double> noEntertainmentModes = new ArrayList<>();
            noEntertainmentModes.add(Double.NaN);
            overallInferentialStatistics.setModeCorrectPercentageEntertainment(noEntertainmentModes);
        }

        // Format and set the median value to two decimal places
        overallInferentialStatistics.setMedianCorrectPercentageCustom(Double.parseDouble(df.format(medianCustomCorrectPercentage)));
        overallInferentialStatistics.setMedianCorrectPercentageSports(Double.parseDouble(df.format(medianSportsCorrectPercentage)));
        overallInferentialStatistics.setMedianCorrectPercentageScience(Double.parseDouble(df.format(medianScienceCorrectPercentage)));
        overallInferentialStatistics.setMedianCorrectPercentageWeather(Double.parseDouble(df.format(medianWeatherCorrectPercentage)));
        overallInferentialStatistics.setMedianCorrectPercentageEntertainment(Double.parseDouble(df.format(medianEntertainmentCorrectPercentage)));

        // Set standard deviation of the percentage of correct predictions for each category
        overallInferentialStatistics.setStandardDeviationCorrectPercentageCustom(Double.parseDouble(formattedCustomStandardDeviation));
        overallInferentialStatistics.setStandardDeviationCorrectPercentageSports(Double.parseDouble(formattedSportsStandardDeviation));
        overallInferentialStatistics.setStandardDeviationCorrectPercentageScience(Double.parseDouble(formattedScienceStandardDeviation));
        overallInferentialStatistics.setStandardDeviationCorrectPercentageWeather(Double.parseDouble(formattedWeatherStandardDeviation));
        overallInferentialStatistics.setStandardDeviationCorrectPercentageEntertainment(Double.parseDouble(formattedEntertainmentStandardDeviation));

        // Set overall prediction counts for each category
        overallInferentialStatistics.setCustomPredictionCount(overallCustomPredictionCount);
        overallInferentialStatistics.setSportsPredictionCount(overallSportsPredictionCount);
        overallInferentialStatistics.setSciencePredictionCount(overallSciencePredictionCount);
        overallInferentialStatistics.setWeatherPredictionCount(overallWeatherPredictionCount);
        overallInferentialStatistics.setEntertainmentPredictionCount(overallEntertainmentPredictionCount);

        // Set overall correct and incorrect prediction counts for each category
        overallInferentialStatistics.setCustomCorrectPredictions(overallCustomCorrectPredictionCount);
        overallInferentialStatistics.setCustomIncorrectPredictions(overallCustomPredictionCount - overallCustomCorrectPredictionCount);
        overallInferentialStatistics.setSportsCorrectPredictions(overallSportsCorrectPredictionCount);
        overallInferentialStatistics.setSportsIncorrectPredictions(overallSportsPredictionCount - overallSportsCorrectPredictionCount);
        overallInferentialStatistics.setScienceCorrectPredictions(overallScienceCorrectPredictionCount);
        overallInferentialStatistics.setScienceIncorrectPredictions(overallSciencePredictionCount - overallScienceCorrectPredictionCount);
        overallInferentialStatistics.setWeatherCorrectPredictions(overallWeatherCorrectPredictionCount);
        overallInferentialStatistics.setWeatherIncorrectPredictions(overallWeatherPredictionCount - overallWeatherCorrectPredictionCount);
        overallInferentialStatistics.setEntertainmentCorrectPredictions(overallEntertainmentCorrectPredictionCount);
        overallInferentialStatistics.setEntertainmentIncorrectPredictions(overallEntertainmentPredictionCount - overallEntertainmentCorrectPredictionCount);

        // Calculate percentage of overall correct predictions (overall correct predictions count / overall prediction count) for each category
        Double overallCustomPercentCorrect;
        Double overallCustomPercentIncorrect;
        if (overallInferentialStatistics.getCustomPredictionCount() > 0) {
            overallCustomPercentCorrect = (double) overallCustomCorrectPredictionCount / overallCustomPredictionCount * 100.0;
            overallCustomPercentIncorrect = 100.0 - overallCustomPercentCorrect;
        } else {
            overallCustomPercentCorrect = Double.NaN;
            overallCustomPercentIncorrect = Double.NaN;
        }
        Double overallSportsPercentCorrect;
        Double overallSportsPercentIncorrect;
        if (overallInferentialStatistics.getSportsPredictionCount() > 0) {
            overallSportsPercentCorrect = (double) overallSportsCorrectPredictionCount / overallSportsPredictionCount * 100.0;
            overallSportsPercentIncorrect = 100.0 - overallSportsPercentCorrect;
        } else {
            overallSportsPercentCorrect = Double.NaN;
            overallSportsPercentIncorrect = Double.NaN;
        }
        Double overallSciencePercentCorrect;
        Double overallSciencePercentIncorrect;
        if (overallInferentialStatistics.getSciencePredictionCount() > 0) {
            overallSciencePercentCorrect = (double) overallScienceCorrectPredictionCount / overallSciencePredictionCount * 100.0;
            overallSciencePercentIncorrect = 100.0 - overallSciencePercentCorrect;
        } else {
            overallSciencePercentCorrect = Double.NaN;
            overallSciencePercentIncorrect = Double.NaN;
        }
        Double overallWeatherPercentCorrect;
        Double overallWeatherPercentIncorrect;
        if (overallInferentialStatistics.getWeatherPredictionCount() > 0) {
            overallWeatherPercentCorrect = (double) overallWeatherCorrectPredictionCount / overallWeatherPredictionCount * 100.0;
            overallWeatherPercentIncorrect = 100.0 - overallWeatherPercentCorrect;
        } else {
            overallWeatherPercentCorrect = Double.NaN;
            overallWeatherPercentIncorrect = Double.NaN;
        }
        Double overallEntertainmentPercentCorrect;
        Double overallEntertainmentPercentIncorrect;
        if (overallInferentialStatistics.getEntertainmentPredictionCount() > 0) {
            overallEntertainmentPercentCorrect = (double) overallEntertainmentCorrectPredictionCount / overallEntertainmentPredictionCount * 100.0;
            overallEntertainmentPercentIncorrect = 100.0 - overallEntertainmentPercentCorrect;
        } else {
            overallEntertainmentPercentCorrect = Double.NaN;
            overallEntertainmentPercentIncorrect = Double.NaN;
        }

        // Format and set the percentage values to two decimal places
        overallInferentialStatistics.setCustomPercentCorrect(Double.parseDouble(df.format(overallCustomPercentCorrect)));
        overallInferentialStatistics.setCustomPercentIncorrect(Double.parseDouble(df.format(overallCustomPercentIncorrect)));
        overallInferentialStatistics.setSportsPercentCorrect(Double.parseDouble(df.format(overallSportsPercentCorrect)));
        overallInferentialStatistics.setSportsPercentIncorrect(Double.parseDouble(df.format(overallSportsPercentIncorrect)));
        overallInferentialStatistics.setSciencePercentCorrect(Double.parseDouble(df.format(overallSciencePercentCorrect)));
        overallInferentialStatistics.setSciencePercentIncorrect(Double.parseDouble(df.format(overallSciencePercentIncorrect)));
        overallInferentialStatistics.setWeatherPercentCorrect(Double.parseDouble(df.format(overallWeatherPercentCorrect)));
        overallInferentialStatistics.setWeatherPercentIncorrect(Double.parseDouble(df.format(overallWeatherPercentIncorrect)));
        overallInferentialStatistics.setEntertainmentPercentCorrect(Double.parseDouble(df.format(overallEntertainmentPercentCorrect)));
        overallInferentialStatistics.setEntertainmentPercentIncorrect(Double.parseDouble(df.format(overallEntertainmentPercentIncorrect)));

        // Calculate the percentages of each prediction type made out of all predictions
        Double customPredictionsMadePercent;
        if (overallCustomPredictionCount > 0) {
            customPredictionsMadePercent = ((double) overallInferentialStatistics.getCustomPredictionCount() / overallInferentialStatistics.getPredictionsMadeCount()) * 100;
            String formattedCustomPredictionsMadePercent = df.format(customPredictionsMadePercent);
            customPredictionsMadePercent = Double.parseDouble(formattedCustomPredictionsMadePercent);
        } else {
            customPredictionsMadePercent = 0.0;
        }
        Double sportsPredictionsMadePercent;
        if (overallSportsPredictionCount > 0) {
            sportsPredictionsMadePercent = ((double) overallInferentialStatistics.getSportsPredictionCount() / overallInferentialStatistics.getPredictionsMadeCount()) * 100;
            String formattedSportsPredictionsMadePercent = df.format(sportsPredictionsMadePercent);
            sportsPredictionsMadePercent = Double.parseDouble(formattedSportsPredictionsMadePercent);
        } else {
            sportsPredictionsMadePercent = 0.0;
        }
        Double sciencePredictionsMadePercent;
        if (overallSciencePredictionCount > 0) {
            sciencePredictionsMadePercent = ((double) overallInferentialStatistics.getSciencePredictionCount() / overallInferentialStatistics.getPredictionsMadeCount()) * 100;
            String formattedSciencePredictionsMadePercent = df.format(sciencePredictionsMadePercent);
            sciencePredictionsMadePercent = Double.parseDouble(formattedSciencePredictionsMadePercent);
        } else {
            sciencePredictionsMadePercent = 0.0;
        }
        Double weatherPredictionsMadePercent;
        if (overallWeatherPredictionCount > 0) {
            weatherPredictionsMadePercent = ((double) overallInferentialStatistics.getWeatherPredictionCount() / overallInferentialStatistics.getPredictionsMadeCount()) * 100;
            String formattedWeatherPredictionsMadePercent = df.format(weatherPredictionsMadePercent);
            weatherPredictionsMadePercent = Double.parseDouble(formattedWeatherPredictionsMadePercent);
        } else {
            weatherPredictionsMadePercent = 0.0;
        }
        Double entertainmentPredictionsMadePercent;
        if (overallEntertainmentPredictionCount > 0) {
            entertainmentPredictionsMadePercent = ((double) overallInferentialStatistics.getEntertainmentPredictionCount() / overallInferentialStatistics.getPredictionsMadeCount()) * 100;
            String formattedEntertainmentPredictionsMadePercent = df.format(entertainmentPredictionsMadePercent);
            entertainmentPredictionsMadePercent = Double.parseDouble(formattedEntertainmentPredictionsMadePercent);
        } else {
            entertainmentPredictionsMadePercent = 0.0;
        }

        // Set the percentages of each prediction type made
        overallInferentialStatistics.setCustomPercentPredictionsMade(customPredictionsMadePercent);
        overallInferentialStatistics.setSportsPercentPredictionsMade(sportsPredictionsMadePercent);
        overallInferentialStatistics.setSciencePercentPredictionsMade(sciencePredictionsMadePercent);
        overallInferentialStatistics.setWeatherPercentPredictionsMade(weatherPredictionsMadePercent);
        overallInferentialStatistics.setEntertainmentPercentPredictionsMade(entertainmentPredictionsMadePercent);

        // Initialize String for mostCorrectPredictionType and leastCorrectPredictionType
        String greatestCorrectPredictionType = "No Predictions Made Yet";
        String leastCorrectPredictionType = "No Predictions Made Yet";

        // Get the user's most correct prediction type
        if (overallInferentialStatistics.getCustomPredictionCount() > 0) {
            greatestCorrectPredictionType = "Custom";
        }
        if (overallInferentialStatistics.getSportsPredictionCount() > 0 && overallSportsPercentCorrect > overallCustomPercentCorrect) {
            greatestCorrectPredictionType = "Sports";
        }
        if (overallInferentialStatistics.getSciencePredictionCount() > 0 && overallSciencePercentCorrect > overallCustomPercentCorrect && overallSciencePercentCorrect > overallSportsPercentCorrect) {
            greatestCorrectPredictionType = "Science";
        }
        if (overallInferentialStatistics.getWeatherPredictionCount() > 0 && overallWeatherPercentCorrect > overallCustomPercentCorrect && overallWeatherPercentCorrect > overallSportsPercentCorrect && overallWeatherPercentCorrect > overallSciencePercentCorrect) {
            greatestCorrectPredictionType = "Weather";
        }
        if (overallInferentialStatistics.getEntertainmentPredictionCount() > 0 && overallEntertainmentPercentCorrect > overallCustomPercentCorrect && overallEntertainmentPercentCorrect > overallSportsPercentCorrect && overallEntertainmentPercentCorrect > overallSciencePercentCorrect && overallEntertainmentPercentCorrect > overallWeatherPercentCorrect) {
            greatestCorrectPredictionType = "Entertainment";
        }

        // Get the user's least correct prediction type
        if (overallInferentialStatistics.getCustomPredictionCount() > 0) {
            leastCorrectPredictionType = "Custom";
        }
        if (overallInferentialStatistics.getSportsPredictionCount() > 0 && overallSportsPercentIncorrect > overallCustomPercentIncorrect) {
            leastCorrectPredictionType = "Sports";
        }
        if (overallInferentialStatistics.getSciencePredictionCount() > 0 && overallSciencePercentIncorrect > overallCustomPercentIncorrect && overallSciencePercentIncorrect > overallSportsPercentIncorrect) {
            leastCorrectPredictionType = "Science";
        }
        if (overallInferentialStatistics.getWeatherPredictionCount() > 0 && overallWeatherPercentIncorrect > overallCustomPercentIncorrect && overallWeatherPercentIncorrect > overallSportsPercentIncorrect && overallWeatherPercentIncorrect > overallSciencePercentIncorrect) {
            leastCorrectPredictionType = "Weather";
        }
        if (overallInferentialStatistics.getEntertainmentPredictionCount() > 0 && overallEntertainmentPercentIncorrect > overallCustomPercentIncorrect && overallEntertainmentPercentIncorrect > overallSportsPercentIncorrect && overallEntertainmentPercentIncorrect > overallSciencePercentIncorrect && overallEntertainmentPercentIncorrect > overallWeatherPercentIncorrect) {
            leastCorrectPredictionType = "Entertainment";
        }

        // Set the most correct prediction type and least correct prediction type
        overallInferentialStatistics.setMostCorrectPredictionType(greatestCorrectPredictionType);
        overallInferentialStatistics.setLeastCorrectPredictionType(leastCorrectPredictionType);

        // Save overall inferential statistics
        MongoDBOverallInferentialStatistics.updateDocument(overallInferentialStatistics);
    }


    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // loadAndDisplayOverallInferentialStatistics
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Loads the overall inferential statistics file and displays the data to the console.
    //
    public static void loadAndDisplayOverallInferentialStatistics() {
        // Initialize the resolvedFilePath using the userIdentifier parameter
        String overallInferentialStatisticsFilePath = overallStatisticsFolderPath + File.separator + "overall_inferential_statistics.json";
        File overallInferentialStatisticsFile = new File(overallInferentialStatisticsFilePath);

        // Check if the file exists before attempting to load it
        if (overallInferentialStatisticsFile.exists()) {
            // Load the file and print
            try {
                String json = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(overallInferentialStatisticsFilePath)));
                Gson gson = new Gson();
                Type listType = new TypeToken<OverallInferentialStatistics>() {}.getType();
                OverallInferentialStatistics loadedOverallInferentialStatistics = gson.fromJson(json, listType);
                System.out.println("====================================================================================================");
                System.out.println("                                 Overall UserInfo.User Inferential Statistics");
                loadedOverallInferentialStatistics.printOverallInferentialStatistics();
                System.out.println();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("ERROR - Overall inferential statistics file does not exist.\n");
        }
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // loadOverallInferentialStatistics
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Loads the overall inferential statistics file and displays the data to the console.
    //
    public static OverallInferentialStatistics loadOverallInferentialStatistics() {
        // Initialize the resolvedFilePath using the userIdentifier parameter
        String overallInferentialStatisticsFilePath = overallStatisticsFolderPath + File.separator + "overall_inferential_statistics.json";
        File overallInferentialStatisticsFile = new File(overallInferentialStatisticsFilePath);

        // Check if the file exists before attempting to load it
        if (overallInferentialStatisticsFile.exists()) {
            // Load the file and print
            try {
                String json = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(overallInferentialStatisticsFilePath)));
                Gson gson = new Gson();
                Type listType = new TypeToken<OverallInferentialStatistics>() {}.getType();
                OverallInferentialStatistics loadedOverallStatistics = gson.fromJson(json, listType);
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
            System.out.println("ERROR - Overall inferential statistics file does not exist.\n");
        }
        return null;
    }
}
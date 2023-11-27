package backend.UserStatistics;

import backend.ResolvedPredictions.ResolvedPrediction;
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

//■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
// UserStatistics.UserInferentialStatisticsUpdater class - Written by Brandon LaPointe
//■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
// Initializes the file path and folders needed to save the UserStatistics.UserInferentialStatistics data. Allows
// for calculation and saving of the user inferential statistics and the loading and displaying of
// the user inferential statistics files to the console.
//
public class UserInferentialStatisticsUpdater {
    private static final String userHome = System.getProperty("user.home");
    private static final String resolvedPredictionFolderPath = userHome + File.separator + "EnvisionaryApp" + File.separator + "ResolvedPredictions";
    private static final String userInferentialStatisticsFolderPath = userHome + File.separator + "EnvisionaryApp" + File.separator + "UserStatistics" + File.separator + "Inferential";

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // initializeFilePath
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Initializes the file path and folders needed to save the user's inferential statistics.
    //
    public static void initializeFilePath() {
        // Create the directory if it doesn't exist
        File directory = new File(userInferentialStatisticsFolderPath);
        if (!directory.exists()) {
            if (directory.mkdirs()) {
                System.out.println("Created directory: " + userInferentialStatisticsFolderPath);
            } else {
                System.err.println("Failed to create directory: " + userInferentialStatisticsFolderPath);
            }
        }
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // calculateAndSaveUserInferentialStatistics
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Calculates the user's inferential statistics by going through the user's resolved predictions file
    // and collecting values of total predictions and correct predictions for each prediction category
    // before calculating the percentages of correct and incorrect resolved predictions of each category
    // and giving the user their most correct prediction type and most incorrect prediction type and
    // saving to the specified user inferential statistics file path.
    //
    public static void calculateAndSaveUserInferentialStatistics(String userIdentifier) {
        // Initialize an array list of resolved predictions to store the user's resolved predictions
        ArrayList<ResolvedPrediction> loadedResolvedPredictions = new ArrayList<>();

        // Check if the file exists before attempting to load it
        String userResolvedPredictionsFilePath = resolvedPredictionFolderPath + File.separator + userIdentifier + "_resolved_predictions.json";
        File resolvedUserPredictionsFile = new File(userResolvedPredictionsFilePath);

        // Initialize a GsonBuilder with support for special floating-point values
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.serializeSpecialFloatingPointValues();

        // Check if the file exists before attempting to load it
        if (resolvedUserPredictionsFile.exists()) {
            // Load the file
            try {
                String json = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(userResolvedPredictionsFilePath)));
                Gson gson = new GsonBuilder().create();
                Type listType = new TypeToken<ArrayList<ResolvedPrediction>>() {}.getType();
                loadedResolvedPredictions = gson.fromJson(json, listType);
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Initialize a new UserStatistics.UserInferentialStatistics
            UserInferentialStatistics userInferentialStatistics = new UserInferentialStatistics();

            // For each resolved prediction within the file, assign type and gather correct, incorrect, and total prediction counts
            for (ResolvedPrediction resolvedPrediction : loadedResolvedPredictions) {
                if (resolvedPrediction.getPredictionType().equalsIgnoreCase("Custom")) {
                    if (resolvedPrediction.getResolution()) {
                        userInferentialStatistics.setCustomCorrectPredictions(userInferentialStatistics.getCustomCorrectPredictions() + 1);
                        userInferentialStatistics.setCustomPredictionCount(userInferentialStatistics.getCustomPredictionCount() + 1);
                        userInferentialStatistics.setPredictionsMadeCount(userInferentialStatistics.getPredictionsMadeCount() + 1);
                    } else {
                        userInferentialStatistics.setCustomIncorrectPredictions(userInferentialStatistics.getCustomIncorrectPredictions() + 1);
                        userInferentialStatistics.setCustomPredictionCount(userInferentialStatistics.getCustomPredictionCount() + 1);
                        userInferentialStatistics.setPredictionsMadeCount(userInferentialStatistics.getPredictionsMadeCount() + 1);
                    }
                }
                else if (resolvedPrediction.getPredictionType().equalsIgnoreCase("Sports") || resolvedPrediction.getPredictionType().equalsIgnoreCase("FootballMatchPredictions.FootballMatch")) {
                    if (resolvedPrediction.getResolution()) {
                        userInferentialStatistics.setSportsCorrectPredictions(userInferentialStatistics.getSportsCorrectPredictions() + 1);
                        userInferentialStatistics.setSportsPredictionCount(userInferentialStatistics.getSportsPredictionCount() + 1);
                        userInferentialStatistics.setPredictionsMadeCount(userInferentialStatistics.getPredictionsMadeCount() + 1);
                    } else {
                        userInferentialStatistics.setSportsIncorrectPredictions(userInferentialStatistics.getSportsIncorrectPredictions() + 1);
                        userInferentialStatistics.setSportsPredictionCount(userInferentialStatistics.getSportsPredictionCount() + 1);
                        userInferentialStatistics.setPredictionsMadeCount(userInferentialStatistics.getPredictionsMadeCount() + 1);
                    }
                }
                else if (resolvedPrediction.getPredictionType().equalsIgnoreCase("Science")) {
                    if (resolvedPrediction.getResolution()) {
                        userInferentialStatistics.setScienceCorrectPredictions(userInferentialStatistics.getScienceCorrectPredictions() + 1);
                        userInferentialStatistics.setSciencePredictionCount(userInferentialStatistics.getSciencePredictionCount() + 1);
                        userInferentialStatistics.setPredictionsMadeCount(userInferentialStatistics.getPredictionsMadeCount() + 1);
                    } else {
                        userInferentialStatistics.setScienceIncorrectPredictions(userInferentialStatistics.getScienceIncorrectPredictions() + 1);
                        userInferentialStatistics.setSciencePredictionCount(userInferentialStatistics.getSciencePredictionCount() + 1);
                        userInferentialStatistics.setPredictionsMadeCount(userInferentialStatistics.getPredictionsMadeCount() + 1);
                    }
                }
                else if (resolvedPrediction.getPredictionType().equalsIgnoreCase("Weather")) {
                    if (resolvedPrediction.getResolution()) {
                        userInferentialStatistics.setWeatherCorrectPredictions(userInferentialStatistics.getWeatherCorrectPredictions() + 1);
                        userInferentialStatistics.setWeatherPredictionCount(userInferentialStatistics.getWeatherPredictionCount() + 1);
                        userInferentialStatistics.setPredictionsMadeCount(userInferentialStatistics.getPredictionsMadeCount() + 1);
                    } else {
                        userInferentialStatistics.setWeatherIncorrectPredictions(userInferentialStatistics.getWeatherIncorrectPredictions() + 1);
                        userInferentialStatistics.setWeatherPredictionCount(userInferentialStatistics.getWeatherPredictionCount() + 1);
                        userInferentialStatistics.setPredictionsMadeCount(userInferentialStatistics.getPredictionsMadeCount() + 1);
                    }
                }
                else if (resolvedPrediction.getPredictionType().equalsIgnoreCase("Entertainment")) {
                    if (resolvedPrediction.getResolution()) {
                        userInferentialStatistics.setEntertainmentCorrectPredictions(userInferentialStatistics.getEntertainmentCorrectPredictions() + 1);
                        userInferentialStatistics.setEntertainmentPredictionCount(userInferentialStatistics.getEntertainmentPredictionCount() + 1);
                        userInferentialStatistics.setPredictionsMadeCount(userInferentialStatistics.getPredictionsMadeCount() + 1);
                    } else {
                        userInferentialStatistics.setEntertainmentIncorrectPredictions(userInferentialStatistics.getEntertainmentIncorrectPredictions() + 1);
                        userInferentialStatistics.setEntertainmentPredictionCount(userInferentialStatistics.getEntertainmentPredictionCount() + 1);
                        userInferentialStatistics.setPredictionsMadeCount(userInferentialStatistics.getPredictionsMadeCount() + 1);
                    }
                }
                else {
                    System.out.println("ERROR - UserStatistics.UserInferentialStatisticsUpdater cannot match the type of the user's resolved prediction: " + resolvedPrediction.getPredictionType());
                }
            }

            // Calculate the percentage of correct and incorrect custom predictions
            Double percentCorrectCustom;
            Double percentIncorrectCustom;

            if (userInferentialStatistics.getCustomPredictionCount() > 0) {
                percentCorrectCustom = ((double) userInferentialStatistics.getCustomCorrectPredictions() / userInferentialStatistics.getCustomPredictionCount()) * 100.0;
                percentIncorrectCustom = 100.0 - percentCorrectCustom;
            } else {
                percentCorrectCustom = Double.NaN;
                percentIncorrectCustom = Double.NaN;
            }

            // Calculate the percentage of correct and incorrect sports predictions
            Double percentCorrectSports;
            Double percentIncorrectSports;

            if (userInferentialStatistics.getSportsPredictionCount() > 0) {
                percentCorrectSports = ((double) userInferentialStatistics.getSportsCorrectPredictions() / userInferentialStatistics.getSportsPredictionCount()) * 100.0;
                percentIncorrectSports = 100.0 - percentCorrectSports;
            } else {
                percentCorrectSports = Double.NaN;
                percentIncorrectSports = Double.NaN;
            }

            // Calculate the percentage of correct and incorrect science predictions
            Double percentCorrectScience;
            Double percentIncorrectScience;

            if (userInferentialStatistics.getSciencePredictionCount() > 0) {
                percentCorrectScience = ((double) userInferentialStatistics.getScienceCorrectPredictions() / userInferentialStatistics.getSciencePredictionCount()) * 100.0;
                percentIncorrectScience = 100.0 - percentCorrectScience;
            } else {
                percentCorrectScience = Double.NaN;
                percentIncorrectScience = Double.NaN;
            }

            // Calculate the percentage of correct and incorrect weather predictions
            Double percentCorrectWeather;
            Double percentIncorrectWeather;

            if (userInferentialStatistics.getWeatherPredictionCount() > 0) {
                percentCorrectWeather = ((double) userInferentialStatistics.getWeatherCorrectPredictions() / userInferentialStatistics.getWeatherPredictionCount()) * 100.0;
                percentIncorrectWeather = 100.0 - percentCorrectWeather;
            } else {
                percentCorrectWeather = Double.NaN;
                percentIncorrectWeather = Double.NaN;
            }

            // Calculate the percentage of correct and incorrect entertainment predictions
            Double percentCorrectEntertainment;
            Double percentIncorrectEntertainment;

            if (userInferentialStatistics.getEntertainmentPredictionCount() > 0) {
                percentCorrectEntertainment = ((double) userInferentialStatistics.getEntertainmentCorrectPredictions() / userInferentialStatistics.getEntertainmentPredictionCount()) * 100.0;
                percentIncorrectEntertainment = 100.0 - percentCorrectEntertainment;
            } else {
                percentCorrectEntertainment = Double.NaN;
                percentIncorrectEntertainment = Double.NaN;
            }


            // Format and set the percentage values to two decimal places
            DecimalFormat df = new DecimalFormat("0.00");
            userInferentialStatistics.setCustomPercentCorrect(Double.parseDouble(df.format(percentCorrectCustom)));
            userInferentialStatistics.setCustomPercentIncorrect(Double.parseDouble(df.format(percentIncorrectCustom)));
            userInferentialStatistics.setSportsPercentCorrect(Double.parseDouble(df.format(percentCorrectSports)));
            userInferentialStatistics.setSportsPercentIncorrect(Double.parseDouble(df.format(percentIncorrectSports)));
            userInferentialStatistics.setSciencePercentCorrect(Double.parseDouble(df.format(percentCorrectScience)));
            userInferentialStatistics.setSciencePercentIncorrect(Double.parseDouble(df.format(percentIncorrectScience)));
            userInferentialStatistics.setWeatherPercentCorrect(Double.parseDouble(df.format(percentCorrectWeather)));
            userInferentialStatistics.setWeatherPercentIncorrect(Double.parseDouble(df.format(percentIncorrectWeather)));
            userInferentialStatistics.setEntertainmentPercentCorrect(Double.parseDouble(df.format(percentCorrectEntertainment)));
            userInferentialStatistics.setEntertainmentPercentIncorrect(Double.parseDouble(df.format(percentIncorrectEntertainment)));

            // Calculate the percentages of each prediction type made
            Double customPredictionsMadePercent = ((double) userInferentialStatistics.getCustomPredictionCount() / userInferentialStatistics.getPredictionsMadeCount()) * 100;
            Double sportsPredictionsMadePercent = ((double) userInferentialStatistics.getSportsPredictionCount() / userInferentialStatistics.getPredictionsMadeCount()) * 100;
            Double sciencePredictionsMadePercent = ((double) userInferentialStatistics.getSciencePredictionCount() / userInferentialStatistics.getPredictionsMadeCount()) * 100;
            Double weatherPredictionsMadePercent = ((double) userInferentialStatistics.getWeatherPredictionCount() / userInferentialStatistics.getPredictionsMadeCount()) * 100;
            Double entertainmentPredictionsMadePercent = ((double) userInferentialStatistics.getEntertainmentPredictionCount() / userInferentialStatistics.getPredictionsMadeCount()) * 100;

            // Set the percentages of each prediction type made
            userInferentialStatistics.setCustomPercentPredictionsMade(Double.parseDouble(df.format(customPredictionsMadePercent)));
            userInferentialStatistics.setSportsPercentPredictionsMade(Double.parseDouble(df.format(sportsPredictionsMadePercent)));
            userInferentialStatistics.setSciencePercentPredictionsMade(Double.parseDouble(df.format(sciencePredictionsMadePercent)));
            userInferentialStatistics.setWeatherPercentPredictionsMade(Double.parseDouble(df.format(weatherPredictionsMadePercent)));
            userInferentialStatistics.setEntertainmentPercentPredictionsMade(Double.parseDouble(df.format(entertainmentPredictionsMadePercent)));

            // Initialize String for mostCorrectPredictionType and leastCorrectPredictionType
            String mostCorrectPredictionType = "No Predictions Made Yet";
            String leastCorrectPredictionType = "No Predictions Made Yet";

            // Get the user's most correct prediction type
            if (userInferentialStatistics.getCustomPredictionCount() > 0) {
                mostCorrectPredictionType = "Custom";
            }
            if (userInferentialStatistics.getSportsPredictionCount() > 0 && percentCorrectSports > percentCorrectCustom) {
                mostCorrectPredictionType = "Sports";
            }
            if (userInferentialStatistics.getSciencePredictionCount() > 0 && percentCorrectScience > percentCorrectCustom && percentCorrectScience > percentCorrectSports) {
                mostCorrectPredictionType = "Science";
            }
            if (userInferentialStatistics.getWeatherPredictionCount() > 0 && percentCorrectWeather > percentCorrectCustom && percentCorrectWeather > percentCorrectSports && percentCorrectWeather > percentCorrectScience) {
                mostCorrectPredictionType = "Weather";
            }
            if (userInferentialStatistics.getEntertainmentPredictionCount() > 0 && percentCorrectEntertainment > percentCorrectCustom && percentCorrectEntertainment > percentCorrectSports && percentCorrectEntertainment > percentCorrectScience && percentCorrectEntertainment > percentCorrectWeather) {
                mostCorrectPredictionType = "Entertainment";
            }

            // Get the user's least correct prediction type
            if (userInferentialStatistics.getCustomPredictionCount() > 0) {
                leastCorrectPredictionType = "Custom";
            }
            if (userInferentialStatistics.getSportsPredictionCount() > 0 && percentIncorrectSports > percentIncorrectCustom) {
                leastCorrectPredictionType = "Sports";
            }
            if (userInferentialStatistics.getSciencePredictionCount() > 0 && percentIncorrectScience > percentIncorrectCustom && percentIncorrectScience > percentIncorrectSports) {
                leastCorrectPredictionType = "Science";
            }
            if (userInferentialStatistics.getWeatherPredictionCount() > 0 && percentIncorrectWeather > percentIncorrectCustom && percentIncorrectWeather > percentIncorrectSports && percentIncorrectWeather > percentIncorrectScience) {
                leastCorrectPredictionType = "Weather";
            }
            if (userInferentialStatistics.getEntertainmentPredictionCount() > 0 && percentIncorrectEntertainment > percentIncorrectCustom && percentIncorrectEntertainment > percentIncorrectSports && percentIncorrectEntertainment > percentIncorrectScience && percentIncorrectEntertainment > percentIncorrectWeather) {
                leastCorrectPredictionType = "Entertainment";
            }

            // Set the most correct prediction type and least correct prediction type
            userInferentialStatistics.setMostCorrectPredictionType(mostCorrectPredictionType);
            userInferentialStatistics.setLeastCorrectPredictionType(leastCorrectPredictionType);

            // Save the user inferential statistics
            String userInferentialStatisticsFilePath = userInferentialStatisticsFolderPath + File.separator + userIdentifier + "_user_inferential_statistics.json";
            try (FileWriter writer = new FileWriter(userInferentialStatisticsFilePath)) {
                Gson gson2 = gsonBuilder.create();
                Type listType2 = new TypeToken<UserInferentialStatistics>() {}.getType();
                String json2 = gson2.toJson(userInferentialStatistics, listType2);
                writer.write(json2);
                //System.out.println("UserInfo.User inferential statistics saved successfully.");
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            System.out.println("ERROR - Resolved predictions file does not exist for user: " + userIdentifier + "\n");
        }
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // calculateAndSaveUserInferentialStatisticsMongoDB
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Calculates the user's inferential statistics by going through the user's resolved predictions file
    // and collecting values of total predictions and correct predictions for each prediction category
    // before calculating the percentages of correct and incorrect resolved predictions of each category
    // and giving the user their most correct prediction type and most incorrect prediction type and
    // saving to the specified user inferential statistics file path.
    //
    public static void calculateAndSaveUserInferentialStatisticsMongoDB(String userIdentifier) {
        // Initialize an array list of resolved predictions to store the user's resolved predictions
        ArrayList<ResolvedPrediction> loadedResolvedPredictions = MongoDBEnvisionaryUsers.retrieveUserResolvedPredictions(userIdentifier);

        // Initialize a new UserStatistics.UserInferentialStatistics
        UserInferentialStatistics userInferentialStatistics = new UserInferentialStatistics();

        // For each resolved prediction within the file, assign type and gather correct, incorrect, and total prediction counts
        for (ResolvedPrediction resolvedPrediction : loadedResolvedPredictions) {
            if (resolvedPrediction.getPredictionType().equalsIgnoreCase("Custom")) {
                if (resolvedPrediction.getResolution()) {
                    userInferentialStatistics.setCustomCorrectPredictions(userInferentialStatistics.getCustomCorrectPredictions() + 1);
                    userInferentialStatistics.setCustomPredictionCount(userInferentialStatistics.getCustomPredictionCount() + 1);
                    userInferentialStatistics.setPredictionsMadeCount(userInferentialStatistics.getPredictionsMadeCount() + 1);
                } else {
                    userInferentialStatistics.setCustomIncorrectPredictions(userInferentialStatistics.getCustomIncorrectPredictions() + 1);
                    userInferentialStatistics.setCustomPredictionCount(userInferentialStatistics.getCustomPredictionCount() + 1);
                    userInferentialStatistics.setPredictionsMadeCount(userInferentialStatistics.getPredictionsMadeCount() + 1);
                }
            }
            else if (resolvedPrediction.getPredictionType().equalsIgnoreCase("Sports") || resolvedPrediction.getPredictionType().equalsIgnoreCase("FootballMatchPredictions.FootballMatch")) {
                if (resolvedPrediction.getResolution()) {
                    userInferentialStatistics.setSportsCorrectPredictions(userInferentialStatistics.getSportsCorrectPredictions() + 1);
                    userInferentialStatistics.setSportsPredictionCount(userInferentialStatistics.getSportsPredictionCount() + 1);
                    userInferentialStatistics.setPredictionsMadeCount(userInferentialStatistics.getPredictionsMadeCount() + 1);
                } else {
                    userInferentialStatistics.setSportsIncorrectPredictions(userInferentialStatistics.getSportsIncorrectPredictions() + 1);
                    userInferentialStatistics.setSportsPredictionCount(userInferentialStatistics.getSportsPredictionCount() + 1);
                    userInferentialStatistics.setPredictionsMadeCount(userInferentialStatistics.getPredictionsMadeCount() + 1);
                }
            }
            else if (resolvedPrediction.getPredictionType().equalsIgnoreCase("Science")) {
                if (resolvedPrediction.getResolution()) {
                    userInferentialStatistics.setScienceCorrectPredictions(userInferentialStatistics.getScienceCorrectPredictions() + 1);
                    userInferentialStatistics.setSciencePredictionCount(userInferentialStatistics.getSciencePredictionCount() + 1);
                    userInferentialStatistics.setPredictionsMadeCount(userInferentialStatistics.getPredictionsMadeCount() + 1);
                } else {
                    userInferentialStatistics.setScienceIncorrectPredictions(userInferentialStatistics.getScienceIncorrectPredictions() + 1);
                    userInferentialStatistics.setSciencePredictionCount(userInferentialStatistics.getSciencePredictionCount() + 1);
                    userInferentialStatistics.setPredictionsMadeCount(userInferentialStatistics.getPredictionsMadeCount() + 1);
                }
            }
            else if (resolvedPrediction.getPredictionType().equalsIgnoreCase("Weather")) {
                if (resolvedPrediction.getResolution()) {
                    userInferentialStatistics.setWeatherCorrectPredictions(userInferentialStatistics.getWeatherCorrectPredictions() + 1);
                    userInferentialStatistics.setWeatherPredictionCount(userInferentialStatistics.getWeatherPredictionCount() + 1);
                    userInferentialStatistics.setPredictionsMadeCount(userInferentialStatistics.getPredictionsMadeCount() + 1);
                } else {
                    userInferentialStatistics.setWeatherIncorrectPredictions(userInferentialStatistics.getWeatherIncorrectPredictions() + 1);
                    userInferentialStatistics.setWeatherPredictionCount(userInferentialStatistics.getWeatherPredictionCount() + 1);
                    userInferentialStatistics.setPredictionsMadeCount(userInferentialStatistics.getPredictionsMadeCount() + 1);
                }
            }
            else if (resolvedPrediction.getPredictionType().equalsIgnoreCase("Entertainment")) {
                if (resolvedPrediction.getResolution()) {
                    userInferentialStatistics.setEntertainmentCorrectPredictions(userInferentialStatistics.getEntertainmentCorrectPredictions() + 1);
                    userInferentialStatistics.setEntertainmentPredictionCount(userInferentialStatistics.getEntertainmentPredictionCount() + 1);
                    userInferentialStatistics.setPredictionsMadeCount(userInferentialStatistics.getPredictionsMadeCount() + 1);
                } else {
                    userInferentialStatistics.setEntertainmentIncorrectPredictions(userInferentialStatistics.getEntertainmentIncorrectPredictions() + 1);
                    userInferentialStatistics.setEntertainmentPredictionCount(userInferentialStatistics.getEntertainmentPredictionCount() + 1);
                    userInferentialStatistics.setPredictionsMadeCount(userInferentialStatistics.getPredictionsMadeCount() + 1);
                }
            }
            else {
                System.out.println("ERROR - UserStatistics.UserInferentialStatisticsUpdater cannot match the type of the user's resolved prediction: " + resolvedPrediction.getPredictionType());
            }
        }

        // Calculate the percentage of correct and incorrect custom predictions
        Double percentCorrectCustom;
        Double percentIncorrectCustom;

        if (userInferentialStatistics.getCustomPredictionCount() > 0) {
            percentCorrectCustom = ((double) userInferentialStatistics.getCustomCorrectPredictions() / userInferentialStatistics.getCustomPredictionCount()) * 100.0;
            percentIncorrectCustom = 100.0 - percentCorrectCustom;
        } else {
            percentCorrectCustom = Double.NaN;
            percentIncorrectCustom = Double.NaN;
        }

        // Calculate the percentage of correct and incorrect sports predictions
        Double percentCorrectSports;
        Double percentIncorrectSports;

        if (userInferentialStatistics.getSportsPredictionCount() > 0) {
            percentCorrectSports = ((double) userInferentialStatistics.getSportsCorrectPredictions() / userInferentialStatistics.getSportsPredictionCount()) * 100.0;
            percentIncorrectSports = 100.0 - percentCorrectSports;
        } else {
            percentCorrectSports = Double.NaN;
            percentIncorrectSports = Double.NaN;
        }

        // Calculate the percentage of correct and incorrect science predictions
        Double percentCorrectScience;
        Double percentIncorrectScience;

        if (userInferentialStatistics.getSciencePredictionCount() > 0) {
            percentCorrectScience = ((double) userInferentialStatistics.getScienceCorrectPredictions() / userInferentialStatistics.getSciencePredictionCount()) * 100.0;
            percentIncorrectScience = 100.0 - percentCorrectScience;
        } else {
            percentCorrectScience = Double.NaN;
            percentIncorrectScience = Double.NaN;
        }

        // Calculate the percentage of correct and incorrect weather predictions
        Double percentCorrectWeather;
        Double percentIncorrectWeather;

        if (userInferentialStatistics.getWeatherPredictionCount() > 0) {
            percentCorrectWeather = ((double) userInferentialStatistics.getWeatherCorrectPredictions() / userInferentialStatistics.getWeatherPredictionCount()) * 100.0;
            percentIncorrectWeather = 100.0 - percentCorrectWeather;
        } else {
            percentCorrectWeather = Double.NaN;
            percentIncorrectWeather = Double.NaN;
        }

        // Calculate the percentage of correct and incorrect entertainment predictions
        Double percentCorrectEntertainment;
        Double percentIncorrectEntertainment;

        if (userInferentialStatistics.getEntertainmentPredictionCount() > 0) {
            percentCorrectEntertainment = ((double) userInferentialStatistics.getEntertainmentCorrectPredictions() / userInferentialStatistics.getEntertainmentPredictionCount()) * 100.0;
            percentIncorrectEntertainment = 100.0 - percentCorrectEntertainment;
        } else {
            percentCorrectEntertainment = Double.NaN;
            percentIncorrectEntertainment = Double.NaN;
        }


        // Format and set the percentage values to two decimal places
        DecimalFormat df = new DecimalFormat("0.00");
        userInferentialStatistics.setCustomPercentCorrect(Double.parseDouble(df.format(percentCorrectCustom)));
        userInferentialStatistics.setCustomPercentIncorrect(Double.parseDouble(df.format(percentIncorrectCustom)));
        userInferentialStatistics.setSportsPercentCorrect(Double.parseDouble(df.format(percentCorrectSports)));
        userInferentialStatistics.setSportsPercentIncorrect(Double.parseDouble(df.format(percentIncorrectSports)));
        userInferentialStatistics.setSciencePercentCorrect(Double.parseDouble(df.format(percentCorrectScience)));
        userInferentialStatistics.setSciencePercentIncorrect(Double.parseDouble(df.format(percentIncorrectScience)));
        userInferentialStatistics.setWeatherPercentCorrect(Double.parseDouble(df.format(percentCorrectWeather)));
        userInferentialStatistics.setWeatherPercentIncorrect(Double.parseDouble(df.format(percentIncorrectWeather)));
        userInferentialStatistics.setEntertainmentPercentCorrect(Double.parseDouble(df.format(percentCorrectEntertainment)));
        userInferentialStatistics.setEntertainmentPercentIncorrect(Double.parseDouble(df.format(percentIncorrectEntertainment)));

        // Calculate the percentages of each prediction type made
        Double customPredictionsMadePercent = ((double) userInferentialStatistics.getCustomPredictionCount() / userInferentialStatistics.getPredictionsMadeCount()) * 100;
        Double sportsPredictionsMadePercent = ((double) userInferentialStatistics.getSportsPredictionCount() / userInferentialStatistics.getPredictionsMadeCount()) * 100;
        Double sciencePredictionsMadePercent = ((double) userInferentialStatistics.getSciencePredictionCount() / userInferentialStatistics.getPredictionsMadeCount()) * 100;
        Double weatherPredictionsMadePercent = ((double) userInferentialStatistics.getWeatherPredictionCount() / userInferentialStatistics.getPredictionsMadeCount()) * 100;
        Double entertainmentPredictionsMadePercent = ((double) userInferentialStatistics.getEntertainmentPredictionCount() / userInferentialStatistics.getPredictionsMadeCount()) * 100;

        // Set the percentages of each prediction type made
        userInferentialStatistics.setCustomPercentPredictionsMade(Double.parseDouble(df.format(customPredictionsMadePercent)));
        userInferentialStatistics.setSportsPercentPredictionsMade(Double.parseDouble(df.format(sportsPredictionsMadePercent)));
        userInferentialStatistics.setSciencePercentPredictionsMade(Double.parseDouble(df.format(sciencePredictionsMadePercent)));
        userInferentialStatistics.setWeatherPercentPredictionsMade(Double.parseDouble(df.format(weatherPredictionsMadePercent)));
        userInferentialStatistics.setEntertainmentPercentPredictionsMade(Double.parseDouble(df.format(entertainmentPredictionsMadePercent)));

        // Initialize String for mostCorrectPredictionType and leastCorrectPredictionType
        String mostCorrectPredictionType = "No Predictions Made Yet";
        String leastCorrectPredictionType = "No Predictions Made Yet";

        // Get the user's most correct prediction type
        if (userInferentialStatistics.getCustomPredictionCount() > 0) {
            mostCorrectPredictionType = "Custom";
        }
        if (userInferentialStatistics.getSportsPredictionCount() > 0 && percentCorrectSports > percentCorrectCustom) {
            mostCorrectPredictionType = "Sports";
        }
        if (userInferentialStatistics.getSciencePredictionCount() > 0 && percentCorrectScience > percentCorrectCustom && percentCorrectScience > percentCorrectSports) {
            mostCorrectPredictionType = "Science";
        }
        if (userInferentialStatistics.getWeatherPredictionCount() > 0 && percentCorrectWeather > percentCorrectCustom && percentCorrectWeather > percentCorrectSports && percentCorrectWeather > percentCorrectScience) {
            mostCorrectPredictionType = "Weather";
        }
        if (userInferentialStatistics.getEntertainmentPredictionCount() > 0 && percentCorrectEntertainment > percentCorrectCustom && percentCorrectEntertainment > percentCorrectSports && percentCorrectEntertainment > percentCorrectScience && percentCorrectEntertainment > percentCorrectWeather) {
            mostCorrectPredictionType = "Entertainment";
        }

        // Get the user's least correct prediction type
        if (userInferentialStatistics.getCustomPredictionCount() > 0) {
            leastCorrectPredictionType = "Custom";
        }
        if (userInferentialStatistics.getSportsPredictionCount() > 0 && percentIncorrectSports > percentIncorrectCustom) {
            leastCorrectPredictionType = "Sports";
        }
        if (userInferentialStatistics.getSciencePredictionCount() > 0 && percentIncorrectScience > percentIncorrectCustom && percentIncorrectScience > percentIncorrectSports) {
            leastCorrectPredictionType = "Science";
        }
        if (userInferentialStatistics.getWeatherPredictionCount() > 0 && percentIncorrectWeather > percentIncorrectCustom && percentIncorrectWeather > percentIncorrectSports && percentIncorrectWeather > percentIncorrectScience) {
            leastCorrectPredictionType = "Weather";
        }
        if (userInferentialStatistics.getEntertainmentPredictionCount() > 0 && percentIncorrectEntertainment > percentIncorrectCustom && percentIncorrectEntertainment > percentIncorrectSports && percentIncorrectEntertainment > percentIncorrectScience && percentIncorrectEntertainment > percentIncorrectWeather) {
            leastCorrectPredictionType = "Entertainment";
        }

        // Set the most correct prediction type and least correct prediction type
        userInferentialStatistics.setMostCorrectPredictionType(mostCorrectPredictionType);
        userInferentialStatistics.setLeastCorrectPredictionType(leastCorrectPredictionType);

        // Save the user inferential statistics
        MongoDBEnvisionaryUsers.updateUserInferentialStatistics(userIdentifier, userInferentialStatistics);
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // loadAndDisplayUserInferentialStatistics
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Loads the given user identifier's user inferential statistics file and displays the data to the
    // console.
    //
    public static void loadAndDisplayUserInferentialStatistics(String userIdentifier) {
        // Initialize the resolvedFilePath using the userIdentifier parameter
        String userInferentialStatisticsFilePath = userInferentialStatisticsFolderPath + File.separator + userIdentifier + "_user_inferential_statistics.json";
        File userInferentialStatisticsFile = new File(userInferentialStatisticsFilePath);

        // Check if the file exists before attempting to load it
        if (userInferentialStatisticsFile.exists()) {
            // Load the file and print
            try {
                String json = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(userInferentialStatisticsFilePath)));
                Gson gson = new Gson();
                Type listType = new TypeToken<UserInferentialStatistics>() {}.getType();
                UserInferentialStatistics loadedUserInferentialStatistics = gson.fromJson(json, listType);
                System.out.println("====================================================================================================");
                System.out.println("                                 " + userIdentifier + "'s UserInfo.User Inferential Statistics");
                loadedUserInferentialStatistics.printUserInferentialStatistics();
                System.out.println();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("ERROR - UserInfo.User inferential statistics file does not exist for user: " + userIdentifier + "\n");
        }
    }
}
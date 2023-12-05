package backend.ResolvedPredictions;

import backend.UserInfo.MongoDBEnvisionaryUsers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

//■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
// ResolvedPredictions.ResolvedPredictionInitializer class - Written by Brandon LaPointe
//■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
// Initializes the file path and folders needed to save the resolved prediction data. Allows for display
// of any specific category of resolved predictions or display of all resolved predictions.
//
public class ResolvedPredictionInitializer {
    // ResolvedPredictions.ResolvedPredictionInitializer Class Constants
    private static final String userHome = System.getProperty("user.home");
    private static final String resolvedPredictionFolderPath = userHome + File.separator + "EnvisionaryApp" + File.separator + "ResolvedPredictions";

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // initializeFilePath
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Initializes the file path and folders needed to save the user's resolved predictions.
    //
    public static void initializeFilePath() {
        // Create the directory if it doesn't exist
        File directory = new File(resolvedPredictionFolderPath);
        if (!directory.exists()) {
            if (directory.mkdirs()) {
                System.out.println("Created directory: " + resolvedPredictionFolderPath);
            } else {
                System.err.println("Failed to create directory: " + resolvedPredictionFolderPath);
            }
        }
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // printResolvedCustomPredictions
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Prints the given user's resolved custom predictions to the console.
    //
    public static void printResolvedCustomPredictions(String userIdentifier) {
        // Initialize new array list of ResolvedPrediction to load file data into
        ArrayList<ResolvedPrediction> loadedResolvedPredictions = new ArrayList<>();

        // Check if the file exists before attempting to load it
        String filePath = resolvedPredictionFolderPath + File.separator + userIdentifier + "_resolved_predictions.json";
        File file = new File(filePath);

        // Check if the file exists before attempting to load it
        if (file.exists()) {
            // Load the file
            try {
                String json = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(filePath)));
                Gson gson = new Gson();
                Type listType = new TypeToken<ArrayList<ResolvedPrediction>>() {}.getType();
                loadedResolvedPredictions = gson.fromJson(json, listType);
                System.out.println("\n====================================================================================================");
                System.out.println("                                 " + userIdentifier + "'s Resolved Custom Predictions");
                // For each resolved prediction within the array list of loaded resolved predictions
                for (ResolvedPrediction resolvedPrediction : loadedResolvedPredictions) {
                    if (resolvedPrediction.getPredictionType().equalsIgnoreCase("Custom")) {
                        System.out.println(resolvedPrediction.getResolution() + " : " + resolvedPrediction.getPredictionMadeDate() + " \"" + resolvedPrediction.getPredictionContent() + "\"" + " -" + userIdentifier);
                    }
                }
                System.out.println();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("\nERROR - USER RESOLVED PREDICTION FILE DOES NOT EXIST FOR USER: " + userIdentifier + " - SUCCESSFULLY RESOLVE A PREDICTION TO INITIALIZE\n");
        }
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // printResolvedCustomPredictionsMongoDB
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Prints the given user's resolved custom predictions to the console.
    //
    public static void printResolvedCustomPredictionsMongoDB(String userIdentifier) {
        // Initialize new array list of ResolvedPrediction to load file data into
        ArrayList<ResolvedPrediction> loadedResolvedPredictions = MongoDBEnvisionaryUsers.retrieveUserResolvedPredictions(userIdentifier);
        if (loadedResolvedPredictions == null) {
            loadedResolvedPredictions = new ArrayList<>();
        }
        System.out.println("\n====================================================================================================");
        System.out.println("                                 " + userIdentifier + "'s Resolved Custom Predictions");
        // For each resolved prediction within the array list of loaded resolved predictions
        for (ResolvedPrediction resolvedPrediction : loadedResolvedPredictions) {
            if (resolvedPrediction.getPredictionType().equalsIgnoreCase("Custom")) {
                System.out.println(resolvedPrediction.getResolution() + " : " + resolvedPrediction.getPredictionMadeDate() + " \"" + resolvedPrediction.getPredictionContent() + "\"" + " -" + userIdentifier);
            }
        }
        System.out.println();
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // printResolvedFootballMatchPredictions
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Prints the given user's resolved football match predictions to the console.
    //
    public static void printResolvedFootballMatchPredictions(String userIdentifier) {
        // Initialize new array list of ResolvedPrediction to load file data into
        ArrayList<ResolvedPrediction> loadedResolvedPredictions = new ArrayList<>();

        // Check if the file exists before attempting to load it
        String filePath = resolvedPredictionFolderPath + File.separator + userIdentifier + "_resolved_predictions.json";
        File file = new File(filePath);

        // Check if the file exists before attempting to load it
        if (file.exists()) {
            // Load the file
            try {
                String json = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(filePath)));
                Gson gson = new Gson();
                Type listType = new TypeToken<ArrayList<ResolvedPrediction>>() {}.getType();
                loadedResolvedPredictions = gson.fromJson(json, listType);
                System.out.println("\n====================================================================================================");
                System.out.println("                                 " + userIdentifier + "'s Resolved Football Match Predictions");
                // For each resolved prediction within the array list of loaded resolved predictions
                for (ResolvedPrediction resolvedPrediction : loadedResolvedPredictions) {
                    if (resolvedPrediction.getPredictionType().equalsIgnoreCase("FootballMatchPredictions.FootballMatch")) {
                        System.out.println(resolvedPrediction.getResolution() + " : " + resolvedPrediction.getPredictionMadeDate() + " \"" + resolvedPrediction.getPredictionContent() + "\"" + " -" + userIdentifier);
                    }
                }
                System.out.println();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("\nERROR - USER RESOLVED PREDICTION FILE DOES NOT EXIST FOR USER: " + userIdentifier + " - SUCCESSFULLY RESOLVE A PREDICTION TO INITIALIZE\n");
        }
    }
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // printResolvedFootballMatchPredictionsMongoDB
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Prints the given user's resolved custom predictions to the console.
    //
    public static void printResolvedFootballMatchPredictionsMongoDB(String userIdentifier) {
        // Initialize new array list of ResolvedPrediction to load file data into
        ArrayList<ResolvedPrediction> loadedResolvedPredictions = MongoDBEnvisionaryUsers.retrieveUserResolvedPredictions(userIdentifier);
        System.out.println("\n====================================================================================================");
        System.out.println("                                 " + userIdentifier + "'s Resolved FootballMatchPredictions.FootballMatch Predictions");
        // For each resolved prediction within the array list of loaded resolved predictions
        for (ResolvedPrediction resolvedPrediction : loadedResolvedPredictions) {
            if (resolvedPrediction.getPredictionType().equalsIgnoreCase("FootballMatchPredictions.FootballMatch")) {
                System.out.println(resolvedPrediction.getResolution() + " : " + resolvedPrediction.getPredictionMadeDate() + " \"" + resolvedPrediction.getPredictionContent() + "\"" + " -" + userIdentifier);
            }
        }
        System.out.println();
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // printResolvedSciencePredictions
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Prints the given user's resolved science predictions to the console.
    //
    public static void printResolvedSciencePredictions(String userIdentifier) {
        // Initialize new array list of ResolvedPrediction to load file data into
        ArrayList<ResolvedPrediction> loadedResolvedPredictions = new ArrayList<>();

        // Check if the file exists before attempting to load it
        String filePath = resolvedPredictionFolderPath + File.separator + userIdentifier + "_resolved_predictions.json";
        File file = new File(filePath);

        // Check if the file exists before attempting to load it
        if (file.exists()) {
            // Load the file
            try {
                String json = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(filePath)));
                Gson gson = new Gson();
                Type listType = new TypeToken<ArrayList<ResolvedPrediction>>() {}.getType();
                loadedResolvedPredictions = gson.fromJson(json, listType);
                System.out.println("\n====================================================================================================");
                System.out.println("                                 " + userIdentifier + "'s Resolved Science Predictions");
                // For each resolved prediction within the array list of loaded resolved predictions
                for (ResolvedPrediction resolvedPrediction : loadedResolvedPredictions) {
                    if (resolvedPrediction.getPredictionType().equalsIgnoreCase("Science")) {
                        System.out.println(resolvedPrediction.getResolution() + " : " + resolvedPrediction.getPredictionMadeDate() + " \"" + resolvedPrediction.getPredictionContent() + "\"" + " -" + userIdentifier);
                    }
                }
                System.out.println();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("\nERROR - USER RESOLVED PREDICTION FILE DOES NOT EXIST FOR USER: " + userIdentifier + " - SUCCESSFULLY RESOLVE A PREDICTION TO INITIALIZE\n");
        }
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // printResolvedSciencePredictionsMongoDB
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Prints the given user's resolved custom predictions to the console.
    //
    public static void printResolvedSciencePredictionsMongoDB(String userIdentifier) {
        // Initialize new array list of ResolvedPrediction to load file data into
        ArrayList<ResolvedPrediction> loadedResolvedPredictions = MongoDBEnvisionaryUsers.retrieveUserResolvedPredictions(userIdentifier);
        System.out.println("\n====================================================================================================");
        System.out.println("                                 " + userIdentifier + "'s Resolved Science Predictions");
        // For each resolved prediction within the array list of loaded resolved predictions
        for (ResolvedPrediction resolvedPrediction : loadedResolvedPredictions) {
            if (resolvedPrediction.getPredictionType().equalsIgnoreCase("Science")) {
                System.out.println(resolvedPrediction.getResolution() + " : " + resolvedPrediction.getPredictionMadeDate() + " \"" + resolvedPrediction.getPredictionContent() + "\"" + " -" + userIdentifier);
            }
        }
        System.out.println();
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // printResolvedWeatherPredictions
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Prints the given user's resolved weather predictions to the console.
    //
    public static void printResolvedWeatherPredictions(String userIdentifier) {
        // Initialize new array list of ResolvedPrediction to load file data into
        ArrayList<ResolvedPrediction> loadedResolvedPredictions = new ArrayList<>();

        // Check if the file exists before attempting to load it
        String filePath = resolvedPredictionFolderPath + File.separator + userIdentifier + "_resolved_predictions.json";
        File file = new File(filePath);

        // Check if the file exists before attempting to load it
        if (file.exists()) {
            // Load the file
            try {
                String json = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(filePath)));
                Gson gson = new Gson();
                Type listType = new TypeToken<ArrayList<ResolvedPrediction>>() {}.getType();
                loadedResolvedPredictions = gson.fromJson(json, listType);
                System.out.println("\n====================================================================================================");
                System.out.println("                                 " + userIdentifier + "'s Resolved Weather Predictions");
                // For each resolved prediction within the array list of loaded resolved predictions
                for (ResolvedPrediction resolvedPrediction : loadedResolvedPredictions) {
                    if (resolvedPrediction.getPredictionType().equalsIgnoreCase("Weather")) {
                        System.out.println(resolvedPrediction.getResolution() + " : " + resolvedPrediction.getPredictionMadeDate() + " \"" + resolvedPrediction.getPredictionContent() + "\"" + " -" + userIdentifier);
                    }
                }
                System.out.println();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("\nERROR - USER RESOLVED PREDICTION FILE DOES NOT EXIST FOR USER: " + userIdentifier + " - SUCCESSFULLY RESOLVE A PREDICTION TO INITIALIZE\n");
        }
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // printResolvedWeatherPredictionsMongoDB
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Prints the given user's resolved custom predictions to the console.
    //
    public static void printResolvedWeatherPredictionsMongoDB(String userIdentifier) {
        // Initialize new array list of ResolvedPrediction to load file data into
        ArrayList<ResolvedPrediction> loadedResolvedPredictions = MongoDBEnvisionaryUsers.retrieveUserResolvedPredictions(userIdentifier);
        System.out.println("\n====================================================================================================");
        System.out.println("                                 " + userIdentifier + "'s Resolved Weather Predictions");
        // For each resolved prediction within the array list of loaded resolved predictions
        for (ResolvedPrediction resolvedPrediction : loadedResolvedPredictions) {
            if (resolvedPrediction.getPredictionType().equalsIgnoreCase("Weather")) {
                System.out.println(resolvedPrediction.getResolution() + " : " + resolvedPrediction.getPredictionMadeDate() + " \"" + resolvedPrediction.getPredictionContent() + "\"" + " -" + userIdentifier);
            }
        }
        System.out.println();
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // printResolvedEntertainmentPredictions
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Prints the given user's resolved entertainment predictions to the console.
    //
    public static void printResolvedEntertainmentPredictions(String userIdentifier) {
        // Initialize new array list of ResolvedPrediction to load file data into
        ArrayList<ResolvedPrediction> loadedResolvedPredictions = new ArrayList<>();

        // Check if the file exists before attempting to load it
        String filePath = resolvedPredictionFolderPath + File.separator + userIdentifier + "_resolved_predictions.json";
        File file = new File(filePath);

        // Check if the file exists before attempting to load it
        if (file.exists()) {
            // Load the file
            try {
                String json = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(filePath)));
                Gson gson = new Gson();
                Type listType = new TypeToken<ArrayList<ResolvedPrediction>>() {}.getType();
                loadedResolvedPredictions = gson.fromJson(json, listType);
                System.out.println("\n====================================================================================================");
                System.out.println("                                 " + userIdentifier + "'s Resolved Entertainment Predictions");
                // For each resolved prediction within the array list of loaded resolved predictions
                for (ResolvedPrediction resolvedPrediction : loadedResolvedPredictions) {
                    if (resolvedPrediction.getPredictionType().equalsIgnoreCase("Entertainment")) {
                        System.out.println(resolvedPrediction.getResolution() + " : " + resolvedPrediction.getPredictionMadeDate() + " \"" + resolvedPrediction.getPredictionContent() + "\"" + " -" + userIdentifier);
                    }
                }
                System.out.println();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("\nERROR - USER RESOLVED PREDICTION FILE DOES NOT EXIST FOR USER: " + userIdentifier + " - SUCCESSFULLY RESOLVE A PREDICTION TO INITIALIZE\n");
        }
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // printResolvedEntertainmentPredictionsMongoDB
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Prints the given user's resolved custom predictions to the console.
    //
    public static void printResolvedEntertainmentPredictionsMongoDB(String userIdentifier) {
        // Initialize new array list of ResolvedPrediction to load file data into
        ArrayList<ResolvedPrediction> loadedResolvedPredictions = MongoDBEnvisionaryUsers.retrieveUserResolvedPredictions(userIdentifier);
        System.out.println("\n====================================================================================================");
        System.out.println("                                 " + userIdentifier + "'s Resolved Entertainment Predictions");
        // For each resolved prediction within the array list of loaded resolved predictions
        for (ResolvedPrediction resolvedPrediction : loadedResolvedPredictions) {
            if (resolvedPrediction.getPredictionType().equalsIgnoreCase("Entertainment")) {
                System.out.println(resolvedPrediction.getResolution() + " : " + resolvedPrediction.getPredictionMadeDate() + " \"" + resolvedPrediction.getPredictionContent() + "\"" + " -" + userIdentifier);
            }
        }
        System.out.println();
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // printAllResolvedPredictions
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Prints all the given user's resolved predictions to the console.
    //
    public static void printAllResolvedPredictions(String userIdentifier) {
        // Initialize new array list of ResolvedPrediction to load file data into
        ArrayList<ResolvedPrediction> loadedResolvedPredictions = new ArrayList<>();

        // Check if the file exists before attempting to load it
        String filePath = resolvedPredictionFolderPath + File.separator + userIdentifier + "_resolved_predictions.json";
        File file = new File(filePath);

        // Check if the file exists before attempting to load it
        if (file.exists()) {
            // Load the file
            try {
                String json = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(filePath)));
                Gson gson = new Gson();
                Type listType = new TypeToken<ArrayList<ResolvedPrediction>>() {}.getType();
                loadedResolvedPredictions = gson.fromJson(json, listType);
                System.out.println("\n====================================================================================================");
                System.out.println("                                 " + userIdentifier + "'s Resolved Predictions");
                // For each resolved prediction within the array list of loaded resolved predictions
                for (ResolvedPrediction resolvedPrediction : loadedResolvedPredictions) {
                    System.out.println(resolvedPrediction.getResolution() + " : " + resolvedPrediction.getPredictionMadeDate() + " \"" + resolvedPrediction.getPredictionContent() + "\"" + " -" + userIdentifier);
                }
                System.out.println();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("\nERROR - USER RESOLVED PREDICTION FILE DOES NOT EXIST FOR USER: " + userIdentifier + " - SUCCESSFULLY RESOLVE A PREDICTION TO INITIALIZE\n");
        }
    }
}
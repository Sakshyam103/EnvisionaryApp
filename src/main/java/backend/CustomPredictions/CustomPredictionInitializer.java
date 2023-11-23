package backend.CustomPredictions;

import backend.UserInfo.MongoDBEnvisionaryUsers;
import backend.ResolvedPredictions.ResolvedPrediction;
import backend.UserStatistics.UserDescriptiveStatisticsUpdater;
import backend.UserStatistics.UserInferentialStatisticsUpdater;
import backend.OverallStatistics.OverallDescriptiveStatisticsUpdater;
import backend.OverallStatistics.OverallInferentialStatisticsUpdater;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

//■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
// CustomPredictions.CustomPredictionInitializer class - Written by Brandon LaPointe
//■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
// Initializes the file path and folders needed to save the users' custom predictions. Initializes
// custom predictions made by the user, removes custom predictions, displays custom predictions,
// and resolves custom predictions using the String userIdentifier parameter to identify the correct
// file.
//
public class CustomPredictionInitializer {
    // CustomPredictions.CustomPredictionInitializer Class Constants & Variables
    private static final String userHome = System.getProperty("user.home");
    private static final String customPredictionFolderPath = userHome + File.separator + "EnvisionaryApp" + File.separator + "CustomPredictions";
    private static final String resolvedPredictionFolderPath = userHome + File.separator + "EnvisionaryApp" + File.separator + "ResolvedPredictions";

    // CustomPredictions.CustomPrediction Object
    private static CustomPrediction userCustomPrediction;

    // Scanner object for reading user input from keyboard
    public static Scanner scan = new Scanner(System.in);

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // initializeFilePath
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Initializes the file path and folders needed to save the users' custom predictions
    //
    public static void initializeFilePath() {
        // Create the directory if it doesn't exist
        File directory = new File(customPredictionFolderPath);
        if (!directory.exists()) {
            if (directory.mkdirs()) {
                System.out.println("Created directory: " + customPredictionFolderPath);
            } else {
                System.err.println("ERROR - Failed to create directory: " + customPredictionFolderPath);
            }
        }
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // createNewCustomPrediction
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Gathers the inputs from the user to set the prediction variables of the CustomPredictions.CustomPrediction and saves
    // to the specified user's custom prediction file
    //
    public static void createNewCustomPrediction(String userIdentifier) {
        // Local variables
        String customSubCategory;
        String userPrediction;
        int endDateMonth = 0;
        int endDateDay = 0;
        int endDateYear = 0;
        LocalDate predictionEndDate = null;

        Scanner scan = new Scanner(System.in);

        // Initialize a new CustomPredictions.CustomPrediction
        userCustomPrediction = new CustomPrediction();

        // Gather the inputs for the custom prediction sub-category
        System.out.println("Please enter your choice of sub-category of your custom prediction or CANCEL to exit:");
        customSubCategory = scan.nextLine();

        // Check if the user wants to cancel
        if (customSubCategory.equalsIgnoreCase("CANCEL")) {
            System.out.println("Custom prediction creation canceled.");
            return; // Exit the method
        }

        // Set custom sub category
        userCustomPrediction.setCustomSubCategory(customSubCategory);

        // Gather the inputs for the prediction
        System.out.println("I predict that (PREDICTION) on (END-DATE).");
        System.out.println("Please enter (PREDICTION) segment or CANCEL to exit:");
        userPrediction = scan.nextLine();

        // Check if the user wants to cancel
        if (userPrediction.equalsIgnoreCase("CANCEL")) {
            System.out.println("Custom prediction creation canceled.");
            return; // Exit the method
        }


        boolean validDate = false;

        while (!validDate) {
            try {
                // Gather the inputs for the end date
                while (endDateMonth < 1 || endDateMonth > 12) {
                    System.out.println("Please enter the month of the END-DATE:");
                    endDateMonth = scan.nextInt();
                }
                while (endDateDay < 1 || endDateDay > 31) {
                    System.out.println("Please enter the day of the END-DATE:");
                    endDateDay = scan.nextInt();
                }
                while (endDateYear < LocalDate.now().getYear()) {
                    System.out.println("Please enter the year of the END-DATE:");
                    endDateYear = scan.nextInt();
                }

                // Attempt to create a LocalDate from the user's input
                predictionEndDate = LocalDate.of(endDateYear, endDateMonth, endDateDay);

                // Validate the date
                if (predictionEndDate.isBefore(LocalDate.now())) {
                    System.out.println("ERROR - The entered date is in the past. Please enter a valid future date.");
                    endDateMonth = 0;
                    endDateDay = 0;
                    endDateYear = 0;
                } else {
                    validDate = true;
                }
            } catch (DateTimeException e) {
                System.out.println("ERROR - Invalid date. Please enter a valid date.");
                endDateMonth = 0;
                endDateDay = 0;
                endDateYear = 0;
                scan.nextLine(); // Consume the invalid input
            }
        }

        // Convert LocalDate to ZonedDateTime with time set to midnight
        ZoneId zoneId = ZoneId.systemDefault();
        String startOfEndDay = predictionEndDate.atStartOfDay(zoneId).toString();

        // Set the prediction description
        userCustomPrediction.getPrediction().setPredictionContent("I predict that " + userPrediction);

        // Set the prediction end date
        userCustomPrediction.getPrediction().setPredictionEndDate(startOfEndDay);

        // Save the custom prediction to the user's custom prediction file
        CustomPredictionInitializer.saveCustomPrediction(userIdentifier);
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // createNewCustomPredictionMongoDB
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Gathers the inputs from the user to set the prediction variables of the CustomPredictions.CustomPrediction and saves
    // to the specified user's custom prediction file
    //
    public static void createNewCustomPredictionMongoDB(String userIdentifier) {
        // Local variables
        String customSubCategory;
        String userPrediction;
        int endDateMonth = 0;
        int endDateDay = 0;
        int endDateYear = 0;
        LocalDate predictionEndDate = null;

        Scanner scan = new Scanner(System.in);

        // Initialize a new CustomPredictions.CustomPrediction
        userCustomPrediction = new CustomPrediction();

        // Gather the inputs for the custom prediction sub-category
        System.out.println("Please enter your choice of sub-category of your custom prediction or CANCEL to exit:");
        customSubCategory = scan.nextLine();

        // Check if the user wants to cancel
        if (customSubCategory.equalsIgnoreCase("CANCEL")) {
            System.out.println("Custom prediction creation canceled.");
            return; // Exit the method
        }

        // Set custom sub category
        userCustomPrediction.setCustomSubCategory(customSubCategory);

        // Gather the inputs for the prediction
        System.out.println("I predict that (PREDICTION) on (END-DATE).");
        System.out.println("Please enter (PREDICTION) segment or CANCEL to exit:");
        userPrediction = scan.nextLine();

        // Check if the user wants to cancel
        if (userPrediction.equalsIgnoreCase("CANCEL")) {
            System.out.println("Custom prediction creation canceled.");
            return; // Exit the method
        }


        boolean validDate = false;

        while (!validDate) {
            try {
                // Gather the inputs for the end date
                while (endDateMonth < 1 || endDateMonth > 12) {
                    System.out.println("Please enter the month of the END-DATE:");
                    endDateMonth = scan.nextInt();
                }
                while (endDateDay < 1 || endDateDay > 31) {
                    System.out.println("Please enter the day of the END-DATE:");
                    endDateDay = scan.nextInt();
                }
                while (endDateYear < LocalDate.now().getYear()) {
                    System.out.println("Please enter the year of the END-DATE:");
                    endDateYear = scan.nextInt();
                }

                // Attempt to create a LocalDate from the user's input
                predictionEndDate = LocalDate.of(endDateYear, endDateMonth, endDateDay);

                // Validate the date
                if (predictionEndDate.isBefore(LocalDate.now())) {
                    System.out.println("ERROR - The entered date is in the past. Please enter a valid future date.");
                    endDateMonth = 0;
                    endDateDay = 0;
                    endDateYear = 0;
                } else {
                    validDate = true;
                }
            } catch (DateTimeException e) {
                System.out.println("ERROR - Invalid date. Please enter a valid date.");
                endDateMonth = 0;
                endDateDay = 0;
                endDateYear = 0;
                scan.nextLine(); // Consume the invalid input
            }
        }

        // Convert LocalDate to ZonedDateTime with time set to midnight
        ZoneId zoneId = ZoneId.systemDefault();
        String startOfEndDay = predictionEndDate.atStartOfDay(zoneId).toString();

        // Set the prediction description
        userCustomPrediction.getPrediction().setPredictionContent("I predict that " + userPrediction);

        // Set the prediction end date
        userCustomPrediction.getPrediction().setPredictionEndDate(startOfEndDay);

        // Save the custom prediction to the user's custom prediction file
        CustomPredictionInitializer.saveCustomPredictionMongoDB(userIdentifier);
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // saveCustomPrediction
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Saves the custom prediction to the given userIdentifier's custom prediction file.
    //
    public static void saveCustomPrediction(String userIdentifier) {
        // Set prediction type
        userCustomPrediction.getPrediction().setPredictionType("Custom");

        // Set prediction made date
        userCustomPrediction.getPrediction().setPredictionMadeDate(ZonedDateTime.now().toString());

        // Initialize new list of CustomPredictions.CustomPrediction to load into and save from
        ArrayList<CustomPrediction> loadedCustomPredictions = new ArrayList<>();

        // Check if the file exists before attempting to load it
        String filePath = customPredictionFolderPath + File.separator + userIdentifier + "_custom_predictions.json";
        File file = new File(filePath);
        if (file.exists()) {
            // Load the file
            try {
                String json = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(filePath)));
                Gson gson = new Gson();
                Type listType = new TypeToken<ArrayList<CustomPrediction>>() {}.getType();
                loadedCustomPredictions = gson.fromJson(json, listType);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Add the newly made custom prediction to the list
        loadedCustomPredictions.add(userCustomPrediction);

        // Save the list to a file in JSON format
        try (FileWriter writer = new FileWriter(filePath)) {
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<CustomPrediction>>() {}.getType();
            String json = gson.toJson(loadedCustomPredictions, listType);
            writer.write(json);
            System.out.println("Custom predictions have been saved to the file: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // saveCustomPredictionMongoDB
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Saves the user's custom prediction to MongoDB
    //
    public static void saveCustomPredictionMongoDB(String userIdentifier) {
        // Set prediction type
        userCustomPrediction.getPrediction().setPredictionType("Custom");

        // Set prediction made date
        userCustomPrediction.getPrediction().setPredictionMadeDate(ZonedDateTime.now().toString());

        // Initialize new list of CustomPredictions.CustomPrediction to load into and save from
        ArrayList<CustomPrediction> loadedCustomPredictions = MongoDBEnvisionaryUsers.retrieveUserCustomPredictions(userIdentifier);

        if (loadedCustomPredictions == null) {
            loadedCustomPredictions = new ArrayList<>();
        }

        // Add the newly made custom prediction to the list
        loadedCustomPredictions.add(userCustomPrediction);

        // Save the list to MongoDB
        MongoDBEnvisionaryUsers.updateUserCustomPredictions(userIdentifier, loadedCustomPredictions);

    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // printAllCustomPredictions
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Prints the given userIdentifier's custom predictions read from the user's custom prediction json
    // file and displays them to the console.
    //
    public static void printAllCustomPredictions(String userIdentifier) {
        // Initialize new array list of FootballMatchPredictions.FootballMatchPrediction to load file data into
        ArrayList<CustomPrediction> loadedCustomPredictions = new ArrayList<>();

        // Check if the file exists before attempting to load it
        String filePath = customPredictionFolderPath + File.separator + userIdentifier + "_custom_predictions.json";
        File file = new File(filePath);

        if (file.exists()) {
            // Load the file
            try {
                String json = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(filePath)));
                Gson gson = new Gson();
                Type listType = new TypeToken<ArrayList<CustomPrediction>>() {}.getType();
                loadedCustomPredictions = gson.fromJson(json, listType);
                System.out.println("\n====================================================================================================");
                System.out.println("                                 " + userIdentifier + "'s Custom Predictions");
                // For each custom prediction within the array list of loaded custom predictions
                for (CustomPrediction customPrediction : loadedCustomPredictions) {
                    System.out.println(customPrediction.getPrediction().getPredictionMadeDate() + " " + customPrediction.getPrediction().getPredictionContent() + " by " + customPrediction.getPrediction().getPredictionEndDate() + " -" + userIdentifier);
                }
                System.out.println();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("ERROR - Custom predictions file does not exist for user: " + userIdentifier);
        }
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // removeCustomPrediction
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Displays all the given userIdentifier's custom predictions with indexes starting at 1 before asking
    // the user for input of the index of the custom prediction that they want to remove, and then confirming
    // that they want to remove the selected prediction before removing or cancelling the removal.
    //

    public static void removeCustomPrediction(String userIdentifier) {
        Scanner scan = new Scanner(System.in);

        // Load custom predictions from JSON file
        String filePath = customPredictionFolderPath + File.separator + userIdentifier + "_custom_predictions.json";
        File file = new File(filePath);

        if (file.exists()) {
            try {
                Gson gson = new Gson();
                FileReader reader = new FileReader(filePath);
                ArrayList<CustomPrediction> customPredictions = gson.fromJson(reader, new TypeToken<ArrayList<CustomPrediction>>() {}.getType());
                reader.close();

                if (customPredictions.isEmpty()) {
                    System.out.println("ERROR - No custom predictions available to remove - Create a new custom prediction and try again.\n");
                    return;
                }

                // Display the list of custom predictions for the user to select from
                System.out.println("Select a prediction to remove from the list of your previously made custom predictions:");
                int predictionIndex = 1;
                for (CustomPrediction customPrediction : customPredictions) {
                    System.out.println(predictionIndex + " : " + customPrediction.getPrediction().getPredictionContent() + " by " + customPrediction.getPrediction().getPredictionEndDate());
                    ++predictionIndex;
                }

                int predictionRemovalSelection = -1; // Initialize the selection variable

                // Get user input from the list of custom predictions to remove with input validation
                boolean validInput = false;
                while (!validInput) {
                    try {
                        System.out.print("Enter the number of the prediction to remove: ");
                        predictionRemovalSelection = scan.nextInt();
                        validInput = true; // The input is valid if no exception is thrown
                    } catch (InputMismatchException e) {
                        System.out.println("ERROR - Invalid input. Please enter a valid number.");
                        scan.nextLine(); // Consume the invalid input
                    } catch (NoSuchElementException e) {
                        System.out.println("ERROR - No input detected. Please enter a valid number.");
                        scan.nextLine(); // Consume the invalid input
                    }
                }

                if (predictionRemovalSelection >= 1 && predictionRemovalSelection <= customPredictions.size()) {
                    // Ask if the user is sure about the choice
                    System.out.print("Are you sure you want to remove this prediction? (Y/N):\n" + customPredictions.get(predictionRemovalSelection - 1).getPrediction().getPredictionContent() + "\n");
                    String confirmation = scan.next();
                    scan.nextLine();

                    if (confirmation.equalsIgnoreCase("Y")) {
                        // Remove the prediction from the list
                        customPredictions.remove(predictionRemovalSelection - 1);

                        // Save the updated list of custom predictions to the JSON file
                        try {
                            FileWriter writer = new FileWriter(filePath);
                            gson.toJson(customPredictions, writer);
                            writer.close();
                            System.out.println("BasePredictionsObject.Prediction removed and saved successfully.");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        System.out.println("BasePredictionsObject.Prediction removal canceled.");
                    }
                } else {
                    System.out.println("ERROR - Invalid prediction number.");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("ERROR - UserInfo.User's custom prediction file does not exist.");
        }
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // removeCustomPredictionMongoDB
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Displays all the given userIdentifier's custom predictions with indexes starting at 1 before asking
    // the user for input of the index of the custom prediction that they want to remove, and then confirming
    // that they want to remove the selected prediction before removing or cancelling the removal.
    //

    public static void removeCustomPredictionMongoDB(String userIdentifier) {
        Scanner scan = new Scanner(System.in);

        // Load custom predictions from MongoDB
        ArrayList<CustomPrediction> userCustomPredictions = MongoDBEnvisionaryUsers.retrieveUserCustomPredictions(userIdentifier);

        if (userCustomPredictions == null) {
            System.out.println("ERROR - No custom predictions available to remove - Create a new custom prediction and try again.\n");
            return;
        }

        // Display the list of custom predictions for the user to select from
        System.out.println("Select a prediction to remove from the list of your previously made custom predictions:");
        int predictionIndex = 1;
        for (CustomPrediction customPrediction : userCustomPredictions) {
            System.out.println(predictionIndex + " : " + customPrediction.getPrediction().getPredictionContent() + " by " + customPrediction.getPrediction().getPredictionEndDate());
            ++predictionIndex;
        }

        int predictionRemovalSelection = -1; // Initialize the selection variable

        // Get user input from the list of custom predictions to remove with input validation
        boolean validInput = false;
        while (!validInput) {
            try {
                System.out.print("Enter the number of the prediction to remove: ");
                predictionRemovalSelection = scan.nextInt();
                validInput = true; // The input is valid if no exception is thrown
            } catch (InputMismatchException e) {
                System.out.println("ERROR - Invalid input. Please enter a valid number.");
                scan.nextLine(); // Consume the invalid input
            } catch (NoSuchElementException e) {
                System.out.println("ERROR - No input detected. Please enter a valid number.");
                scan.nextLine(); // Consume the invalid input
            }
        }

        if (predictionRemovalSelection >= 1 && predictionRemovalSelection <= userCustomPredictions.size()) {
            // Ask if the user is sure about the choice
            System.out.print("Are you sure you want to remove this prediction? (Y/N):\n" + userCustomPredictions.get(predictionRemovalSelection - 1).getPrediction().getPredictionContent() + "\n");
            String confirmation = scan.next();
            scan.nextLine();

            if (confirmation.equalsIgnoreCase("Y")) {
                // Remove the prediction from the list
                userCustomPredictions.remove(predictionRemovalSelection - 1);

                // Save the updated list of custom predictions to the JSON file
                MongoDBEnvisionaryUsers.updateUserCustomPredictions(userIdentifier, userCustomPredictions);
            } else {
                System.out.println("BasePredictionsObject.Prediction removal canceled.");
            }
        } else {
            System.out.println("ERROR - Invalid prediction number.");
        }
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // resolveCustomPrediction
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Displays all the given userIdentifier's custom predictions with indexes starting at 1 before asking
    // the user for input of the index of the custom prediction that they want to resolve, and then confirming
    // that they want to resolve the selected prediction before resolving or cancelling the removal.
    //

    public static void resolveCustomPrediction(String userIdentifier) {
        Scanner scan = new Scanner(System.in);

        // Load custom predictions from JSON file
        String filePath = customPredictionFolderPath + File.separator + userIdentifier + "_custom_predictions.json";
        File file = new File(filePath);

        if (file.exists()) {
            try {
                String json = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(filePath)));
                Gson gson = new Gson();
                Type listType = new TypeToken<ArrayList<CustomPrediction>>() {}.getType();
                ArrayList<CustomPrediction> customPredictions = gson.fromJson(json, listType);

                if (customPredictions.isEmpty()) {
                    System.out.println("ERROR - No custom predictions available to resolve - Create a new custom prediction and try again.\n");
                    return; // Exit the function
                }

                // Display the list of custom predictions for the user to select from
                System.out.println("Select a prediction to resolve from the list of your previously made custom predictions:");
                int predictionIndex = 1;
                for (CustomPrediction customPrediction : customPredictions) {
                    System.out.println(predictionIndex + " : " + customPrediction.getPrediction().getPredictionContent());
                    ++predictionIndex;
                }

                int predictionResolveSelection = -1; // Initialize the selection variable

                // Get user input from the list of custom predictions to resolve with input validation
                boolean validInput = false;
                while (!validInput) {
                    try {
                        System.out.print("Enter the number of the prediction to resolve: ");
                        predictionResolveSelection = scan.nextInt();
                        if (predictionResolveSelection > 0 && predictionResolveSelection <= customPredictions.size())
                            validInput = true; // The input is valid if no exception is thrown
                    } catch (InputMismatchException e) {
                        System.out.println("ERROR - Invalid input. Please enter a valid number.");
                        scan.nextLine(); // Consume the invalid input
                    } catch (NoSuchElementException e) {
                        System.out.println("ERROR - No input detected. Please enter a valid number.");
                        scan.nextLine();
                    }
                }

                if (predictionResolveSelection >= 1 && predictionResolveSelection <= customPredictions.size()) {
                    // Ask if the user is sure about the choice
                    System.out.print("Are you sure you want to resolve this prediction? (Y/N):\n" + customPredictions.get(predictionResolveSelection - 1).getPrediction().getPredictionContent() + "\n");
                    String confirmation = scan.next();

                    if (confirmation.equalsIgnoreCase("Y")) {
                        // Create a new resolved prediction from the custom prediction
                        ResolvedPrediction resolved = new ResolvedPrediction();

                        // Copy over the details of the custom prediction into the new resolved prediction
                        resolved.setPredictionContent(customPredictions.get(predictionResolveSelection - 1).getPrediction().getPredictionContent());
                        resolved.setPredictionEndDate(customPredictions.get(predictionResolveSelection - 1).getPrediction().getPredictionEndDate());
                        resolved.setPredictionMadeDate(customPredictions.get(predictionResolveSelection - 1).getPrediction().getPredictionMadeDate());
                        resolved.setPredictionType(customPredictions.get(predictionResolveSelection - 1).getPrediction().getPredictionType());

                        // Set the resolution to the resolved prediction
                        String resolveConfirmation = "";

                        // While the response is not valid, get outcome from the user
                        while (!resolveConfirmation.equalsIgnoreCase("T") && !resolveConfirmation.equalsIgnoreCase("F")) {
                            System.out.println("Enter the outcome of the prediction: (T/F)");
                            resolveConfirmation = scan.next();

                            // Set the resolution outcome to the resolved prediction
                            if (resolveConfirmation.equalsIgnoreCase("T")) {
                                resolved.setResolution(true);
                            } else if (resolveConfirmation.equalsIgnoreCase("F")) {
                                resolved.setResolution(false);
                            } else {
                                System.out.println("ERROR - Invalid response. T = True, F = False.");
                            }
                        }

                        // Set the resolved date to the resolved prediction
                        resolved.setResolvedDate(ZonedDateTime.now().toString());

                        // Initialize new array list of ResolvedPredictions.ResolvedPrediction to load into and save from
                        ArrayList<ResolvedPrediction> loadedResolvedPredictions = new ArrayList<>();

                        // Load the list of resolved predictions from JSON file
                        String resolvedFilePath = resolvedPredictionFolderPath + File.separator + userIdentifier + "_resolved_predictions.json";
                        File resolvedFile = new File(resolvedFilePath);

                        if (resolvedFile.exists()) {
                            try {
                                String json2 = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(resolvedFilePath)));
                                Gson gson2 = new Gson();
                                Type listType2 = new TypeToken<ArrayList<ResolvedPrediction>>() {}.getType();
                                loadedResolvedPredictions = gson2.fromJson(json2, listType2);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        // Add the new resolved prediction to the list
                        loadedResolvedPredictions.add(resolved);

                        // Save the resolved prediction to the user's resolved prediction list in JSON format
                        try {
                            FileWriter resolvedWriter = new FileWriter(resolvedFilePath);
                            gson.toJson(loadedResolvedPredictions, resolvedWriter);
                            resolvedWriter.close();
                            System.out.println("BasePredictionsObject.Prediction resolved successfully.");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        // Remove the custom prediction from the user's list of custom predictions
                        customPredictions.remove(predictionResolveSelection - 1);

                        // Save over the user's custom prediction file
                        FileWriter customWriter = new FileWriter(filePath);
                        gson.toJson(customPredictions, customWriter);
                        customWriter.close();

                        System.out.println("BasePredictionsObject.Prediction removed successfully.");

                        // Update UserStatistics.UserDescriptiveStatistics, UserStatistics.UserInferentialStatistics, and OverallStatistics
                        UserDescriptiveStatisticsUpdater.calculateAndSaveUserDescriptiveStatistics(userIdentifier);
                        UserInferentialStatisticsUpdater.calculateAndSaveUserInferentialStatistics(userIdentifier);
                        OverallDescriptiveStatisticsUpdater.calculateAndSaveOverallDescriptiveStatistics();
                        OverallInferentialStatisticsUpdater.calculateAndSaveOverallInferentialStatistics();

                    } else {
                        System.out.println("BasePredictionsObject.Prediction resolution canceled.");
                    }
                } else {
                    System.out.println("ERROR - Invalid prediction number.");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("ERROR - UserInfo.User's custom prediction file does not exist.");
        }
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // resolveCustomPredictionMongoDB
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Displays all the given userIdentifier's custom predictions with indexes starting at 1 before asking
    // the user for input of the index of the custom prediction that they want to resolve, and then confirming
    // that they want to resolve the selected prediction before resolving or cancelling the removal.
    //

    public static void resolveCustomPredictionMongoDB(String userIdentifier) {
        Scanner scan = new Scanner(System.in);

        // Load custom predictions from MongoDB
        ArrayList<CustomPrediction> userCustomPredictions = MongoDBEnvisionaryUsers.retrieveUserCustomPredictions(userIdentifier);

        if (userCustomPredictions == null) {
            userCustomPredictions = new ArrayList<>();
        }

        if (userCustomPredictions.isEmpty()) {
            System.out.println("ERROR - No custom predictions available to resolve - Create a new custom prediction and try again.\n");
            return;
        }

        // Display the list of custom predictions for the user to select from
        System.out.println("Select a prediction to resolve from the list of your previously made custom predictions:");
        int predictionIndex = 1;
        for (CustomPrediction customPrediction : userCustomPredictions) {
            System.out.println(predictionIndex + " : " + customPrediction.getPrediction().getPredictionContent());
            ++predictionIndex;
        }

        int predictionResolveSelection = -1; // Initialize the selection variable

        // Get user input from the list of custom predictions to resolve with input validation
        boolean validInput = false;
        while (!validInput) {
            try {
                System.out.print("Enter the number of the prediction to resolve: ");
                predictionResolveSelection = scan.nextInt();
                if (predictionResolveSelection > 0 && predictionResolveSelection <= userCustomPredictions.size())
                    validInput = true; // The input is valid if no exception is thrown
            } catch (InputMismatchException e) {
                System.out.println("ERROR - Invalid input. Please enter a valid number.");
                scan.nextLine(); // Consume the invalid input
            } catch (NoSuchElementException e) {
                System.out.println("ERROR - No input detected. Please enter a valid number.");
                scan.nextLine();
            }
        }

        if (predictionResolveSelection >= 1 && predictionResolveSelection <= userCustomPredictions.size()) {
            // Ask if the user is sure about the choice
            System.out.print("Are you sure you want to resolve this prediction? (Y/N):\n" + userCustomPredictions.get(predictionResolveSelection - 1).getPrediction().getPredictionContent() + "\n");
            String confirmation = scan.next();

            if (confirmation.equalsIgnoreCase("Y")) {
                // Create a new resolved prediction from the custom prediction
                ResolvedPrediction resolved = new ResolvedPrediction();

                // Copy over the details of the custom prediction into the new resolved prediction
                resolved.setPredictionContent(userCustomPredictions.get(predictionResolveSelection - 1).getPrediction().getPredictionContent());
                resolved.setPredictionEndDate(userCustomPredictions.get(predictionResolveSelection - 1).getPrediction().getPredictionEndDate());
                resolved.setPredictionMadeDate(userCustomPredictions.get(predictionResolveSelection - 1).getPrediction().getPredictionMadeDate());
                resolved.setPredictionType(userCustomPredictions.get(predictionResolveSelection - 1).getPrediction().getPredictionType());

                // Set the resolution to the resolved prediction
                String resolveConfirmation = "";

                // While the response is not valid, get outcome from the user
                while (!resolveConfirmation.equalsIgnoreCase("T") && !resolveConfirmation.equalsIgnoreCase("F")) {
                    System.out.println("Enter the outcome of the prediction: (T/F)");
                    resolveConfirmation = scan.next();

                    // Set the resolution outcome to the resolved prediction
                    if (resolveConfirmation.equalsIgnoreCase("T")) {
                        resolved.setResolution(true);
                    } else if (resolveConfirmation.equalsIgnoreCase("F")) {
                        resolved.setResolution(false);
                    } else {
                        System.out.println("ERROR - Invalid response. T = True, F = False.");
                    }
                }

                // Set the resolved date to the resolved prediction
                resolved.setResolvedDate(ZonedDateTime.now().toString());

                // Initialize new array list of ResolvedPredictions.ResolvedPrediction to load into and save from
                ArrayList<ResolvedPrediction> loadedResolvedPredictions = MongoDBEnvisionaryUsers.retrieveUserResolvedPredictions(userIdentifier);

                if (loadedResolvedPredictions == null) {
                    loadedResolvedPredictions = new ArrayList<>();
                }

                // Add the new resolved prediction to the list
                loadedResolvedPredictions.add(resolved);

                // Save the resolved prediction to the user's resolved prediction list in JSON format
                MongoDBEnvisionaryUsers.updateUserResolvedPredictions(userIdentifier, loadedResolvedPredictions);

                // Remove the custom prediction from the user's list of custom predictions
                userCustomPredictions.remove(predictionResolveSelection - 1);

                // Save the updated user's custom predictions list
                MongoDBEnvisionaryUsers.updateUserCustomPredictions(userIdentifier, userCustomPredictions);

                System.out.println("BasePredictionsObject.Prediction removed successfully.");

                // Update UserStatistics.UserDescriptiveStatistics, UserStatistics.UserInferentialStatistics, and OverallStatistics
                UserDescriptiveStatisticsUpdater.calculateAndSaveUserDescriptiveStatisticsMongoDB(userIdentifier);
                UserInferentialStatisticsUpdater.calculateAndSaveUserInferentialStatisticsMongoDB(userIdentifier);
                OverallDescriptiveStatisticsUpdater.calculateAndSaveOverallDescriptiveStatisticsMongoDB();
                OverallInferentialStatisticsUpdater.calculateAndSaveOverallInferentialStatisticsMongoDB();

            } else {
                System.out.println("BasePredictionsObject.Prediction resolution canceled.");
            }
        } else {
            System.out.println("ERROR - Invalid prediction number.");
        }
    }
}
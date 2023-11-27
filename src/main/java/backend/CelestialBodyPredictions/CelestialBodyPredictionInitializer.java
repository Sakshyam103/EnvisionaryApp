package backend.CelestialBodyPredictions;

import backend.UserInfo.MongoDBEnvisionaryUsers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class CelestialBodyPredictionInitializer {
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // CelestialBodyPredictions.CelestialBodyPredictionUpdater Class Constants
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    private static final String userHome = System.getProperty("user.home");
    private static final String celestialBodyPredictionFolderPath = userHome + File.separator + "EnvisionaryApp" + File.separator + "SciencePredictions" + File.separator + "CelestialBodyPredictions";

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // CelestialBodyPredictions.CelestialBodyPrediction Object
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    private static CelestialBodyPrediction userCelestialBodyPrediction;

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Scanner object for reading user input from keyboard
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    public static Scanner scan = new Scanner(System.in);

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // initializeFilePath
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    public static void initializeFilePath() {
        // Create the directory if it doesn't exist
        File directory = new File(celestialBodyPredictionFolderPath);
        if (!directory.exists()) {
            if (directory.mkdirs()) {
                System.out.println("Created directory: " + celestialBodyPredictionFolderPath);
            } else {
                System.err.println("ERROR - Failed to create directory: " + celestialBodyPredictionFolderPath);
            }
        }
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // getUserChoiceCelestialBody
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    public static void getUserChoiceCelestialBody() {
        userCelestialBodyPrediction = new CelestialBodyPrediction();

        ArrayList<CelestialBody> celestialBodyArrayList = CelestialBodyUpdater.readAndReturnCelestialBodiesFile();

        // get user selection
        if (!celestialBodyArrayList.isEmpty()) {
            for (CelestialBody body : celestialBodyArrayList) {
                System.out.println(celestialBodyArrayList.indexOf(body) + 1 + " : " + "Celestial Body: " + body.getCelestialBodyType() + " - Known Count: " + body.getKnownCount() + " - Last Updated: " + body.getUpdatedDate());
            }

            int selectedBodyIndex = 0;
            boolean valid;

            // get user input of celestial body selection
            do {
                valid = true;
                System.out.println("\nSelect a celestial body from the list that you predict will change in number by a certain date:");

                try {
                    selectedBodyIndex = scan.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a valid integer.");
                    scan.next(); // consume the invalid input
                    valid = false;
                }

            } while (!valid || selectedBodyIndex < 1 || selectedBodyIndex > celestialBodyArrayList.size());

            userCelestialBodyPrediction.setCelestialBody(celestialBodyArrayList.get(selectedBodyIndex - 1));
            userCelestialBodyPrediction.getPrediction().setPredictionContent("I predict that there will be a change in the number of " + userCelestialBodyPrediction.getCelestialBody().getCelestialBodyType() + "s");
        } else {
            userCelestialBodyPrediction.setCelestialBody(null);
        }
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // getCelestialBodyPredictionEndDate
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    public static void getCelestialBodyPredictionEndDate() {
        int endDateMonth = 0;
        int endDateDay = 0;
        int endDateYear = 0;
        LocalDate predictionEndDate = null;
        boolean validDate = false;

        Scanner scan = new Scanner(System.in);

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
            } catch (Exception e) {
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

        // Set the prediction end date
        userCelestialBodyPrediction.getPrediction().setPredictionEndDate(startOfEndDay);
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // saveCelestialBodyPrediction
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    public static void saveCelestialBodyPrediction(String userIdentifier) {
        // Set prediction type
        userCelestialBodyPrediction.getPrediction().setPredictionType("Science");

        // Set prediction made date
        userCelestialBodyPrediction.getPrediction().setPredictionMadeDate(ZonedDateTime.now().toString());

        // Initialize new list of CelestialBodyPredictions.CelestialBodyPrediction to load into and save from
        ArrayList<CelestialBodyPrediction> loadedCelestialBodyPredictions = new ArrayList<>();

        // Check if the file exists before attempting to load it
        String filePath = celestialBodyPredictionFolderPath + File.separator + userIdentifier + "_celestial_body_predictions.json";
        File file = new File(filePath);
        if (file.exists()) {
            // Load the file
            try {
                String json = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(filePath)));
                Gson gson = new Gson();
                Type listType = new TypeToken<ArrayList<CelestialBodyPrediction>>() {}.getType();
                loadedCelestialBodyPredictions = gson.fromJson(json, listType);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Add the newly made celestial body prediction to the list
        loadedCelestialBodyPredictions.add(userCelestialBodyPrediction);

        // Save the list to a file in JSON format
        try (FileWriter writer = new FileWriter(filePath)) {
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<CelestialBodyPrediction>>() {}.getType();
            String json = gson.toJson(loadedCelestialBodyPredictions, listType);
            writer.write(json);
            System.out.println("Celestial body predictions have been saved to the file: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // saveCelestialBodyPredictionMongoDB
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    public static void saveCelestialBodyPredictionMongoDB(String userIdentifier) {
        // Set prediction type
        userCelestialBodyPrediction.getPrediction().setPredictionType("Science");

        // Set prediction made date
        userCelestialBodyPrediction.getPrediction().setPredictionMadeDate(ZonedDateTime.now().toString());

        // Initialize new list of CelestialBodyPredictions.CelestialBodyPrediction to load into and save from
        ArrayList<CelestialBodyPrediction> loadedCelestialBodyPredictions = MongoDBEnvisionaryUsers.retrieveUserCelestialBodyPredictions(userIdentifier);
        if (loadedCelestialBodyPredictions == null) {
            loadedCelestialBodyPredictions = new ArrayList<>();
        }

        // Add the newly made celestial body prediction to the list
        loadedCelestialBodyPredictions.add(userCelestialBodyPrediction);

        // Save the list to a file in JSON format
        MongoDBEnvisionaryUsers.updateUserCelestialBodyPredictions(userIdentifier, loadedCelestialBodyPredictions);
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // createNewCelestialBodyPrediction
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    public static void createNewCelestialBodyPrediction(String userIdentifier) {
        boolean valid = true;	// Used for user input validation

        // Predict on celestial bodies?
        String userInput = null;
        do {
            System.out.println("Would you like to make a prediction on celestial bodies? (Y/N)");
            try {
                userInput = scan.next();
            } catch (Exception e) {
                e.printStackTrace();
                valid = false;
            }
        } while (!valid && !userInput.equalsIgnoreCase("Y") && !userInput.equalsIgnoreCase("N"));
        if (userInput.equalsIgnoreCase("Y")) {
            getUserChoiceCelestialBody();
            if (userCelestialBodyPrediction.getCelestialBody() != null) {
                getCelestialBodyPredictionEndDate();
                saveCelestialBodyPrediction(userIdentifier);
            }
            else {
                System.out.println("ERROR - CelestialBodyPredictions.CelestialBodyPrediction: Celestial body is null.");
            }
        }
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // createNewCelestialBodyPredictionMongoDB
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    public static void createNewCelestialBodyPredictionMongoDB(String userIdentifier) {
        boolean valid = true;	// Used for user input validation

        // Predict on celestial bodies?
        String userInput = null;
        do {
            System.out.println("Would you like to make a prediction on celestial bodies? (Y/N)");
            try {
                userInput = scan.next();
            } catch (Exception e) {
                e.printStackTrace();
                valid = false;
            }
        } while (!valid && !userInput.equalsIgnoreCase("Y") && !userInput.equalsIgnoreCase("N"));
        if (userInput.equalsIgnoreCase("Y")) {
            getUserChoiceCelestialBody();
            if (userCelestialBodyPrediction.getCelestialBody() != null) {
                getCelestialBodyPredictionEndDate();
                saveCelestialBodyPredictionMongoDB(userIdentifier);
            }
            else {
                System.out.println("ERROR - CelestialBodyPredictions.CelestialBodyPrediction: Celestial body is null.");
            }
        }
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // removeCelestialBodyPrediction
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    public static void removeCelestialBodyPrediction(String userIdentifier) {
        Scanner scan = new Scanner(System.in);

        // Initialize new array list of CelestialBodyPredictions.CelestialBodyPrediction to load into and save from
        ArrayList<CelestialBodyPrediction> loadedCelestialBodyPredictions = new ArrayList<>();

        // Load the list of user predictions from the file
        String filePath = celestialBodyPredictionFolderPath + File.separator + userIdentifier + "_celestial_body_predictions.json";
        File file = new File(filePath);

        if (file.exists()) {
            try {
                String json = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(filePath)));
                Gson gson = new Gson();
                Type listType = new TypeToken<ArrayList<CelestialBodyPrediction>>() {}.getType();
                loadedCelestialBodyPredictions = gson.fromJson(json, listType);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (loadedCelestialBodyPredictions.isEmpty()) {
            System.out.println("ERROR - No football CelestialBodyPredictions.CelestialBody predictions available to remove.\n");
            return; // Exit the function
        }

        // Display the list of celestial body predictions for the user to select from
        System.out.println("Select a prediction to remove from the list of your previously made celestial body predictions:");
        int predictionIndex = 1;
        for (CelestialBodyPrediction celestialBodyPrediction : loadedCelestialBodyPredictions) {
            System.out.println(predictionIndex + " : " + celestialBodyPrediction.getPrediction().getPredictionContent());
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

        if (predictionRemovalSelection >= 1 && predictionRemovalSelection <= loadedCelestialBodyPredictions.size()) {
            // Ask if the user is sure about the choice
            System.out.print("Are you sure you want to remove this prediction? (Y/N):\n" + loadedCelestialBodyPredictions.get(predictionRemovalSelection - 1).getPrediction().getPredictionContent() + "\n");
            String confirmation = scan.next();

            if (confirmation.equalsIgnoreCase("Y")) {
                // Remove the prediction from the list
                loadedCelestialBodyPredictions.remove(predictionRemovalSelection - 1);

                // Save over the old user prediction file
                try (FileWriter writer = new FileWriter(filePath)) {
                    Gson gson = new Gson();
                    Type listType = new TypeToken<ArrayList<CelestialBodyPrediction>>() {}.getType();
                    String json = gson.toJson(loadedCelestialBodyPredictions, listType);
                    writer.write(json);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("BasePredictionsObject.Prediction removal canceled.");
            }
        } else {
            System.out.println("ERROR - Invalid prediction number.");
        }
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // removeCelestialBodyPredictionMongoDB
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    public static void removeCelestialBodyPredictionMongoDB(String userIdentifier) {
        Scanner scan = new Scanner(System.in);

        // Initialize new array list of CelestialBodyPredictions.CelestialBodyPrediction to update
        ArrayList<CelestialBodyPrediction> loadedCelestialBodyPredictions = MongoDBEnvisionaryUsers.retrieveUserCelestialBodyPredictions(userIdentifier);

        if (loadedCelestialBodyPredictions == null) {
            loadedCelestialBodyPredictions = new ArrayList<>();
            System.out.println("ERROR - No CelestialBodyPredictions.CelestialBody predictions available to remove.\n");
            return; // Exit the function
        }

        // Display the list of celestial body predictions for the user to select from
        System.out.println("Select a prediction to remove from the list of your previously made celestial body predictions:");
        int predictionIndex = 1;
        for (CelestialBodyPrediction celestialBodyPrediction : loadedCelestialBodyPredictions) {
            System.out.println(predictionIndex + " : " + celestialBodyPrediction.getPrediction().getPredictionContent());
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

        if (predictionRemovalSelection >= 1 && predictionRemovalSelection <= loadedCelestialBodyPredictions.size()) {
            // Ask if the user is sure about the choice
            System.out.print("Are you sure you want to remove this prediction? (Y/N):\n" + loadedCelestialBodyPredictions.get(predictionRemovalSelection - 1).getPrediction().getPredictionContent() + "\n");
            String confirmation = scan.next();

            if (confirmation.equalsIgnoreCase("Y")) {
                // Remove the prediction from the list
                loadedCelestialBodyPredictions.remove(predictionRemovalSelection - 1);

                MongoDBEnvisionaryUsers.updateUserCelestialBodyPredictions(userIdentifier, loadedCelestialBodyPredictions);
            } else {
                System.out.println("BasePredictionsObject.Prediction removal canceled.");
            }
        } else {
            System.out.println("ERROR - Invalid prediction number.");
        }
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // printAllUserCelestialBodyPredictions
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    public static void printAllUserCelestialBodyPredictions(String userIdentifier) {
        // Initialize new array list of CelestialBodyPredictions.CelestialBodyPrediction to load file data into
        ArrayList<CelestialBodyPrediction> loadedCelestialBodyPredictions = new ArrayList<>();

        // Check if the file exists before attempting to load it
        String filePath = celestialBodyPredictionFolderPath + File.separator + userIdentifier + "_celestial_body_predictions.json";
        File file = new File(filePath);

        // Check if the file exists before attempting to load it
        if (file.exists()) {
            // Load the file
            try {
                String json = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(filePath)));
                Gson gson = new Gson();
                Type listType = new TypeToken<ArrayList<CelestialBodyPrediction>>() {}.getType();
                loadedCelestialBodyPredictions = gson.fromJson(json, listType);
                System.out.println("\n====================================================================================================");
                System.out.println("                                 " + userIdentifier + "'s Celestial Body Predictions");
                // For each CelestialBodyPredictions.CelestialBody prediction within the array list of loaded CelestialBodyPredictions.CelestialBody predictions
                for (CelestialBodyPrediction celestialBodyPrediction : loadedCelestialBodyPredictions) {
                    System.out.println(celestialBodyPrediction.getPrediction().getPredictionContent() + " by " + celestialBodyPrediction.getPrediction().getPredictionEndDate());
                }
                System.out.println();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("ERROR - Celestial body prediction file does not exist for user: " + userIdentifier + "\n");
        }
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // printAllUserCelestialBodyPredictionsMongoDB
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    public static void printAllUserCelestialBodyPredictionsMongoDB(String userIdentifier) {
        // Initialize new array list of CelestialBodyPredictions.CelestialBodyPrediction to load file data into
        ArrayList<CelestialBodyPrediction> loadedCelestialBodyPredictions = MongoDBEnvisionaryUsers.retrieveUserCelestialBodyPredictions(userIdentifier);

        if (loadedCelestialBodyPredictions == null) {
            loadedCelestialBodyPredictions = new ArrayList<>();
        }
        System.out.println("\n====================================================================================================");
        System.out.println("                                 " + userIdentifier + "'s Celestial Body Predictions");
        // For each CelestialBodyPredictions.CelestialBody prediction within the array list of loaded CelestialBodyPredictions.CelestialBody predictions
        for (CelestialBodyPrediction celestialBodyPrediction : loadedCelestialBodyPredictions) {
            System.out.println(celestialBodyPrediction.getPrediction().getPredictionContent() + " by " + celestialBodyPrediction.getPrediction().getPredictionEndDate());
        }
        System.out.println();
    }
}
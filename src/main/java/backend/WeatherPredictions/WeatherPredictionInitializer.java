package backend.WeatherPredictions;

import backend.CelestialBodyPredictions.CelestialBodyPrediction;
import backend.UserInfo.MongoDBEnvisionaryUsers;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class WeatherPredictionInitializer {
    private static CelestialBodyPrediction userCelestialBodyPrediction;

    public static Scanner scan = new Scanner(System.in);

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // createNewWeatherPredictionMongoDB
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    public static void createNewCelestialBodyPredictionMongoDB(String userIdentifier) {
        boolean valid = true;	// Used for user input validation

        // Predict on celestial bodies?
        String userInput = null;
        do {
            System.out.println("Would you like to make a prediction on the weather? (Y/N)");
            try {
                userInput = scan.next();
            } catch (Exception e) {
                e.printStackTrace();
                valid = false;
            }
        } while (!valid && !userInput.equalsIgnoreCase("Y") && !userInput.equalsIgnoreCase("N"));
        if (userInput.equalsIgnoreCase("Y")) {
            getUserChoiceTemperature();
            if (userCelestialBodyPrediction.getCelestialBody() != null) {
                getCelestialBodyPredictionEndDate();
                saveCelestialBodyPredictionMongoDB(userIdentifier);
            }
            else {
                System.out.println("ERROR - CelestialBodyPredictions.CelestialBodyPrediction: Celestial body is null.");
            }
        }
    }

    private static void getUserChoiceTemperature() {
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
}

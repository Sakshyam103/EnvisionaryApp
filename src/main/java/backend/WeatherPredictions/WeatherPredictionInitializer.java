package backend.WeatherPredictions;

import backend.CelestialBodyPredictions.CelestialBodyPrediction;
import backend.UserInfo.MongoDBEnvisionaryUsers;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class WeatherPredictionInitializer {
    private static WeatherPrediction userWeatherPrediction;

    public static Scanner scan = new Scanner(System.in);

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // createNewWeatherPredictionMongoDB
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    public static void createNewWeatherPredictionMongoDB(String userIdentifier) {
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
            getWeatherPredictionEndDate();
            saveWeatherPredictionMongoDB(userIdentifier);
        }
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // getUserChoiceTemperature
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    private static void getUserChoiceTemperature() {
        userWeatherPrediction = new WeatherPrediction();
        String highLowInput = "";
        int predictionTemperature;

        while (!highLowInput.equalsIgnoreCase("Y") && !highLowInput.equalsIgnoreCase("N")) {
            System.out.println("Is this a high temperature prediction? (Y/N)");
            highLowInput = scan.next();
        }

        System.out.println("Enter your predicted temperature:");
        predictionTemperature = scan.nextInt();

        if (highLowInput.equalsIgnoreCase("Y")) {
            userWeatherPrediction.setHighTempPrediction(true);
            userWeatherPrediction.getPrediction().setPredictionContent("I predict that the temperature will be hit highs of " + predictionTemperature + " degrees Fahrenheit");
            userWeatherPrediction.getPrediction().setPredictionMadeDate(ZonedDateTime.now().toString());
            //userWeatherPrediction.getPrediction().setRemindFrequency("");
        } else if (highLowInput.equalsIgnoreCase("N")) {
            userWeatherPrediction.setHighTempPrediction(false);
            userWeatherPrediction.getPrediction().setPredictionContent("I predict that the temperature will be hit lows of " + predictionTemperature + " degrees Fahrenheit");
            userWeatherPrediction.getPrediction().setPredictionMadeDate(ZonedDateTime.now().toString());
            //userWeatherPrediction.getPrediction().setRemindFrequency("");
        }

        userWeatherPrediction.setTemperature(predictionTemperature);
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // getWeatherPredictionEndDate
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    private static void getWeatherPredictionEndDate() {
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
        userWeatherPrediction.getPrediction().setPredictionEndDate(startOfEndDay);
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // saveWeatherPredictionMongoDB
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    public static void saveWeatherPredictionMongoDB(String userIdentifier) {
        // Set prediction type
        userWeatherPrediction.getPrediction().setPredictionType("Weather");

        // Set prediction made date
        userWeatherPrediction.getPrediction().setPredictionMadeDate(ZonedDateTime.now().toString());

        // Initialize new list of WeatherPredictions.WeatherPrediction to load into and save from
        ArrayList<WeatherPrediction> loadedWeatherPredictions = MongoDBEnvisionaryUsers.retrieveUserWeatherPredictions(userIdentifier);
        if (loadedWeatherPredictions == null) {
            loadedWeatherPredictions = new ArrayList<>();
        }

        // Add the newly made celestial body prediction to the list
        loadedWeatherPredictions.add(userWeatherPrediction);

        // Save the list to a file in JSON format
        MongoDBEnvisionaryUsers.updateUserWeatherPredictions(userIdentifier, loadedWeatherPredictions);
    }



    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // printAllUserWeatherPredictionsMongoDB
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    public static void printAllUserWeatherPredictionsMongoDB(String userIdentifier) {
        // Initialize new array list of CelestialBodyPrediction to load file data into
        ArrayList<WeatherPrediction> loadedWeatherPredictions = MongoDBEnvisionaryUsers.retrieveUserWeatherPredictions(userIdentifier);

        if (loadedWeatherPredictions == null) {
            loadedWeatherPredictions = new ArrayList<>();
        }
        System.out.println("\n====================================================================================================");
        System.out.println("                                 " + userIdentifier + "'s Weather Predictions");
        // For each CelestialBody prediction within the array list of loaded CelestialBody predictions
        for (WeatherPrediction weatherPrediction : loadedWeatherPredictions) {
            System.out.println(weatherPrediction.getPrediction().getPredictionContent() + " by " + weatherPrediction.getPrediction().getPredictionEndDate());
        }
        System.out.println();
    }

    public static void main(String[] args){
        ArrayList<WeatherPrediction> emptyList = new ArrayList<>();
        MongoDBEnvisionaryUsers.updateUserWeatherPredictions("bLapointe", emptyList);
        createNewWeatherPredictionMongoDB("bLapointe");
    }
}

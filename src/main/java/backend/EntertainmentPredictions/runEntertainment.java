package backend.EntertainmentPredictions;

import backend.v2Prototype;

import javax.json.JsonArray;
import javax.json.JsonValue;
import java.util.Scanner;

import static backend.EntertainmentPredictions.buildEntertainmentPrediction.buildMoviePrediction;
import static backend.EntertainmentPredictions.checkGuess.checkUserPrediction;
import static backend.EntertainmentPredictions.entertainmentAmountCheck.EntertainmentTrigger;
import static backend.EntertainmentPredictions.entertainmentAmountCheck.makeOrBreak;
import static backend.EntertainmentPredictions.makeMovieGuess.getPredictionFromUser;

public class runEntertainment {

    public static String movieTitle;
    public static int year;

    public static void runEntertainmentPrediction(String userID){
        if (makeOrBreak()) {
           checkGood(userID);
        }
        else{
            checkBad(userID);
        }
    }

    private static void checkGood(String userID){
        EntertainmentTrigger();
        getPredictionFromUser();
        JsonArray array = movieAPI.connectToMovie();
        JsonValue movieInfo = parseMovieArray.getCorrectMovie(array);
        checkUserPrediction(movieInfo);
        EntertainmentPrediction moviePrediction = buildMoviePrediction();
        EntertainmentPredictionInitializer.saveEntertainmentPredictionMongoDB(userID, moviePrediction);
        EntertainmentTrigger();
    }

    private static void checkBad(String userID) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("We're sorry, due to restraints your prediction will be unable to be resolved today" +
                "\nWould you still like to continue?" +
                "\n1. Yes" +
                "\n2. No");
        int response = scanner.nextInt();
        switch (response) {
            case 1 -> checkGood(userID);
            case 2 -> {
                System.out.println("You will now be returned to the main menu");
                v2Prototype.userMenu();
            }
            default -> {
                System.out.println("Invalid selection. PLease input 1 or 2.");
                runEntertainmentPrediction(userID);
            }
        }
    }


}

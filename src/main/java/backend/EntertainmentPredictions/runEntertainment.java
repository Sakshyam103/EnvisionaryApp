package backend.EntertainmentPredictions;

import backend.ResolvedPredictions.ResolvedPrediction;
import javax.json.JsonArray;
import javax.json.JsonValue;
import static backend.EntertainmentPredictions.buildEntertainmentPrediction.buildMoviePrediction;
import static backend.EntertainmentPredictions.checkGuess.checkUserPrediction;
import static backend.EntertainmentPredictions.entertainmentAmountCheck.EntertainmentTrigger;
import static backend.EntertainmentPredictions.entertainmentAmountCheck.makeOrBreak;

public class runEntertainment {

    public static String movieTitle;
    public static int year;
    public static boolean exists;
    public static boolean canMake;
    public static int trueYear;
    public static boolean resolve;

    public static void runEntertainmentPrediction(String userID){
//        canMake = makeOrBreak();
//        if (canMake) {
//           checkGood(userID);
//        }
        checkGood(userID);
    }

    private static void checkGood(String userID){
        JsonArray array = movieAPI.connectToMovie();
        JsonValue movieInfo = parseMovieArray.getCorrectMovie(array);
        resolve = checkUserPrediction(movieInfo);
    }
}


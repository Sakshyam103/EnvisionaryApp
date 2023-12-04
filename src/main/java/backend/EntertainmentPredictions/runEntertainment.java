//package backend.EntertainmentPredictions;
//
//import backend.ResolvedPredictions.ResolvedPrediction;
//import backend.v2Prototype;
//
//import javax.json.JsonArray;
//import javax.json.JsonValue;
//import java.util.Scanner;
//
//import static backend.EntertainmentPredictions.buildEntertainmentPrediction.buildMoviePrediction;
//import static backend.EntertainmentPredictions.checkGuess.checkUserPrediction;
//import static backend.EntertainmentPredictions.entertainmentAmountCheck.EntertainmentTrigger;
//import static backend.EntertainmentPredictions.entertainmentAmountCheck.makeOrBreak;
//import static backend.EntertainmentPredictions.makeMovieGuess.getPredictionFromUser;
//
//public class runEntertainment {
//
//    public static String movieTitle;
//    public static int year;
//    public static boolean exists;
//    public static boolean canMake;
//    public static int trueYear;
//    public static boolean resolve;
//
//    public static void runEntertainmentPrediction(String userID){
//        canMake = makeOrBreak();
//        if (canMake) {
//           checkGood(userID);
//        }
//    }
//
//    private static void checkGood(String userID){
//        EntertainmentTrigger();
//        // run entertainment prediction from frontend
//
//        JsonArray array = movieAPI.connectToMovie();
//        JsonValue movieInfo = parseMovieArray.getCorrectMovie(array);
//        resolve = checkUserPrediction(movieInfo);
//        ResolvedPrediction moviePrediction = buildMoviePrediction();
//        EntertainmentPredictionInitializer.resolveEntertainmentPrediction(userID, moviePrediction);
//        EntertainmentTrigger();
//    }
//}
//

package backend.EntertainmentPredictions;

import javax.json.JsonArray;
import javax.json.JsonValue;
import static backend.EntertainmentPredictions.checkGuess.checkUserPrediction;
import static backend.EntertainmentPredictions.makeMovieGuess.getPredictionFromUser;

public class runEntertainment {

    public static String movieTitle;
    public static int year;

    public static void runEntertainmentPrediction(){
        getPredictionFromUser();
        JsonArray array = movieAPI.connectToMovie();
        JsonValue movieInfo = parseMovieArray.getCorrectMovie(array);
        checkUserPrediction(movieInfo);
    }

}

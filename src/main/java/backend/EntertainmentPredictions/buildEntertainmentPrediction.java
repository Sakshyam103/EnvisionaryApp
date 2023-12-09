package backend.EntertainmentPredictions;

import backend.ResolvedPredictions.ResolvedPrediction;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.ZonedDateTime;

import static backend.EntertainmentPredictions.EntertainmentPredictionInitializer.resolveEntertainmentPrediction;
import static backend.EntertainmentPredictions.EntertainmentPredictionInitializer.saveNewCustomMovieToMongo;

public class buildEntertainmentPrediction {

    public static ResolvedPrediction prediction = new ResolvedPrediction();

    public static String userInput;

    public static String userID;

    public static String buildMoviePrediction(JSONObject json, String ID) throws JSONException {

        userID = ID;

        setPrediction(json);

        boolean check = entertainmentAmountCheck.makeOrBreak();

        boolean success = false;

        prediction.setPredictionType("Entertainment");

        prediction.setPredictionMadeDate(ZonedDateTime.now().toString());

        prediction.setPredictionContent("I predict that " + runEntertainment.movieTitle + " was released in " + runEntertainment.year);

        prediction.setPredictionEndDate(ZonedDateTime.now().toString());

        if (!check) {
            success = saveNewCustomMovieToMongo(prediction);
            if (success) {
                return "Custom Prediction Saved";
            } else {
                return "Error Saving Custom Prediction";
            }
        }

        runEntertainment.runEntertainmentPrediction(userID);

        prediction.setResolution(runEntertainment.resolve);

        prediction.setResolvedDate(ZonedDateTime.now().toString());

        success = resolveEntertainmentPrediction(userID, prediction);

        if (success) {
            return String.valueOf(prediction.getResolution());
        } else {
            return "Error Saving Movie Prediction";
        }
    }

    private static void setPrediction(JSONObject json) throws JSONException {
        runEntertainment.movieTitle = json.getString("Prediction");
        runEntertainment.year = json.getInt("ReleaseYear");
    }
}

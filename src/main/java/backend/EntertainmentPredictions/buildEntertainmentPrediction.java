package backend.EntertainmentPredictions;

import backend.ResolvedPredictions.ResolvedPrediction;
import org.json.JSONException;
import org.json.JSONObject;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;
import java.io.StringReader;
import java.time.ZonedDateTime;

import static backend.EntertainmentPredictions.EntertainmentPredictionInitializer.resolveEntertainmentPrediction;

public class buildEntertainmentPrediction {

    public static ResolvedPrediction prediction = new ResolvedPrediction();

    public static String userInput;

    public static String userID;

    public static void buildMoviePrediction(JsonObject json, String ID) throws JSONException {

        userID = ID;

        setPrediction(json);

        runEntertainment.runEntertainmentPrediction(userID);

        prediction.setPredictionType("Entertainment");

        prediction.setPredictionMadeDate(ZonedDateTime.now().toString());

        prediction.setPredictionContent("I predict that " + runEntertainment.movieTitle + " was released in " + runEntertainment.year);

        prediction.setPredictionEndDate(ZonedDateTime.now().toString());

        prediction.setResolution(runEntertainment.resolve);

        prediction.setResolvedDate(ZonedDateTime.now().toString());

        resolveEntertainmentPrediction(userID, prediction);
    }

    private static void setPrediction(JsonObject json) throws JSONException {
        runEntertainment.movieTitle = json.getString("Prediction");
        runEntertainment.year = json.getInt("ReleaseYear");
    }
}


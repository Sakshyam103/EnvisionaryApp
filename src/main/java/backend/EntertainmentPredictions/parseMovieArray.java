package backend.EntertainmentPredictions;

import javax.json.JsonArray;
import javax.json.JsonValue;

public class parseMovieArray {

    public static JsonValue getCorrectMovie(JsonArray array){
        for (JsonValue jsonValue : array) {
            if (jsonValue.asJsonObject().getString("title").trim().equalsIgnoreCase(runEntertainment.movieTitle)){
                return jsonValue;
            }
        }
        pickCorrectMovie(array);
        return null;
    }

    private static void pickCorrectMovie(JsonArray array){
        runEntertainment.exists = false;
    }
}

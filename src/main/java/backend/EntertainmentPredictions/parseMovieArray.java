//package backend.EntertainmentPredictions;
//
//import java.util.Scanner;
//
//import javax.json.JsonArray;
//import javax.json.JsonValue;
//
//public class parseMovieArray {
//
//    public static JsonValue getCorrectMovie(JsonArray array){
//        for (JsonValue jsonValue : array) {
//            if (jsonValue.asJsonObject().getString("title").trim().equalsIgnoreCase(runEntertainment.movieTitle)){
//                return jsonValue;
//            }
//        }
//        return pickCorrectMovie(array);
//    }
//
//    private static JsonValue pickCorrectMovie(JsonArray array){
//        System.out.println("\n \n \n \n _____________________________________________________________");
//        System.out.println("\n Sorry, but it seems more than one movie was found with that title, please select from the list below which one you want!");
//        int i = 0;
//        for (JsonValue jsonValue : array) {
//            System.out.println(++i + ": " + jsonValue.asJsonObject().getString("title") + "\n");
//        }
//        Scanner scanner = new Scanner(System.in);
//        int trueMovie = scanner.nextInt();
//        scanner.close();
//        return array.get(trueMovie);
//    }
//}

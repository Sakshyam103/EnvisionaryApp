//package backend.EntertainmentPredictions;
//
//import javax.json.JsonValue;
//
//public class checkGuess {
//
//    public static void checkUserPrediction(JsonValue movieInfo){
//        int correctYear = movieInfo.asJsonObject().getInt("year");
//        String resolution = checkDate(correctYear);
//        System.out.println("\n \n \n \n _____________________________________________________________");
//        System.out.println("\n \n \n \n" + movieInfo.asJsonObject().getString("title") + " was released in " + movieInfo.asJsonObject().getInt("year"));
//        System.out.println("\n You were " + resolution);
//    }
//
//    private static String checkDate(int correctYear) {
//        if(correctYear == runEntertainment.year){
//            return "correct! \n CONGRATULATIONS";
//        }
//        return "incorrect! \n You were " + Math.abs(correctYear - runEntertainment.year) + " years wrong!";
//    }
//
//
//}

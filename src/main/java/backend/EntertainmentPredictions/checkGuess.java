//package backend.EntertainmentPredictions;
//
//import javax.json.JsonValue;
//
//public class checkGuess {
//
//    public static boolean checkUserPrediction(JsonValue movieInfo){
//        int correctYear = movieInfo.asJsonObject().getInt("year");
//        runEntertainment.trueYear = correctYear;
//        return checkDate(correctYear);
//    }
//
//    private static boolean checkDate(int correctYear) {
//        if(correctYear == runEntertainment.year){
//            return true;
//        }
//        return false;
//    }
//
//
//}

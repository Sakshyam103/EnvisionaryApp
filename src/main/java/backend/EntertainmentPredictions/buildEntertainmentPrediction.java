//package backend.EntertainmentPredictions;
//
//import backend.ResolvedPredictions.ResolvedPrediction;
//
//import java.sql.Time;
//import java.time.ZonedDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.Scanner;
//
//public class buildEntertainmentPrediction {
//
//    public static ResolvedPrediction prediction = new ResolvedPrediction();
//
//    public static ResolvedPrediction buildMoviePrediction() {
//
//        prediction.setPredictionType("Entertainment");
//
//        prediction.setPredictionMadeDate(ZonedDateTime.now().toString());
//
//        prediction.setPredictionContent("I predict that " + runEntertainment.movieTitle + " was released in " + runEntertainment.year);
//
//        prediction.setPredictionEndDate(ZonedDateTime.now().toString());
//
//        prediction.setResolution(runEntertainment.resolve);
//
//        prediction.setResolvedDate(ZonedDateTime.now().toString());
//
//        return prediction;
//    }
//}
//

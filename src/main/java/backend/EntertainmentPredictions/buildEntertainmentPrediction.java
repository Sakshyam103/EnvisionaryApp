package backend.EntertainmentPredictions;

import java.sql.Time;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class buildEntertainmentPrediction {

    public static EntertainmentPrediction prediction = new EntertainmentPrediction();

    public static EntertainmentPrediction buildMoviePrediction() {
        // set movie
        prediction.setMovieTitle(runEntertainment.movieTitle);

        // set predicted year
        prediction.setPredictedYear(runEntertainment.year);

        prediction.getPrediction().setPredictionType("Entertainment");

        prediction.getPrediction().setPredictionMadeDate(ZonedDateTime.now().toString());

        prediction.getPrediction().setPredictionEndDate(setResolveDate());

        prediction.getPrediction().setPredictionContent("I predict that " + runEntertainment.movieTitle + " was released in " + runEntertainment.year);

        return prediction;
    }

    private static String setResolveDate(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please select when you would like this prediction to resolve! \n 1. Now \n 2. Tomorrow \n 3. Pick a date ");
        int reminder = scanner.nextInt();
        ZonedDateTime resolveDate = null;
        switch (reminder) {
            case 1 -> {
                scanner.close();
                resolveDate = ZonedDateTime.now();
                return resolveDate.toString();
            }
            case 2 -> {
                scanner.close();
                resolveDate = ZonedDateTime.now().plusDays(1);
                return resolveDate.toString();
            }
            case 3 -> {
                System.out.println("When would you like this prediction to resolve? Please enter in DD/MM/YYYY format");
                String date = scanner.next();
                scanner.close();
                resolveDate = ZonedDateTime.parse(date);
                return resolveDate.toString();
            }
            default -> {
                System.out.println("Invalid selection. Please enter a number (1-3)");
                scanner.close();
                setResolveDate();
            }
        }
        return null;
    }
}


package backend.WeatherPredictions;

import backend.Notifications.NotificationUpdater;
import backend.OverallStatistics.OverallDescriptiveStatisticsUpdater;
import backend.OverallStatistics.OverallInferentialStatisticsUpdater;
import backend.ResolvedPredictions.ResolvedPrediction;
import backend.UserInfo.EnvisionaryUser;
import backend.UserInfo.MongoDBEnvisionaryUsers;
import backend.UserStatistics.UserDescriptiveStatisticsUpdater;
import backend.UserStatistics.UserInferentialStatisticsUpdater;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;

public class WeatherPredictionUpdater {
    public static void updateWeatherPredictionsMongoDB() {
        // Retrieve collection of EnvisionaryUsers
        ArrayList<EnvisionaryUser> envisionaryUsers = MongoDBEnvisionaryUsers.retrieveCollection();

        // Retrieve today's weather data
        DailyForecast todaysWeather = MongoDBWeatherData.retrieveCollection();
        todaysWeather.displayDailyForecast();

        // For each Envisionary UserInfo.User
        for (EnvisionaryUser user : envisionaryUsers) {
            // Initialize removedPredictions boolean flag to false
            boolean removedPredictions = false;

            // Initialize an array list of the user's weather predictions
            ArrayList<WeatherPrediction> userWeatherPredictions = user.getWeatherPredictions();

            // Initialize a new array list of celestial body predictions to store predictions that need to be removed
            ArrayList<WeatherPrediction> predictionsToRemove = new ArrayList<>();

            // For each prediction within array list of WeatherPrediction
            for (WeatherPrediction userWeatherPrediction : userWeatherPredictions) {
                // Initialize remove prediction boolean flag
                boolean removePrediction = false;

                // String date from the userWeatherPrediction
                String predictionDateString = userWeatherPrediction.getPrediction().getPredictionEndDate();

                // If prediction end date equals today's date && userWeatherPrediction is a high temperature prediction && userWeatherPrediction is equal to today's weather forecast max temperature
                if (predictionDateString.equals(LocalDate.now().toString()) &&
                        userWeatherPrediction.getHighTempPrediction() &&
                        userWeatherPrediction.getTemperature() == todaysWeather.getMaxTemp()) {

                    // Initialize new resolved prediction
                    ResolvedPrediction resolvedWeatherPrediction = new ResolvedPrediction();

                    // Copy over celestial body prediction values to the new resolved prediction
                    resolvedWeatherPrediction.setPredictionType(userWeatherPrediction.getPrediction().getPredictionType());
                    resolvedWeatherPrediction.setPredictionContent(userWeatherPrediction.getPrediction().getPredictionContent());
                    resolvedWeatherPrediction.setPredictionMadeDate(userWeatherPrediction.getPrediction().getPredictionMadeDate());
                    resolvedWeatherPrediction.setPredictionEndDate(userWeatherPrediction.getPrediction().getPredictionEndDate());
                    resolvedWeatherPrediction.setResolution(true);

                    // Set the resolved date of the ResolvedPrediction
                    resolvedWeatherPrediction.setResolvedDate(ZonedDateTime.now().toString());

                    // Initialize new array list of ResolvedPrediction to update
                    ArrayList<ResolvedPrediction> userResolvedPredictions = user.getResolvedPredictions();

                    // Add the new resolved prediction to the list
                    userResolvedPredictions.add(resolvedWeatherPrediction);

                    // Update the user's resolved predictions list
                    MongoDBEnvisionaryUsers.updateUserResolvedPredictions(user.getUserID(), userResolvedPredictions);

                    // Send notification to the user
                    NotificationUpdater.newWeatherPredictionResolvedTrueNotificationMongoDB(userWeatherPrediction, user.getUserID());

                    // Set remove prediction flag to true
                    removePrediction = true;
                    removedPredictions = true;

                    // Calculate statistics of user and update UserStatistics, UserStatistics.UserInferentialStatistics, and OverallStatistics
                    UserDescriptiveStatisticsUpdater.calculateAndSaveUserDescriptiveStatisticsMongoDB(user.getUserID());
                    UserInferentialStatisticsUpdater.calculateAndSaveUserInferentialStatisticsMongoDB(user.getUserID());
                    OverallDescriptiveStatisticsUpdater.calculateAndSaveOverallDescriptiveStatisticsMongoDB();
                    OverallInferentialStatisticsUpdater.calculateAndSaveOverallInferentialStatisticsMongoDB();
                }
                // Else if prediction end date equals today's date && userWeatherPrediction is a low temperature prediction && userWeatherPrediction is equal to today's weather forecast min temperature
                else if (predictionDateString.equals(LocalDate.now().toString()) &&
                        !userWeatherPrediction.getHighTempPrediction() &&
                        userWeatherPrediction.getTemperature() == todaysWeather.getMinTemp()) {

                    // Initialize new resolved prediction
                    ResolvedPrediction resolvedWeatherPrediction = new ResolvedPrediction();

                    // Copy over celestial body prediction values to the new resolved prediction
                    resolvedWeatherPrediction.setPredictionType(userWeatherPrediction.getPrediction().getPredictionType());
                    resolvedWeatherPrediction.setPredictionContent(userWeatherPrediction.getPrediction().getPredictionContent());
                    resolvedWeatherPrediction.setPredictionMadeDate(userWeatherPrediction.getPrediction().getPredictionMadeDate());
                    resolvedWeatherPrediction.setPredictionEndDate(userWeatherPrediction.getPrediction().getPredictionEndDate());
                    resolvedWeatherPrediction.setResolution(true);

                    // Set the resolved date of the ResolvedPrediction
                    resolvedWeatherPrediction.setResolvedDate(ZonedDateTime.now().toString());

                    // Initialize new array list of ResolvedPrediction to update
                    ArrayList<ResolvedPrediction> userResolvedPredictions = user.getResolvedPredictions();

                    // Add the new resolved prediction to the list
                    userResolvedPredictions.add(resolvedWeatherPrediction);

                    // Update the user's resolved predictions list
                    MongoDBEnvisionaryUsers.updateUserResolvedPredictions(user.getUserID(), userResolvedPredictions);

                    // Send notification to the user
                    NotificationUpdater.newWeatherPredictionResolvedTrueNotificationMongoDB(userWeatherPrediction, user.getUserID());

                    // Set remove prediction flag and removed predictions flag to true
                    removePrediction = true;
                    removedPredictions = true;

                    // Calculate statistics of user and update UserStatistics, UserStatistics.UserInferentialStatistics, and OverallStatistics
                    UserDescriptiveStatisticsUpdater.calculateAndSaveUserDescriptiveStatisticsMongoDB(user.getUserID());
                    UserInferentialStatisticsUpdater.calculateAndSaveUserInferentialStatisticsMongoDB(user.getUserID());
                    OverallDescriptiveStatisticsUpdater.calculateAndSaveOverallDescriptiveStatisticsMongoDB();
                    OverallInferentialStatisticsUpdater.calculateAndSaveOverallInferentialStatisticsMongoDB();
                }
                // Else if prediction end date equals today's date & above if statements haven't been triggered
                else if (predictionDateString.equals(LocalDate.now().toString())) {
                    System.out.println("\n\nDEBUG LINE\n\n");
                    System.out.println(userWeatherPredictions.size());
                    // Initialize new resolved prediction
                    ResolvedPrediction resolvedWeatherPrediction = new ResolvedPrediction();

                    // Copy over football Weather prediction values to the new resolved prediction
                    resolvedWeatherPrediction.setPredictionType(userWeatherPrediction.getPrediction().getPredictionType());
                    resolvedWeatherPrediction.setPredictionContent(userWeatherPrediction.getPrediction().getPredictionContent());
                    resolvedWeatherPrediction.setPredictionMadeDate(userWeatherPrediction.getPrediction().getPredictionMadeDate());
                    resolvedWeatherPrediction.setPredictionEndDate(userWeatherPrediction.getPrediction().getPredictionEndDate());
                    resolvedWeatherPrediction.setResolution(false);

                    // Set the resolved date of the ResolvedPredictions.ResolvedPrediction
                    resolvedWeatherPrediction.setResolvedDate(ZonedDateTime.now().toString());

                    // Initialize new array list of ResolvedPredictions.ResolvedPrediction to update
                    ArrayList<ResolvedPrediction> userResolvedPredictions = user.getResolvedPredictions();

                    // Add the new resolved prediction to the file
                    userResolvedPredictions.add(resolvedWeatherPrediction);

                    // Save the resolved prediction to the user's resolved prediction list
                    MongoDBEnvisionaryUsers.updateUserResolvedPredictions(user.getUserID(), userResolvedPredictions);

                    // Send a notification to the user
                    NotificationUpdater.newWeatherPredictionResolvedFalseNotificationMongoDB(userWeatherPrediction, user.getUserID());

                    // Set removePrediction boolean flag to true
                    removePrediction = true;
                    removedPredictions = true;

                    // Calculate statistics of user and update UserStatistics, UserStatistics.UserInferentialStatistics, and OverallStatistics
                    UserDescriptiveStatisticsUpdater.calculateAndSaveUserDescriptiveStatisticsMongoDB(user.getUserID());
                    UserInferentialStatisticsUpdater.calculateAndSaveUserInferentialStatisticsMongoDB(user.getUserID());
                    OverallDescriptiveStatisticsUpdater.calculateAndSaveOverallDescriptiveStatisticsMongoDB();
                    OverallInferentialStatisticsUpdater.calculateAndSaveOverallInferentialStatisticsMongoDB();
                }
                // If removePrediction boolean flag is true
                if (removePrediction) {
                    // Add the prediction to the array list of football Weather predictions to remove
                    predictionsToRemove.add(userWeatherPrediction);
                }
            }
            if (removedPredictions) {
                System.out.println(userWeatherPredictions.size());

                // Remove the resolved and cancelled predictions
                userWeatherPredictions.removeAll(predictionsToRemove);

                // Save the updated list to the user's prediction file
                MongoDBEnvisionaryUsers.updateUserWeatherPredictions(user.getUserID(), userWeatherPredictions);
            }
        }
    }
}

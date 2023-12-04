//package backend.EntertainmentPredictions;
//
//import backend.OverallStatistics.OverallDescriptiveStatisticsUpdater;
//import backend.OverallStatistics.OverallInferentialStatisticsUpdater;
//import backend.ResolvedPredictions.ResolvedPrediction;
//import backend.UserInfo.MongoDBEnvisionaryUsers;
//import backend.UserStatistics.UserDescriptiveStatisticsUpdater;
//import backend.UserStatistics.UserInferentialStatisticsUpdater;
//import java.util.ArrayList;
//
//public class EntertainmentPredictionInitializer {
//
//    public static void resolveEntertainmentPrediction(String userIdentifier, ResolvedPrediction prediction) {
//        // Initialize new array list of ResolvedPredictions.ResolvedPrediction to load into and save from
//        ArrayList<ResolvedPrediction> loadedResolvedPredictions = MongoDBEnvisionaryUsers.retrieveUserResolvedPredictions(userIdentifier);
//
//        if (loadedResolvedPredictions == null) {
//            loadedResolvedPredictions = new ArrayList<>();
//        }
//
//        // Add the new resolved prediction to the list
//        loadedResolvedPredictions.add(prediction);
//
//        // Save the resolved prediction to the user's resolved prediction list in JSON format
//        MongoDBEnvisionaryUsers.updateUserResolvedPredictions(userIdentifier, loadedResolvedPredictions);
//
//
//        // Update UserStatistics.UserDescriptiveStatistics, UserStatistics.UserInferentialStatistics, and OverallStatistics
//        UserDescriptiveStatisticsUpdater.calculateAndSaveUserDescriptiveStatisticsMongoDB(userIdentifier);
//        UserInferentialStatisticsUpdater.calculateAndSaveUserInferentialStatisticsMongoDB(userIdentifier);
//        OverallDescriptiveStatisticsUpdater.calculateAndSaveOverallDescriptiveStatisticsMongoDB();
//        OverallInferentialStatisticsUpdater.calculateAndSaveOverallInferentialStatisticsMongoDB();
//    }
//}

package backend.CelestialBodyPredictions;
import backend.ResolvedPredictions.ResolvedPrediction;
import backend.Notifications.NotificationUpdater;
import backend.UserStatistics.UserDescriptiveStatisticsUpdater;
import backend.UserStatistics.UserInferentialStatisticsUpdater;
import backend.OverallStatistics.OverallDescriptiveStatisticsUpdater;
import backend.OverallStatistics.OverallInferentialStatisticsUpdater;
import backend.UserInfo.EnvisionaryUser;
import backend.UserInfo.MongoDBEnvisionaryUsers;
import backend.DateTimeConverter.DateTimeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.ZonedDateTime;
import java.util.ArrayList;


public class CelestialBodyPredictionUpdater {
    //*****************************************************************************
    // CelestialBodyPredictionUpdater Class Constants & Variables
    private static final String userHome = System.getProperty("user.home");
    private static final String celestialBodiesFolderPath = userHome + File.separator + "EnvisionaryApp" + File.separator + "SciencePredictions" + File.separator + "CelestialBodyData";
    private static final String celestialBodyPredictionFolderPath = userHome + File.separator + "EnvisionaryApp" + File.separator + "SciencePredictions" + File.separator + "CelestialBodyPredictions";
    private static final String resolvedPredictionFolderPath = userHome + File.separator + "EnvisionaryApp" + File.separator + "ResolvedPredictions";

    //*****************************************************************************
    public static void updateCelestialBodyPredictions() {
        // userIdentifier collected from the file path of the current football match prediction file being checked
        String userIdentifier;

        // Initialize array lists of CelestialBodyPrediction and CelestialBody for comparison
        ArrayList<CelestialBodyPrediction> loadedUserCelestialBodyPredictions = new ArrayList<>();
        ArrayList<CelestialBody> loadedCelestialBodyArrayList = new ArrayList<>();

        // Check if the today's CelestialBody file exists before attempting to load it
        String celestialBodyFilePath = celestialBodiesFolderPath + File.separator + "celestial_bodies.json";
        File todayCelestialBodyFile = new File(celestialBodyFilePath);
        if (todayCelestialBodyFile.exists()) {
            // Load the file
            try {
                String json = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(celestialBodyFilePath)));
                Gson gson = new Gson();
                Type listType = new TypeToken<ArrayList<CelestialBody>>() {}.getType();
                loadedCelestialBodyArrayList = gson.fromJson(json, listType);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("ERROR - Celestial body file does not exist.");
            return;
        }

        // Initialize predictionFilesFolder as a new File
        File predictionFilesFolder = new File(celestialBodyPredictionFolderPath + File.separator + "CelestialBodyPredictions");

        // If the prediction files folder exists and is a directory
        if (predictionFilesFolder.exists() && predictionFilesFolder.isDirectory()) {
            // Initialize the file array of user celestial body prediction files
            File[] celestialBodyPredictionFiles = predictionFilesFolder.listFiles();
            // If the file array is not null
            if (celestialBodyPredictionFiles != null) {
                // For each file within the user celestial body prediction folder
                for (File userPredictionFile : celestialBodyPredictionFiles) {
                    // Gather userIdentifier from the file path
                    String fileNameWithoutExtension = userPredictionFile.getName(); // Get the file name without path and extension
                    userIdentifier = fileNameWithoutExtension.replace("_celestial_body_predictions.json", "");
                    //System.out.println("UserInfo.User Identifier: " + userIdentifier);

                    // Load the file
                    try {
                        String json = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(userPredictionFile.getAbsolutePath())));
                        Gson gson = new Gson();
                        Type listType = new TypeToken<ArrayList<CelestialBodyPrediction>>() {}.getType();
                        loadedUserCelestialBodyPredictions = gson.fromJson(json, listType);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    // Initialize a new array list of celestial body prediction to store predictions that need to be removed
                    ArrayList<CelestialBodyPrediction> predictionsToRemove = new ArrayList<>();

                    // For each prediction within array list of CelestialBodyPrediction
                    for (CelestialBodyPrediction userCelestialBodyPrediction : loadedUserCelestialBodyPredictions) {
                        // Initialize remove prediction boolean flag
                        boolean removePrediction = false;

                        // Compare to each of the loaded CelestialBodies
                        for (CelestialBody celestialBody : loadedCelestialBodyArrayList) {
                            // If prediction end date == today's date && CelestialBody ID == updated CelestialBody ID && prediction knownCount == updated knownCount
                            if (DateTimeConverter.parseZonedDateTimeFromString(userCelestialBodyPrediction.getPrediction().getPredictionEndDate()).toLocalDate().toString().equals(ZonedDateTime.now().toLocalDate().toString()) && userCelestialBodyPrediction.getCelestialBody().getCelestialBodyType().equalsIgnoreCase(celestialBody.getCelestialBodyType()) && userCelestialBodyPrediction.getCelestialBody().getKnownCount() != celestialBody.getKnownCount()) {
                                // Initialize new resolved prediction
                                ResolvedPrediction resolvedCelestialBodyPrediction = new ResolvedPrediction();

                                // Copy over celestial body prediction values to the new resolved prediction
                                resolvedCelestialBodyPrediction.setPredictionType(userCelestialBodyPrediction.getPrediction().getPredictionType());
                                resolvedCelestialBodyPrediction.setPredictionContent(userCelestialBodyPrediction.getPrediction().getPredictionContent());
                                resolvedCelestialBodyPrediction.setPredictionMadeDate(userCelestialBodyPrediction.getPrediction().getPredictionMadeDate());
                                resolvedCelestialBodyPrediction.setPredictionEndDate(userCelestialBodyPrediction.getPrediction().getPredictionEndDate());
                                resolvedCelestialBodyPrediction.setResolution(false);

                                // Set the resolved date of the ResolvedPredictions.ResolvedPrediction
                                resolvedCelestialBodyPrediction.setResolvedDate(ZonedDateTime.now().toString());

                                // Initialize new array list of ResolvedPredictions.ResolvedPrediction to load into and save from
                                ArrayList<ResolvedPrediction> loadedResolvedPredictions = new ArrayList<>();

                                // Load the list of resolved predictions from the file
                                // Check if the file exists before attempting to load it
                                String resolvedFilePath = resolvedPredictionFolderPath + File.separator + userIdentifier + "_resolved_predictions.json";
                                File resolvedFile = new File(resolvedFilePath);
                                if (resolvedFile.exists()) {
                                    // Load the file
                                    try {
                                        String json = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(resolvedFilePath)));
                                        Gson gson = new Gson();
                                        Type listType = new TypeToken<ArrayList<ResolvedPrediction>>() {}.getType();
                                        loadedResolvedPredictions = gson.fromJson(json, listType);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }

                                // Add the new resolved prediction to the file
                                loadedResolvedPredictions.add(resolvedCelestialBodyPrediction);

                                // Save the resolved prediction to the user's resolved prediction list
                                try (FileWriter writer = new FileWriter(resolvedFilePath)) {
                                    Gson gson = new Gson();
                                    Type listType = new TypeToken<ArrayList<ResolvedPrediction>>() {}.getType();
                                    String json = gson.toJson(loadedResolvedPredictions, listType);
                                    writer.write(json);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                // Send notification to the user
                                NotificationUpdater.newCelestialBodyPredictionResolvedFalseNotification(userCelestialBodyPrediction, userIdentifier);

                                // Set remove prediction flag to true
                                removePrediction = true;
                            }

                            // If prediction CelestialBody ID == updated CelestialBody ID && knownCount != updated knownCount
                            if (userCelestialBodyPrediction.getCelestialBody().getCelestialBodyType() == celestialBody.getCelestialBodyType() && userCelestialBodyPrediction.getCelestialBody().getKnownCount() != celestialBody.getKnownCount()) {

                                // Initialize new resolved prediction
                                ResolvedPrediction resolvedCelestialBodyPrediction = new ResolvedPrediction();

                                // Copy over football CelestialBody prediction values to the new resolved prediction
                                resolvedCelestialBodyPrediction.setPredictionType(userCelestialBodyPrediction.getPrediction().getPredictionType());
                                resolvedCelestialBodyPrediction.setPredictionContent(userCelestialBodyPrediction.getPrediction().getPredictionContent());
                                resolvedCelestialBodyPrediction.setPredictionMadeDate(userCelestialBodyPrediction.getPrediction().getPredictionMadeDate());
                                resolvedCelestialBodyPrediction.setPredictionEndDate(userCelestialBodyPrediction.getPrediction().getPredictionEndDate());
                                resolvedCelestialBodyPrediction.setResolution(true);

                                // Set the resolved date of the ResolvedPredictions.ResolvedPrediction
                                resolvedCelestialBodyPrediction.setResolvedDate(ZonedDateTime.now().toString());

                                // Initialize new array list of ResolvedPredictions.ResolvedPrediction to load into and save from
                                ArrayList<ResolvedPrediction> loadedResolvedPredictions = new ArrayList<>();

                                // Load the list of resolved predictions from the file
                                // Check if the file exists before attempting to load it
                                String resolvedFilePath = resolvedPredictionFolderPath + File.separator + userIdentifier + "_resolved_predictions.json";
                                File resolvedFile = new File(resolvedFilePath);
                                if (resolvedFile.exists()) {
                                    // Load the file
                                    try {
                                        String json = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(resolvedFilePath)));
                                        Gson gson = new Gson();
                                        Type listType = new TypeToken<ArrayList<ResolvedPrediction>>() {}.getType();
                                        loadedResolvedPredictions = gson.fromJson(json, listType);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }

                                // Add the new resolved prediction to the file
                                loadedResolvedPredictions.add(resolvedCelestialBodyPrediction);

                                // Save the resolved prediction to the user's resolved prediction list
                                try (FileWriter writer = new FileWriter(resolvedFilePath)) {
                                    Gson gson = new Gson();
                                    Type listType = new TypeToken<ArrayList<ResolvedPrediction>>() {}.getType();
                                    String json = gson.toJson(loadedResolvedPredictions, listType);
                                    writer.write(json);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                // Send a notification to the user
                                NotificationUpdater.newCelestialBodyPredictionResolvedTrueNotification(userCelestialBodyPrediction, userIdentifier);

                                // Set removePrediction boolean flag to true
                                removePrediction = true;

                                // Calculate statistics of user and update UserStatistics, UserStatistics.UserInferentialStatistics, and OverallStatistics
                                UserDescriptiveStatisticsUpdater.calculateAndSaveUserDescriptiveStatistics(userIdentifier);
                                UserInferentialStatisticsUpdater.calculateAndSaveUserInferentialStatistics(userIdentifier);
                                OverallDescriptiveStatisticsUpdater.calculateAndSaveOverallDescriptiveStatistics();
                                OverallInferentialStatisticsUpdater.calculateAndSaveOverallInferentialStatistics();
                            }
                        }
                        // If removePrediction boolean flag is true
                        if (removePrediction) {
                            // Add the prediction to the array list of football CelestialBody predictions to remove
                            predictionsToRemove.add(userCelestialBodyPrediction);
                        }
                    }
                    // Remove the resolved and cancelled predictions
                    loadedUserCelestialBodyPredictions.removeAll(predictionsToRemove);

                    // Save the updated list to the user's prediction file
                    try (FileWriter writer = new FileWriter(userPredictionFile)) {
                        Gson gson = new Gson();
                        Type listType = new TypeToken<ArrayList<CelestialBodyPrediction>>() {}.getType();
                        String json = gson.toJson(loadedUserCelestialBodyPredictions, listType);
                        writer.write(json);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    //*****************************************************************************
    public static void updateCelestialBodyPredictionsMongoDB() {
        // Get the current date time using LocalDate.now()
        LocalDate currentDate = LocalDate.now();

        // Retrieve collection of EnvisionaryUsers
        ArrayList<EnvisionaryUser> envisionaryUsers = MongoDBEnvisionaryUsers.retrieveCollection();

        // Retrieve collection of celestial bodies
        ArrayList<CelestialBody> celestialBodies = MongoDBCelestialBodyData.retrieveCollection();

        // For each Envisionary UserInfo.User
        for (EnvisionaryUser user : envisionaryUsers) {
            // Initialize an array list of the user's celestial body predictions
            ArrayList<CelestialBodyPrediction> userCelestialBodyPredictions = user.getCelestialBodyPredictions();

            // Initialize a new array list of celestial body predictions to store predictions that need to be removed
            ArrayList<CelestialBodyPrediction> predictionsToRemove = new ArrayList<>();

            // For each prediction within array list of CelestialBodyPrediction
            for (CelestialBodyPrediction userCelestialBodyPrediction : userCelestialBodyPredictions) {
                // Initialize remove prediction boolean flag
                boolean removePrediction = false;

                // Compare to each of the loaded CelestialBodies
                for (CelestialBody celestialBody : celestialBodies) {
                    // If prediction end date == today's date && CelestialBody ID == updated CelestialBody ID && prediction knownCount == updated knownCount
                    if (userCelestialBodyPrediction.getPrediction().getPredictionEndDate().equals(currentDate.toString()) &&
                        userCelestialBodyPrediction.getCelestialBody().getCelestialBodyType().equalsIgnoreCase(celestialBody.getCelestialBodyType()) &&
                        userCelestialBodyPrediction.getCelestialBody().getKnownCount() != celestialBody.getKnownCount()) {

                        // Initialize new resolved prediction
                        ResolvedPrediction resolvedCelestialBodyPrediction = new ResolvedPrediction();

                        // Copy over celestial body prediction values to the new resolved prediction
                        resolvedCelestialBodyPrediction.setPredictionType(userCelestialBodyPrediction.getPrediction().getPredictionType());
                        resolvedCelestialBodyPrediction.setPredictionContent(userCelestialBodyPrediction.getPrediction().getPredictionContent());
                        resolvedCelestialBodyPrediction.setPredictionMadeDate(userCelestialBodyPrediction.getPrediction().getPredictionMadeDate());
                        resolvedCelestialBodyPrediction.setPredictionEndDate(userCelestialBodyPrediction.getPrediction().getPredictionEndDate());
                        resolvedCelestialBodyPrediction.setResolution(false);

                        // Set the resolved date of the ResolvedPredictions.ResolvedPrediction
                        resolvedCelestialBodyPrediction.setResolvedDate(ZonedDateTime.now().toString());

                        // Initialize new array list of ResolvedPredictions.ResolvedPrediction to update
                        ArrayList<ResolvedPrediction> userResolvedPredictions = user.getResolvedPredictions();

                        // Add the new resolved prediction to the list
                        userResolvedPredictions.add(resolvedCelestialBodyPrediction);

                        // Update the user's resolved predictions list
                        MongoDBEnvisionaryUsers.updateUserResolvedPredictions(user.getUserID(), userResolvedPredictions);

                        // Send notification to the user
                        NotificationUpdater.newCelestialBodyPredictionResolvedFalseNotificationMongoDB(userCelestialBodyPrediction, user.getUserID());

                        // Set remove prediction flag to true
                        removePrediction = true;

                        // Calculate statistics of user and update UserStatistics, UserStatistics.UserInferentialStatistics, and OverallStatistics
                        UserDescriptiveStatisticsUpdater.calculateAndSaveUserDescriptiveStatisticsMongoDB(user.getUserID());
                        UserInferentialStatisticsUpdater.calculateAndSaveUserInferentialStatisticsMongoDB(user.getUserID());
                        OverallDescriptiveStatisticsUpdater.calculateAndSaveOverallDescriptiveStatisticsMongoDB();
                        OverallInferentialStatisticsUpdater.calculateAndSaveOverallInferentialStatisticsMongoDB();
                    }

                    // If prediction CelestialBody ID == updated CelestialBody ID && knownCount != updated knownCount
                    if (userCelestialBodyPrediction.getCelestialBody().getCelestialBodyType().equalsIgnoreCase(celestialBody.getCelestialBodyType()) &&
                        userCelestialBodyPrediction.getCelestialBody().getKnownCount() != celestialBody.getKnownCount()) {

                        // Initialize new resolved prediction
                        ResolvedPrediction resolvedCelestialBodyPrediction = new ResolvedPrediction();

                        // Copy over football CelestialBody prediction values to the new resolved prediction
                        resolvedCelestialBodyPrediction.setPredictionType(userCelestialBodyPrediction.getPrediction().getPredictionType());
                        resolvedCelestialBodyPrediction.setPredictionContent(userCelestialBodyPrediction.getPrediction().getPredictionContent());
                        resolvedCelestialBodyPrediction.setPredictionMadeDate(userCelestialBodyPrediction.getPrediction().getPredictionMadeDate());
                        resolvedCelestialBodyPrediction.setPredictionEndDate(userCelestialBodyPrediction.getPrediction().getPredictionEndDate());
                        resolvedCelestialBodyPrediction.setResolution(true);

                        // Set the resolved date of the ResolvedPredictions.ResolvedPrediction
                        resolvedCelestialBodyPrediction.setResolvedDate(ZonedDateTime.now().toString());

                        // Initialize new array list of ResolvedPredictions.ResolvedPrediction to update
                        ArrayList<ResolvedPrediction> userResolvedPredictions = user.getResolvedPredictions();

                        // Add the new resolved prediction to the file
                        userResolvedPredictions.add(resolvedCelestialBodyPrediction);

                        // Save the resolved prediction to the user's resolved prediction list
                        MongoDBEnvisionaryUsers.updateUserResolvedPredictions(user.getUserID(), userResolvedPredictions);

                        // Send a notification to the user
                        NotificationUpdater.newCelestialBodyPredictionResolvedTrueNotificationMongoDB(userCelestialBodyPrediction, user.getUserID());

                        // Set removePrediction boolean flag to true
                        removePrediction = true;

                        // Calculate statistics of user and update UserStatistics, UserStatistics.UserInferentialStatistics, and OverallStatistics
                        UserDescriptiveStatisticsUpdater.calculateAndSaveUserDescriptiveStatisticsMongoDB(user.getUserID());
                        UserInferentialStatisticsUpdater.calculateAndSaveUserInferentialStatisticsMongoDB(user.getUserID());
                        OverallDescriptiveStatisticsUpdater.calculateAndSaveOverallDescriptiveStatisticsMongoDB();
                        OverallInferentialStatisticsUpdater.calculateAndSaveOverallInferentialStatisticsMongoDB();
                    }
                }
                // If removePrediction boolean flag is true
                if (removePrediction) {
                    // Add the prediction to the array list of football CelestialBody predictions to remove
                    predictionsToRemove.add(userCelestialBodyPrediction);
                }
            }
            // Remove the resolved and cancelled predictions
            userCelestialBodyPredictions.removeAll(predictionsToRemove);

            // Save the updated list to the user's prediction file
            MongoDBEnvisionaryUsers.updateUserCelestialBodyPredictions(user.getUserID(), userCelestialBodyPredictions);
        }
    }
}
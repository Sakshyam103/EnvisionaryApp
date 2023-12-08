package backend.Notifications;

import backend.CelestialBodyPredictions.CelestialBodyPrediction;
import backend.FootballMatchPredictions.FootballMatchPrediction;
import backend.UserInfo.MongoDBEnvisionaryUsers;
import backend.WeatherPredictions.WeatherPrediction;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

//■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
// Notifications.NotificationUpdater class - Written by Brandon LaPointe
//■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
// Initializes the file path and folders needed to save the user notification data. Allows for creation
// of new notifications, creation of new football match resolved notifications, creation of new football
// match cancelled notifications, display of a given userIdentifier's notifications, deletion of a given
// userIdentifier's selected notification, and deletion of all of a given userIdentifier's notifications.
//
public class NotificationUpdater {
    private static final String userHome = System.getProperty("user.home");
    private static final String notificationsFolderPath = userHome + File.separator + "EnvisionaryApp" + File.separator + "Notifications";


    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // initializeFilePath
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Initializes the file path and folders needed to save the notification files.
    //
    public static void initializeFilePath() {
        // Create the directory if it doesn't exist
        File directory = new File(notificationsFolderPath);
        if (!directory.exists()) {
            if (directory.mkdirs()) {
                System.out.println("Created directory: " + notificationsFolderPath);
            } else {
                System.err.println("ERROR - Failed to create directory: " + notificationsFolderPath);
            }
        }
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // newFootballMatchPredictionCancelledNotification
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Creates a new notification for a user's cancelled football match prediction when the predicted on
    // football match's status updates to either "CANCELLED", "SUSPENDED", or "POSTPONED".
    //
    public static void newFootballMatchPredictionCancelledNotification(FootballMatchPrediction matchPrediction, String userIdentifier) {
        // Initialize the file path
        String notificationsFilePath = notificationsFolderPath + File.separator + userIdentifier + "_notifications.json";
        File userNotificationsFile = new File(notificationsFilePath);

        // Initialize a new Notifications.Notification
        Notification notification = new Notification();

        // Set notification variables
        notification.setNotificationTitle("Football match prediction was removed due to match being cancelled, suspended, or postponed.");
        notification.setPredictionContent(matchPrediction.getPrediction().getPredictionContent());
        notification.setPredictionMadeDate(matchPrediction.getPrediction().getPredictionMadeDate());
        notification.setPredictionEndDate(matchPrediction.getPrediction().getPredictionEndDate());

        // Initialize a new array list of Notifications.Notification objects
        ArrayList<Notification> userNotifications = new ArrayList<>();

        // Load user's notification file if it exists
        if(userNotificationsFile.exists()) {
            try {
                String json = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(notificationsFilePath)));
                Gson gson = new Gson();
                Type listType = new TypeToken<ArrayList<Notification>>() {}.getType();
                userNotifications = gson.fromJson(json, listType);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Add notification to list of notifications
        userNotifications.add(notification);

        // Save notification to user's notification file
        try (FileWriter writer = new FileWriter(notificationsFilePath)) {
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<Notification>>() {}.getType();
            String json = gson.toJson(userNotifications, listType);
            writer.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // newFootballMatchPredictionResolvedNotification
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Creates a new notification for a user's resolved football match prediction when the predicted on
    // football match's status updates to "FINISHED" and the football match prediction is resolved
    // automatically using the FootballMatchPredictions.FootballMatchPredictionUpdater class's watchdog pattern updater.
    //
    public static void newFootballMatchPredictionResolvedNotification(FootballMatchPrediction matchPrediction, String userIdentifier, Boolean outcome) {
        // Initialize the file path
        String notificationsFilePath = notificationsFolderPath + File.separator + userIdentifier + "_notifications.json";
        File userNotificationsFile = new File(notificationsFilePath);

        // Initialize a new Notifications.Notification
        Notification notification = new Notification();

        // Set notification variables
        notification.setNotificationTitle("Football match prediction was successfully resolved: " + outcome);
        notification.setPredictionContent(matchPrediction.getPrediction().getPredictionContent());
        notification.setPredictionMadeDate(matchPrediction.getPrediction().getPredictionMadeDate());
        notification.setPredictionEndDate(matchPrediction.getPrediction().getPredictionEndDate());

        // Initialize a new array list of Notifications.Notification objects
        ArrayList<Notification> userNotifications = new ArrayList<>();

        // Load user's notification file if it exists
        if(userNotificationsFile.exists()) {
            try {
                String json = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(notificationsFilePath)));
                Gson gson = new Gson();
                Type listType = new TypeToken<ArrayList<Notification>>() {}.getType();
                userNotifications = gson.fromJson(json, listType);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Add notification to list of notifications
        userNotifications.add(notification);

        // Save notification to user's notification file
        try (FileWriter writer = new FileWriter(notificationsFilePath)) {
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<Notification>>() {}.getType();
            String json = gson.toJson(userNotifications, listType);
            writer.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // newFootballMatchPredictionCancelledNotificationMongoDB
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Creates a new notification for a user's cancelled football match prediction when the predicted on
    // football match's status updates to either "CANCELLED", "SUSPENDED", or "POSTPONED".
    //
    public static void newFootballMatchPredictionCancelledNotificationMongoDB(FootballMatchPrediction matchPrediction, String userIdentifier) {
        // Initialize a new Notifications.Notification
        Notification notification = new Notification();

        // Set notification variables
        notification.setNotificationTitle("Football match prediction was removed due to match being cancelled, suspended, or postponed.");
        notification.setPredictionContent(matchPrediction.getPrediction().getPredictionContent());
        notification.setPredictionMadeDate(matchPrediction.getPrediction().getPredictionMadeDate());
        notification.setPredictionEndDate(matchPrediction.getPrediction().getPredictionEndDate());

        // Initialize a new array list of Notifications.Notification objects
        ArrayList<Notification> userNotifications = MongoDBEnvisionaryUsers.retrieveUserNotifications(userIdentifier);

        // Add notification to list of notifications
        userNotifications.add(notification);

        // Save notification to user's notification file
        MongoDBEnvisionaryUsers.updateUserNotifications(userIdentifier, userNotifications);

        // Send email to the user
        Mailing.sendMail(notification.getNotificationTitle() + ":\n" + notification.getPredictionContent(), MongoDBEnvisionaryUsers.retrieveUserEmail(userIdentifier));
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // newFootballMatchPredictionResolvedNotificationMongoDB
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Creates a new notification for a user's resolved football match prediction when the predicted on
    // football match's status updates to "FINISHED" and the football match prediction is resolved
    // automatically using the FootballMatchPredictions.FootballMatchPredictionUpdater class's watchdog pattern updater.
    //
    public static void newFootballMatchPredictionResolvedNotificationMongoDB(FootballMatchPrediction matchPrediction, String userIdentifier, Boolean outcome) {
        // Initialize a new Notifications.Notification
        Notification notification = new Notification();

        // Set notification variables
        notification.setNotificationTitle("Football match prediction was successfully resolved: " + outcome);
        notification.setPredictionContent(matchPrediction.getPrediction().getPredictionContent());
        notification.setPredictionMadeDate(matchPrediction.getPrediction().getPredictionMadeDate());
        notification.setPredictionEndDate(matchPrediction.getPrediction().getPredictionEndDate());

        // Initialize a new array list of Notifications.Notification objects
        ArrayList<Notification> userNotifications = MongoDBEnvisionaryUsers.retrieveUserNotifications(userIdentifier);

        if(userNotifications == null) {
            userNotifications = new ArrayList<>();
        }

        // Add notification to list of notifications
        userNotifications.add(notification);

        // Save notification to user's notification file
        MongoDBEnvisionaryUsers.updateUserNotifications(userIdentifier, userNotifications);

        // Send email to the user
        Mailing.sendMail(notification.getNotificationTitle() + ":\n" + notification.getPredictionContent(), MongoDBEnvisionaryUsers.retrieveUserEmail(userIdentifier));
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // newCelestialBodyPredictionResolvedFalseNotification
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Creates a new notification for a user's resolved celestial body prediction when the predicted on
    // celestial body's knownCount has not changed since the prediction was made and the end date is equal
    // to today's date, resolving the CelestialBodyPredictions.CelestialBodyPrediction to false.
    //
    public static void newCelestialBodyPredictionResolvedFalseNotification(CelestialBodyPrediction userCelestialBodyPrediction, String userIdentifier) {
        // Initialize the file path
        String notificationsFilePath = notificationsFolderPath + File.separator + userIdentifier + "_notifications.json";
        File userNotificationsFile = new File(notificationsFilePath);

        // Set outcome to false
        boolean outcome = false;

        // Initialize a new Notifications.Notification
        Notification notification = new Notification();

        // Set notification variables
        notification.setNotificationTitle("Football match prediction was successfully resolved: " + outcome);
        notification.setPredictionContent(userCelestialBodyPrediction.getPrediction().getPredictionContent());
        notification.setPredictionMadeDate(userCelestialBodyPrediction.getPrediction().getPredictionMadeDate());
        notification.setPredictionEndDate(userCelestialBodyPrediction.getPrediction().getPredictionEndDate());

        // Initialize a new array list of Notifications.Notification objects
        ArrayList<Notification> userNotifications = new ArrayList<>();

        if(userNotifications == null) {
            userNotifications = new ArrayList<>();
        }

        // Load user's notification file if it exists
        if(userNotificationsFile.exists()) {
            try {
                String json = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(notificationsFilePath)));
                Gson gson = new Gson();
                Type listType = new TypeToken<ArrayList<Notification>>() {}.getType();
                userNotifications = gson.fromJson(json, listType);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Add notification to list of notifications
        userNotifications.add(notification);

        // Save notification to user's notification file
        try (FileWriter writer = new FileWriter(notificationsFilePath)) {
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<Notification>>() {}.getType();
            String json = gson.toJson(userNotifications, listType);
            writer.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // newCelestialBodyPredictionResolvedTrueNotification
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Creates a new notification for a user's resolved celestial body prediction when the predicted on
    // celestial body's knownCount has changed since the prediction was made, resolving the
    // CelestialBodyPredictions.CelestialBodyPrediction to true.
    //
    public static void newCelestialBodyPredictionResolvedTrueNotification(CelestialBodyPrediction userCelestialBodyPrediction, String userIdentifier) {
        // Initialize the file path
        String notificationsFilePath = notificationsFolderPath + File.separator + userIdentifier + "_notifications.json";
        File userNotificationsFile = new File(notificationsFilePath);

        // Set outcome to false
        boolean outcome = true;

        // Initialize a new Notifications.Notification
        Notification notification = new Notification();

        // Set notification variables
        notification.setNotificationTitle("Celestial body prediction was successfully resolved: " + outcome);
        notification.setPredictionContent(userCelestialBodyPrediction.getPrediction().getPredictionContent());
        notification.setPredictionMadeDate(userCelestialBodyPrediction.getPrediction().getPredictionMadeDate());
        notification.setPredictionEndDate(userCelestialBodyPrediction.getPrediction().getPredictionEndDate());

        // Initialize a new array list of Notifications.Notification objects
        ArrayList<Notification> userNotifications = new ArrayList<>();

        // Load user's notification file if it exists
        if(userNotificationsFile.exists()) {
            try {
                String json = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(notificationsFilePath)));
                Gson gson = new Gson();
                Type listType = new TypeToken<ArrayList<Notification>>() {}.getType();
                userNotifications = gson.fromJson(json, listType);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Add notification to list of notifications
        userNotifications.add(notification);

        // Save notification to user's notification file
        try (FileWriter writer = new FileWriter(notificationsFilePath)) {
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<Notification>>() {}.getType();
            String json = gson.toJson(userNotifications, listType);
            writer.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // newCelestialBodyPredictionResolvedFalseNotificationMongoDB
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Creates a new notification for a user's resolved celestial body prediction when the predicted on
    // celestial body's knownCount has not changed since the prediction was made and the end date is equal
    // to today's date, resolving the CelestialBodyPredictions.CelestialBodyPrediction to false.
    //
    public static void newCelestialBodyPredictionResolvedFalseNotificationMongoDB(CelestialBodyPrediction userCelestialBodyPrediction, String userIdentifier) {
        // Set outcome to false
        boolean outcome = false;

        // Initialize a new Notifications.Notification
        Notification notification = new Notification();

        // Set notification variables
        notification.setNotificationTitle("Celestial body prediction was successfully resolved: " + outcome);
        notification.setPredictionContent(userCelestialBodyPrediction.getPrediction().getPredictionContent());
        notification.setPredictionMadeDate(userCelestialBodyPrediction.getPrediction().getPredictionMadeDate());
        notification.setPredictionEndDate(userCelestialBodyPrediction.getPrediction().getPredictionEndDate());

        // Initialize a new array list of Notifications.Notification objects
        ArrayList<Notification> userNotifications = MongoDBEnvisionaryUsers.retrieveUserNotifications(userIdentifier);

        if(userNotifications == null) {
            userNotifications = new ArrayList<>();
        }

        // Add notification to list of notifications
        userNotifications.add(notification);

        // Save notification to user's notification file
        MongoDBEnvisionaryUsers.updateUserNotifications(userIdentifier, userNotifications);

        // Send email to the user
        Mailing.sendMail(notification.getNotificationTitle() + ":\n" + notification.getPredictionContent(), MongoDBEnvisionaryUsers.retrieveUserEmail(userIdentifier));
    }


    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // newCelestialBodyPredictionResolvedTrueNotificationMongoDB
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Creates a new notification for a user's resolved celestial body prediction when the predicted on
    // celestial body's knownCount has changed since the prediction was made, resolving the
    // CelestialBodyPredictions.CelestialBodyPrediction to true.
    //
    public static void newCelestialBodyPredictionResolvedTrueNotificationMongoDB(CelestialBodyPrediction userCelestialBodyPrediction, String userIdentifier) {
        // Set outcome to false
        boolean outcome = true;

        // Initialize a new Notifications.Notification
        Notification notification = new Notification();

        // Set notification variables
        notification.setNotificationTitle("Celestial body prediction was successfully resolved: " + outcome);
        notification.setPredictionContent(userCelestialBodyPrediction.getPrediction().getPredictionContent());
        notification.setPredictionMadeDate(userCelestialBodyPrediction.getPrediction().getPredictionMadeDate());
        notification.setPredictionEndDate(userCelestialBodyPrediction.getPrediction().getPredictionEndDate());

        // Initialize a new array list of Notifications.Notification objects
        ArrayList<Notification> userNotifications = MongoDBEnvisionaryUsers.retrieveUserNotifications(userIdentifier);

        if(userNotifications == null) {
            userNotifications = new ArrayList<>();
        }

        // Add notification to list of notifications
        userNotifications.add(notification);

        // Save notification to user's notification file
        MongoDBEnvisionaryUsers.updateUserNotifications(userIdentifier, userNotifications);

        // Send email to the user
        Mailing.sendMail(notification.getNotificationTitle() + ":\n" + notification.getPredictionContent(), MongoDBEnvisionaryUsers.retrieveUserEmail(userIdentifier));
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // newWeatherPredictionResolvedFalseNotificationMongoDB
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    public static void newWeatherPredictionResolvedFalseNotificationMongoDB(WeatherPrediction userWeatherPrediction, String userIdentifier) {
        // Set outcome to false
        boolean outcome = false;

        // Initialize a new Notifications.Notification
        Notification notification = new Notification();

        // Set notification variables
        notification.setNotificationTitle("Weather prediction was successfully resolved: " + outcome);
        notification.setPredictionContent(userWeatherPrediction.getPrediction().getPredictionContent());
        notification.setPredictionMadeDate(userWeatherPrediction.getPrediction().getPredictionMadeDate());
        notification.setPredictionEndDate(userWeatherPrediction.getPrediction().getPredictionEndDate());

        // Initialize a new array list of Notifications.Notification objects
        ArrayList<Notification> userNotifications = MongoDBEnvisionaryUsers.retrieveUserNotifications(userIdentifier);

        if(userNotifications == null) {
            userNotifications = new ArrayList<>();
        }

        // Add notification to list of notifications
        userNotifications.add(notification);

        // Save notification to user's notification file
        MongoDBEnvisionaryUsers.updateUserNotifications(userIdentifier, userNotifications);

        // Send email to the user
        Mailing.sendMail(notification.getNotificationTitle() + ":\n" + notification.getPredictionContent(), MongoDBEnvisionaryUsers.retrieveUserEmail(userIdentifier));
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // newWeatherPredictionResolvedTrueNotificationMongoDB
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    public static void newWeatherPredictionResolvedTrueNotificationMongoDB(WeatherPrediction userWeatherPrediction, String userIdentifier) {
        // Set outcome to false
        boolean outcome = true;

        // Initialize a new Notifications.Notification
        Notification notification = new Notification();

        // Set notification variables
        notification.setNotificationTitle("Weather prediction was successfully resolved: " + outcome);
        notification.setPredictionContent(userWeatherPrediction.getPrediction().getPredictionContent());
        notification.setPredictionMadeDate(userWeatherPrediction.getPrediction().getPredictionMadeDate());
        notification.setPredictionEndDate(userWeatherPrediction.getPrediction().getPredictionEndDate());

        // Initialize a new array list of Notifications.Notification objects
        ArrayList<Notification> userNotifications = MongoDBEnvisionaryUsers.retrieveUserNotifications(userIdentifier);

        if(userNotifications == null) {
            userNotifications = new ArrayList<>();
        }

        // Add notification to list of notifications
        userNotifications.add(notification);

        // Save notification to user's notification file
        MongoDBEnvisionaryUsers.updateUserNotifications(userIdentifier, userNotifications);

        // Send email to the user
        Mailing.sendMail(notification.getNotificationTitle() + ":\n" + notification.getPredictionContent(), MongoDBEnvisionaryUsers.retrieveUserEmail(userIdentifier));
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // loadAndDisplayUserNotifications
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Loads a given userIdentifier's notification data file and displays the notifications to the console.
    //
    public static void loadAndDisplayUserNotifications(String userIdentifier) {
        // Initialize the file path
        String notificationsFilePath = notificationsFolderPath + File.separator + userIdentifier + "_notifications.json";
        File userNotificationsFile = new File(notificationsFilePath);

        // Initialize a new array list of Notifications.Notification objects
        ArrayList<Notification> userNotifications = new ArrayList<>();

        // Load user's notification file if it exists
        if (userNotificationsFile.exists()) {
            try {
                String json = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(notificationsFilePath)));
                Gson gson = new Gson();
                Type listType = new TypeToken<ArrayList<Notification>>() {}.getType();
                userNotifications = gson.fromJson(json, listType);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (userNotifications.isEmpty()) {
                System.out.println("ERROR - Notifications file is empty for user: " + userIdentifier + "\n");
                return; // Exit the function
            }
            else {
                System.out.println("\n====================================================================================================");
                System.out.println("                                 " + userIdentifier + "'s Notifications");
                for (Notification userNotification : userNotifications) {
                    System.out.println("\nTitle: " + userNotification.getNotificationTitle() + "\n" +
                            "Context: " + userNotification.getPredictionContent() + "\n" +
                            "Made Date: " + userNotification.getPredictionMadeDate() + "\n" +
                            "End Date: " + userNotification.getPredictionEndDate() + "\n");
                }
                System.out.println();
            }
        } else {
            System.out.println("ERROR - Notifications file does not exist for user: " + userIdentifier + "\n");
        }
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // removeUserNotification
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Loads a given userIdentifier's notification data file and displays the notifications to the console
    // with indexes starting at 1 and asks the user to input the index of the notification that they want
    // to select for removal before confirming that the selection is correct and if Y, removing the
    // notification from the ArrayList and saving the updated list to the user's notification file.
    //
    public static void removeUserNotification(String userIdentifier) {
        Scanner scan = new Scanner(System.in);

        // Initialize new array list of Notifications.Notification to load into and save from
        ArrayList<Notification> userNotifications = new ArrayList<>();

        // Load the list of user predictions from the file
        String userNotificationsFilePath = notificationsFolderPath + File.separator + userIdentifier + "_notifications.json";
        File userNotificationsFile = new File(userNotificationsFilePath);

        if (userNotificationsFile.exists()) {
            try {
                String json = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(userNotificationsFilePath)));
                Gson gson = new Gson();
                Type listType = new TypeToken<ArrayList<Notification>>() {}.getType();
                userNotifications = gson.fromJson(json, listType);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (userNotifications.isEmpty()) {
            System.out.println("ERROR - Notifications file does not exist for user: " + userIdentifier + "\n");
            return; // Exit the function
        }

        // Display the list of notifications for the user to select from
        System.out.println("Select a notification to remove from the list of your notifications:");
        int notificationIndex = 1;
        for (Notification userNotification : userNotifications) {
            System.out.println(notificationIndex + " : " + userNotification.getNotificationTitle() + " : " + userNotification.getPredictionContent());
            ++notificationIndex;
        }
        int notificationRemovalSelection = -1; // Initialize the selection variable

        // Get user input from the list of predictions to remove with input validation
        boolean validInput = false;
        while (!validInput) {
            try {
                System.out.print("Enter the number of the notification to remove: ");
                notificationRemovalSelection = scan.nextInt();
                validInput = true; // The input is valid if no exception is thrown
            } catch (InputMismatchException e) {
                System.out.println("ERROR - Invalid input. Please enter a valid number.");
                scan.nextLine(); // Consume the invalid input
            }
        }

        if (notificationRemovalSelection >= 1 && notificationRemovalSelection <= userNotifications.size()) {
            // Ask if the user is sure about the choice
            System.out.print("Are you sure you want to remove this notification? (Y/N):\n" + userNotifications.get(notificationRemovalSelection - 1).getNotificationTitle() + " : " + userNotifications.get(notificationRemovalSelection - 1).getPredictionContent() + "\n");
            String confirmation = scan.next();

            if (confirmation.equalsIgnoreCase("Y")) {
                // Remove the prediction from the list
                userNotifications.remove(notificationRemovalSelection - 1);

                // Save over the old user prediction file
                try (FileWriter writer = new FileWriter(userNotificationsFilePath)) {
                    Gson gson = new Gson();
                    Type listType = new TypeToken<ArrayList<Notification>>() {}.getType();
                    String json = gson.toJson(userNotifications, listType);
                    writer.write(json);
                    System.out.println("All notifications saved successfully.");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Notifications.Notification removal canceled.");
            }
        } else {
            System.out.println("ERROR - Invalid notification number.\n");
        }
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // removeAllUserNotifications
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Asks the user to confirm that they want to remove all of their notifications and if Y, initializes
    // a new empty ArrayList of Notifications.Notification and saves the empty list to the user's notification file.
    //
    public static void removeAllUserNotifications(String userIdentifier) {
        Scanner scan = new Scanner(System.in);

        // Initialize the file path
        String userNotificationsFilePath = notificationsFolderPath + File.separator + userIdentifier + "_notifications.json";
        File userNotificationsFile = new File(userNotificationsFilePath);

        if (userNotificationsFile.exists()) {
            ArrayList<Notification> loadedNotificationsArrayList = new ArrayList<>();
            // Load the file
            try {
                String json = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(userNotificationsFilePath)));
                Gson gson = new Gson();
                Type listType = new TypeToken<ArrayList<Notification>>() {}.getType();
                loadedNotificationsArrayList = gson.fromJson(json, listType);
            } catch (IOException e) {
                e.printStackTrace();
            }
            // Check if the file is empty
            if (loadedNotificationsArrayList.isEmpty()) {
                System.out.println("ERROR - Notifications file does not exist for user: " + userIdentifier + "\n");
                return; // Exit the function
            }

            for (Notification notification : loadedNotificationsArrayList) {
                notification.printNotification();
                System.out.println();
            }

            // Initialize userInput to null
            String userInput = null;

            // Ask user if they are sure they want to remove all notifications
            System.out.println("Are you sure you want to remove all notifications? (Y/N)");

            // Get user input
            userInput = scan.next();

            // If user input equals Y or y, remove all notifications
            if (userInput.equalsIgnoreCase("Y")) {
                // Initialize new array list of notification objects
                ArrayList<Notification> emptyNotificationsArrayList = new ArrayList<>();

                // Save empty array list over user's notification file
                try (FileWriter writer = new FileWriter(userNotificationsFilePath)) {
                    Gson gson = new Gson();
                    Type listType = new TypeToken<ArrayList<Notification>>() {}.getType();
                    String json = gson.toJson(emptyNotificationsArrayList, listType);
                    writer.write(json);
                    System.out.println("All notifications removed successfully.");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else {
                System.out.println("Notifications.Notification removal canceled.");
            }
        }
        else {
            System.out.println("ERROR - Notifications file does not exist for user: " + userIdentifier + "\n");
        }
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // removeUserNotificationMongoDB
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Loads a given userIdentifier's notification data file and displays the notifications to the console
    // with indexes starting at 1 and asks the user to input the index of the notification that they want
    // to select for removal before confirming that the selection is correct and if Y, removing the
    // notification from the ArrayList and saving the updated list to MongoDB.
    //
    public static void removeUserNotificationMongoDB(String userIdentifier) {
        Scanner scan = new Scanner(System.in);

        // Initialize new array list of Notifications.Notification to load into and save from
        ArrayList<Notification> userNotifications = MongoDBEnvisionaryUsers.retrieveUserNotifications(userIdentifier);

        if (userNotifications == null || userNotifications.isEmpty()) {
            System.out.println("ERROR - Notifications file does not exist for user: " + userIdentifier + "\n");
            return; // Exit the function
        }

        // Display the list of notifications for the user to select from
        System.out.println("Select a notification to remove from the list of your notifications:");
        int notificationIndex = 1;
        for (Notification userNotification : userNotifications) {
            System.out.println(notificationIndex + " : " + userNotification.getNotificationTitle() + " : " + userNotification.getPredictionContent());
            ++notificationIndex;
        }
        int notificationRemovalSelection = -1; // Initialize the selection variable

        // Get user input from the list of predictions to remove with input validation
        boolean validInput = false;
        while (!validInput) {
            try {
                System.out.print("Enter the number of the notification to remove: ");
                notificationRemovalSelection = scan.nextInt();
                validInput = true; // The input is valid if no exception is thrown
            } catch (InputMismatchException e) {
                System.out.println("ERROR - Invalid input. Please enter a valid number.");
                scan.nextLine(); // Consume the invalid input
            }
        }

        if (notificationRemovalSelection >= 1 && notificationRemovalSelection <= userNotifications.size()) {
            // Ask if the user is sure about the choice
            System.out.print("Are you sure you want to remove this notification? (Y/N):\n" + userNotifications.get(notificationRemovalSelection - 1).getNotificationTitle() + " : " + userNotifications.get(notificationRemovalSelection - 1).getPredictionContent() + "\n");
            String confirmation = scan.next();

            if (confirmation.equalsIgnoreCase("Y")) {
                // Remove the prediction from the list
                userNotifications.remove(notificationRemovalSelection - 1);

                // Save over the old user prediction file
                MongoDBEnvisionaryUsers.updateUserNotifications(userIdentifier, userNotifications);
            } else {
                System.out.println("Notifications.Notification removal canceled.");
            }
        } else {
            System.out.println("ERROR - Invalid notification number.\n");
        }
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // removeAllUserNotificationsMongoDB
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Asks the user to confirm that they want to remove all of their notifications and if Y, initializes
    // a new empty ArrayList of Notifications.Notification and saves the empty list to MongoDB.
    //
    public static void removeAllUserNotificationsMongoDB(String userIdentifier) {
        Scanner scan = new Scanner(System.in);

        // Initialize the file path
        ArrayList<Notification> loadedNotificationsArrayList = MongoDBEnvisionaryUsers.retrieveUserNotifications(userIdentifier);

        // Check if the file is empty
        if (loadedNotificationsArrayList == null) {
            System.out.println("ERROR - Notifications file does not exist for user: " + userIdentifier + "\n");
            return; // Exit the function
        }

        for (Notification notification : loadedNotificationsArrayList) {
            notification.printNotification();
            System.out.println();
        }

        // Initialize userInput to null
        String userInput = null;

        // Ask user if they are sure they want to remove all notifications
        System.out.println("Are you sure you want to remove all notifications? (Y/N)");

        // Get user input
        userInput = scan.next();

        // If user input equals Y or y, remove all notifications
        if (userInput.equalsIgnoreCase("Y")) {
            ArrayList<Notification> emptyNotificationsArrayList = new ArrayList<>();
            MongoDBEnvisionaryUsers.updateUserNotifications(userIdentifier, emptyNotificationsArrayList);
        } else {
            System.out.println("Notifications.Notification removal canceled.");
        }
    }
}
package backend.UserInfo;

import backend.CelestialBodyPredictions.CelestialBodyPrediction;
import backend.CustomPredictions.CustomPrediction;
import backend.FootballMatchPredictions.FootballMatchPrediction;
//import backend.WeatherPredictions.WeatherPrediction;
import backend.Notifications.Notification;
import backend.ResolvedPredictions.ResolvedPrediction;
import backend.UserStatistics.UserDescriptiveStatistics;
import backend.UserStatistics.UserInferentialStatistics;
import backend.WeatherPredictions.WeatherPrediction;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

//■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
// UserInfo.EnvisionaryUser object class - Idea by Kritika Parajuli - Written by Brandon LaPointe
//■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
// Represents a single user within the Envisionary web app.
// Alternate idea drawn out by Kritika to more easily access users' predictions, statistics, and notifications.
// I think this would be a good idea as we would only need a single collection within the database,
//
public class EnvisionaryUser {
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // UserInfo.EnvisionaryUser object variables
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    @SerializedName("userID")
    private String userID;                                                  // User identifier
    @SerializedName("email")
    private String email;			                                        // User's email address
    @SerializedName("customPredictions")
    private ArrayList<CustomPrediction> customPredictions;		            // List of user's custom predictions
//    @SerializedName("entertainmentPredictions")
//    private ArrayList<EntertainmentPrediction> entertainmentPredictions;    // List of user's entertainment predictions
    @SerializedName("footballMatchPredictions")
    private ArrayList<FootballMatchPrediction> footballMatchPredictions;	// List of user's football match predictions (Sports)
    @SerializedName("celestialBodyPredictions")
    private ArrayList<CelestialBodyPrediction> celestialBodyPredictions;    // List of user's celestial body predictions (Science)
    @SerializedName("weatherPredictions")
    private ArrayList<WeatherPrediction> weatherPredictions;		        // List of user's weather predictions
    @SerializedName("resolvedPredictions")
    private ArrayList<ResolvedPrediction> resolvedPredictions;		        // List of user's resolved predictions
    @SerializedName("notifications")
    private ArrayList<Notification> notifications;	                        // List of user's notifications
    @SerializedName("userDescriptiveStatistics")
    private UserDescriptiveStatistics userDescriptiveStatistics;	        // UserInfo.User's descriptive statistics
    @SerializedName("userInferentialStatistics")
    private UserInferentialStatistics userInferentialStatistics;	        // UserInfo.User's inferential statistics

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Getter and setter methods for the UserInfo.User object variables
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    public String getUserID() {
        return userID;
    }
    public void setUserID(String userID) {
        this.userID = userID;
    }
//    public String getPassword() {
//        return password;
//    }
//    public void setPassword(String password) {
//        this.password = password;
//    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public ArrayList<CustomPrediction> getCustomPredictions() {
        return customPredictions;
    }
    public void setCustomPredictions(ArrayList<CustomPrediction> customPredictions) {
        this.customPredictions = customPredictions;
    }
    //    public ArrayList<EntertainmentPrediction> getEntertainmentPredictions() {
//        return entertainmentPredictions;
//    }
//    public void setEntertainmentPredictions(ArrayList<EntertainmentPrediction> entertainmentPredictions) {
//        this.entertainmentPredictions = entertainmentPredictions;
//    }
    public ArrayList<FootballMatchPrediction> getFootballMatchPredictions() {
        return footballMatchPredictions;
    }
    public void setFootballMatchPredictions(ArrayList<FootballMatchPrediction> footballMatchPredictions) {
        this.footballMatchPredictions = footballMatchPredictions;
    }
    public ArrayList<CelestialBodyPrediction> getCelestialBodyPredictions() {
        return celestialBodyPredictions;
    }
    public void setCelestialBodyPredictions(ArrayList<CelestialBodyPrediction> celestialBodyPredictions) {
        this.celestialBodyPredictions = celestialBodyPredictions;
    }
    public ArrayList<WeatherPrediction> getWeatherPredictions() {
        return weatherPredictions;
    }
    public void setWeatherPredictions(ArrayList<WeatherPrediction> weatherPredictions) {
        this.weatherPredictions = weatherPredictions;
    }
    public ArrayList<ResolvedPrediction> getResolvedPredictions() {
        return resolvedPredictions;
    }
    public void setResolvedPredictions(ArrayList<ResolvedPrediction> resolvedPredictions) {
        this.resolvedPredictions = resolvedPredictions;
    }
    public ArrayList<Notification> getNotifications() {
        return notifications;
    }
    public void setNotifications(ArrayList<Notification> notifications) {
        this.notifications = notifications;
    }
    public UserDescriptiveStatistics getUserDescriptiveStatistics() {
        return userDescriptiveStatistics;
    }
    public void setUserDescriptiveStatistics(UserDescriptiveStatistics userDescriptiveStatistics) {
        this.userDescriptiveStatistics = userDescriptiveStatistics;
    }
    public UserInferentialStatistics getUserInferentialStatistics() {
        return userInferentialStatistics;
    }
    public void setUserInferentialStatistics(UserInferentialStatistics userInferentialStatistics) {
        this.userInferentialStatistics = userInferentialStatistics;
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Default Empty Constructor
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    public EnvisionaryUser() {}

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Constructor with Base Parameters
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    public EnvisionaryUser(String userID, String email) {
        this.userID = userID;
        this.email = email;
        ArrayList<CustomPrediction> emptyCustomPredictions = new ArrayList<>();
        this.customPredictions = emptyCustomPredictions;
//        ArrayList<EntertainmentPrediction> emptyEntertainmentPredictions = new ArrayList<>();
//        this.entertainmentPredictions = emptyEntertainmentPredictions;
        ArrayList<FootballMatchPrediction> emptyFootballMatchPredictions = new ArrayList<>();
        this.footballMatchPredictions = emptyFootballMatchPredictions;
        ArrayList<CelestialBodyPrediction> emptyCelestialBodyPredictions = new ArrayList<>();
        this.celestialBodyPredictions = emptyCelestialBodyPredictions;
        ArrayList<WeatherPrediction> emptyWeatherPredictions = new ArrayList<>();
        this.weatherPredictions = emptyWeatherPredictions;
        ArrayList<ResolvedPrediction> emptyResolvedPredictions = new ArrayList<>();
        this.resolvedPredictions = emptyResolvedPredictions;
        ArrayList<Notification> emptyNotifications = new ArrayList<>();
        this.notifications = emptyNotifications;
        this.userDescriptiveStatistics = new UserDescriptiveStatistics();
        this.userInferentialStatistics = new UserInferentialStatistics();
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Constructor with Parameters
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    public EnvisionaryUser(String userID, String email,
                           ArrayList<CustomPrediction> customPredictions,
//                           ArrayList<EntertainmentPrediction> entertainmentPredictions,
                           ArrayList<FootballMatchPrediction> footballMatchPredictions,
                           ArrayList<CelestialBodyPrediction> celestialBodyPredictions,
                           ArrayList<WeatherPrediction> weatherPredictions,
                           UserDescriptiveStatistics userDescriptiveStatistics,
                           UserInferentialStatistics userInferentialStatistics,
                           ArrayList<Notification> notifications) {
        this.userID = userID;
        this.email = email;
        this.customPredictions = customPredictions;
//        this.entertainmentPredictions = entertainmentPredictions;
        this.footballMatchPredictions = footballMatchPredictions;
        this.celestialBodyPredictions = celestialBodyPredictions;
        this.weatherPredictions = weatherPredictions;
        this.userDescriptiveStatistics = userDescriptiveStatistics;
        this.userInferentialStatistics = userInferentialStatistics;
        this.notifications = notifications;
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // printUserDetails
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    public void printUserDetails() {
        System.out.println("UserInfo.User ID: " + userID + "\n" +
                "Email: " + email + "\n" +
                "Number of Custom Predictions: " + customPredictions.size() + "\n" +
//                "Number of Entertainment Predictions: " + entertainmentPredictions.size() + "\n" +
                "Number of Football Match Predictions: " + footballMatchPredictions.size() + "\n" +
                "Number of Celestial Body Predictions: " + celestialBodyPredictions.size() + "\n" +
                "Number of Weather Predictions: " + weatherPredictions.size() + "\n" +
                "UserInfo.User Descriptive Statistics: " + "\n" +
                "   BasePredictionsObject.Prediction Count: " + userDescriptiveStatistics.getPredictionCount() + "\n" +
                "   Number of Correct Predictions: " + userDescriptiveStatistics.getCorrectPredictions() + "\n" +
                "   Number of Incorrect Predictions: " + userDescriptiveStatistics.getIncorrectPredictions() + "\n" +
                "   Percent of Correct Predictions: " + userDescriptiveStatistics.getPercentCorrect() + "%\n" +
                "   Percent of Incorrect Predictions: " + userDescriptiveStatistics.getPercentIncorrect() + "%\n" +
                "UserInfo.User Inferential Statistics: " + "\n" +
                "   Best BasePredictionsObject.Prediction Category: " + userInferentialStatistics.getMostCorrectPredictionType() + "\n" +
                "   Worst BasePredictionsObject.Prediction Category: " + userInferentialStatistics.getLeastCorrectPredictionType() + "\n" +
                "Number of Notifications: " + notifications.size());
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // toJson
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Method to convert the UserInfo.EnvisionaryUser object to JSON.
    //
    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // fromJson
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Static method to create an UserInfo.EnvisionaryUser object from JSON.
    //
    public static EnvisionaryUser fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, EnvisionaryUser.class);
    }
}
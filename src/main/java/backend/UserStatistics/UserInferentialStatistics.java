package backend.UserStatistics;

import com.google.gson.annotations.SerializedName;
import com.google.gson.Gson;

//■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
// UserStatistics.UserInferentialStatistics object class - Written by Brandon LaPointe
//■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
// Represents a single user's inferential statistics within the Envisionary web app.
//
public class UserInferentialStatistics {
    // UserStatistics.UserInferentialStatistics object variables
    @SerializedName("customPredictionCount")
    protected int customPredictionCount;                    // Number of custom type predictions made by the user
    @SerializedName("sportsPredictionCount")
    protected int sportsPredictionCount;                    // Number of sports type predictions made by the user
    @SerializedName("sciencePredictionCount")
    protected int sciencePredictionCount;                   // Number of science type predictions made by the user
    @SerializedName("weatherPredictionCount")
    protected int weatherPredictionCount;                   // Number of weather type predictions made by the user
    @SerializedName("entertainmentPredictionCount")
    protected int entertainmentPredictionCount;             // Number of entertainment type predictions made by the user
    @SerializedName("predictionsMadeCount")
    protected int predictionsMadeCount;                     // Number of total predictions made by the user
    @SerializedName("customCorrectPredictions")
    protected int customCorrectPredictions;                 // Number of correct custom type predictions made by the user
    @SerializedName("sportsCorrectPredictions")
    protected int sportsCorrectPredictions;                 // Number of correct sports type predictions made by the user
    @SerializedName("scienceCorrectPredictions")
    protected int scienceCorrectPredictions;                // Number of correct science type predictions made by the user
    @SerializedName("weatherCorrectPredictions")
    protected int weatherCorrectPredictions;                // Number of correct weather type predictions made by the user
    @SerializedName("entertainmentCorrectPredictions")
    protected int entertainmentCorrectPredictions;          // Number of correct entertainment type predictions made by the user
    @SerializedName("customIncorrectPredictions")
    protected int customIncorrectPredictions;               // Number of incorrect custom type predictions made by the user
    @SerializedName("sportsIncorrectPredictions")
    protected int sportsIncorrectPredictions;               // Number of incorrect sports type predictions made by the user
    @SerializedName("scienceIncorrectPredictions")
    protected int scienceIncorrectPredictions;              // Number of incorrect science type predictions made by the user
    @SerializedName("weatherIncorrectPredictions")
    protected int weatherIncorrectPredictions;              // Number of incorrect weather type predictions made by the user
    @SerializedName("entertainmentIncorrectPredictions")
    protected int entertainmentIncorrectPredictions;        // Number of incorrect custom type predictions made by the user
    @SerializedName("customPercentCorrect")
    protected double customPercentCorrect;                  // Percentage of correct custom type predictions made by the user
    @SerializedName("sportsPercentCorrect")
    protected double sportsPercentCorrect;                  // Percentage of correct sports type predictions made by the user
    @SerializedName("sciencePercentCorrect")
    protected double sciencePercentCorrect;                 // Percentage of correct science type predictions made by the user
    @SerializedName("weatherPercentCorrect")
    protected double weatherPercentCorrect;                 // Percentage of correct weather type predictions made by the user
    @SerializedName("entertainmentPercentCorrect")
    protected double entertainmentPercentCorrect;           // Percentage of correct entertainment type predictions made by the user
    @SerializedName("customPercentIncorrect")
    protected double customPercentIncorrect;                // Percentage of incorrect custom type predictions made by the user
    @SerializedName("sportsPercentIncorrect")
    protected double sportsPercentIncorrect;                // Percentage of incorrect custom type predictions made by the user
    @SerializedName("sciencePercentIncorrect")
    protected double sciencePercentIncorrect;               // Percentage of incorrect custom type predictions made by the user
    @SerializedName("weatherPercentIncorrect")
    protected double weatherPercentIncorrect;               // Percentage of incorrect custom type predictions made by the user
    @SerializedName("entertainmentPercentIncorrect")
    protected double entertainmentPercentIncorrect;         // Percentage of incorrect custom type predictions made by the user
    @SerializedName("customPercentPredictionsMade")
    protected double customPercentPredictionsMade;          // Percentage of the custom predictions made out of all the user's resolved predictions
    @SerializedName("sportsPercentPredictionsMade")
    protected double sportsPercentPredictionsMade;          // Percentage of the sports predictions made out of all the user's resolved predictions
    @SerializedName("sciencePercentPredictionsMade")
    protected double sciencePercentPredictionsMade;         // Percentage of the science predictions made out of all the user's resolved predictions
    @SerializedName("weatherPercentPredictionsMade")
    protected double weatherPercentPredictionsMade;         // Percentage of the weather predictions made out of all the user's resolved predictions
    @SerializedName("entertainmentPercentPredictionsMade")
    protected double entertainmentPercentPredictionsMade;   // Percentage of the entertainment predictions made out of all the user's resolved predictions
    @SerializedName("mostCorrectPredictionType")
    protected String mostCorrectPredictionType;             // Type of prediction made by the user with the greatest percent correct
    @SerializedName("leastCorrectPredictionType")
    protected String leastCorrectPredictionType;            // Type of prediction made by the user with the least percent correct

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Getter and setter methods for the UserStatistics.UserInferentialStatistics object variables
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    public int getCustomPredictionCount() {
        return customPredictionCount;
    }

    public void setCustomPredictionCount(int customPredictionCount) {
        this.customPredictionCount = customPredictionCount;
    }

    public int getSportsPredictionCount() {
        return sportsPredictionCount;
    }

    public void setSportsPredictionCount(int sportsPredictionCount) {
        this.sportsPredictionCount = sportsPredictionCount;
    }

    public int getSciencePredictionCount() {
        return sciencePredictionCount;
    }

    public void setSciencePredictionCount(int sciencePredictionCount) {
        this.sciencePredictionCount = sciencePredictionCount;
    }

    public int getWeatherPredictionCount() {
        return weatherPredictionCount;
    }

    public void setWeatherPredictionCount(int weatherPredictionCount) {
        this.weatherPredictionCount = weatherPredictionCount;
    }

    public int getEntertainmentPredictionCount() {
        return entertainmentPredictionCount;
    }

    public void setEntertainmentPredictionCount(int entertainmentPredictionCount) {
        this.entertainmentPredictionCount = entertainmentPredictionCount;
    }

    public int getCustomCorrectPredictions() {
        return customCorrectPredictions;
    }

    public void setCustomCorrectPredictions(int customCorrectPredictions) {
        this.customCorrectPredictions = customCorrectPredictions;
    }

    public int getSportsCorrectPredictions() {
        return sportsCorrectPredictions;
    }

    public void setSportsCorrectPredictions(int sportsCorrectPredictions) {
        this.sportsCorrectPredictions = sportsCorrectPredictions;
    }

    public int getScienceCorrectPredictions() {
        return scienceCorrectPredictions;
    }

    public void setScienceCorrectPredictions(int scienceCorrectPredictions) {
        this.scienceCorrectPredictions = scienceCorrectPredictions;
    }

    public int getWeatherCorrectPredictions() {
        return weatherCorrectPredictions;
    }

    public void setWeatherCorrectPredictions(int weatherCorrectPredictions) {
        this.weatherCorrectPredictions = weatherCorrectPredictions;
    }

    public int getEntertainmentCorrectPredictions() {
        return entertainmentCorrectPredictions;
    }

    public void setEntertainmentCorrectPredictions(int entertainmentCorrectPredictions) {
        this.entertainmentCorrectPredictions = entertainmentCorrectPredictions;
    }

    public int getCustomIncorrectPredictions() {
        return customIncorrectPredictions;
    }

    public void setCustomIncorrectPredictions(int customIncorrectPredictions) {
        this.customIncorrectPredictions = customIncorrectPredictions;
    }

    public int getSportsIncorrectPredictions() {
        return sportsIncorrectPredictions;
    }

    public void setSportsIncorrectPredictions(int sportsIncorrectPredictions) {
        this.sportsIncorrectPredictions = sportsIncorrectPredictions;
    }

    public int getScienceIncorrectPredictions() {
        return scienceIncorrectPredictions;
    }

    public void setScienceIncorrectPredictions(int scienceIncorrectPredictions) {
        this.scienceIncorrectPredictions = scienceIncorrectPredictions;
    }

    public int getWeatherIncorrectPredictions() {
        return weatherIncorrectPredictions;
    }

    public void setWeatherIncorrectPredictions(int weatherIncorrectPredictions) {
        this.weatherIncorrectPredictions = weatherIncorrectPredictions;
    }

    public int getEntertainmentIncorrectPredictions() {
        return entertainmentIncorrectPredictions;
    }

    public void setEntertainmentIncorrectPredictions(int entertainmentIncorrectPredictions) {
        this.entertainmentIncorrectPredictions = entertainmentIncorrectPredictions;
    }

    public double getCustomPercentCorrect() {
        return customPercentCorrect;
    }

    public void setCustomPercentCorrect(double customPercentCorrect) {
        this.customPercentCorrect = customPercentCorrect;
    }

    public double getSportsPercentCorrect() {
        return sportsPercentCorrect;
    }

    public void setSportsPercentCorrect(double sportsPercentCorrect) {
        this.sportsPercentCorrect = sportsPercentCorrect;
    }

    public double getSciencePercentCorrect() {
        return sciencePercentCorrect;
    }

    public void setSciencePercentCorrect(double sciencePercentCorrect) {
        this.sciencePercentCorrect = sciencePercentCorrect;
    }

    public double getWeatherPercentCorrect() {
        return weatherPercentCorrect;
    }

    public void setWeatherPercentCorrect(double weatherPercentCorrect) {
        this.weatherPercentCorrect = weatherPercentCorrect;
    }

    public double getEntertainmentPercentCorrect() {
        return entertainmentPercentCorrect;
    }

    public void setEntertainmentPercentCorrect(double entertainmentPercentCorrect) {
        this.entertainmentPercentCorrect = entertainmentPercentCorrect;
    }

    public double getCustomPercentIncorrect() {
        return customPercentIncorrect;
    }

    public void setCustomPercentIncorrect(double customPercentIncorrect) {
        this.customPercentIncorrect = customPercentIncorrect;
    }
    public double getSportsPercentIncorrect() {
        return sportsPercentIncorrect;
    }

    public void setSportsPercentIncorrect(double sportsPercentIncorrect) {
        this.sportsPercentIncorrect = sportsPercentIncorrect;
    }
    public double getSciencePercentIncorrect() {
        return sciencePercentIncorrect;
    }

    public void setSciencePercentIncorrect(double sciencePercentIncorrect) {
        this.sciencePercentIncorrect = sciencePercentIncorrect;
    }
    public double getWeatherPercentIncorrect() {
        return weatherPercentIncorrect;
    }

    public void setWeatherPercentIncorrect(double weatherPercentIncorrect) {
        this.weatherPercentIncorrect = weatherPercentIncorrect;
    }

    public double getEntertainmentPercentIncorrect() {
        return entertainmentPercentIncorrect;
    }

    public void setEntertainmentPercentIncorrect(double entertainmentPercentIncorrect) {
        this.entertainmentPercentIncorrect = entertainmentPercentIncorrect;
    }

    public int getPredictionsMadeCount() {
        return predictionsMadeCount;
    }

    public void setPredictionsMadeCount(int predictionsMadeCount) {
        this.predictionsMadeCount = predictionsMadeCount;
    }

    public double getCustomPercentPredictionsMade() {
        return customPercentPredictionsMade;
    }

    public void setCustomPercentPredictionsMade(double customPercentPredictionsMade) {
        this.customPercentPredictionsMade = customPercentPredictionsMade;
    }

    public double getSportsPercentPredictionsMade() {
        return sportsPercentPredictionsMade;
    }

    public void setSportsPercentPredictionsMade(double sportsPercentPredictionsMade) {
        this.sportsPercentPredictionsMade = sportsPercentPredictionsMade;
    }

    public double getSciencePercentPredictionsMade() {
        return sciencePercentPredictionsMade;
    }

    public void setSciencePercentPredictionsMade(double sciencePercentPredictionsMade) {
        this.sciencePercentPredictionsMade = sciencePercentPredictionsMade;
    }

    public double getWeatherPercentPredictionsMade() {
        return weatherPercentPredictionsMade;
    }

    public void setWeatherPercentPredictionsMade(double weatherPercentPredictionsMade) {
        this.weatherPercentPredictionsMade = weatherPercentPredictionsMade;
    }

    public double getEntertainmentPercentPredictionsMade() {
        return entertainmentPercentPredictionsMade;
    }

    public void setEntertainmentPercentPredictionsMade(double entertainmentPercentPredictionsMade) {
        this.entertainmentPercentPredictionsMade = entertainmentPercentPredictionsMade;
    }

    public String getMostCorrectPredictionType() {
        return mostCorrectPredictionType;
    }

    public void setMostCorrectPredictionType(String mostCorrectPredictionType) {
        this.mostCorrectPredictionType = mostCorrectPredictionType;
    }

    public String getLeastCorrectPredictionType() {
        return leastCorrectPredictionType;
    }

    public void setLeastCorrectPredictionType(String leastCorrectPredictionType) {
        this.leastCorrectPredictionType = leastCorrectPredictionType;
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // printUserInferentialStatistics
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Method to print the UserStatistics.UserInferentialStatistics object details to the console.
    //
    public void printUserInferentialStatistics() {
        System.out.println("-----Prediction Counts per Category-----\n" +
                           "Custom: " + getCustomPredictionCount() + "\n" +
                           "Sports: " + getSportsPredictionCount()  + "\n" +
                           "Science: " + getSciencePredictionCount() + "\n" +
                           "Weather: " + getWeatherPredictionCount() + "\n" +
                           "Entertainment: " + getEntertainmentPredictionCount() + "\n" +
                           "\n" +
                           "-----Number of Correct Predictions per Category-----\n" +
                           "Custom: " + getCustomCorrectPredictions() + "\n" +
                           "Sports: " + getSportsCorrectPredictions() + "\n" +
                           "Science: " + getScienceCorrectPredictions() + "\n" +
                           "Weather: " + getWeatherCorrectPredictions() + "\n" +
                           "Entertainment: " + getEntertainmentCorrectPredictions() + "\n" +
                           "\n" +
                           "-----Number of Incorrect Predictions per Category-----\n" +
                           "Custom: " + getCustomIncorrectPredictions() + "\n" +
                           "Sports: " + getSportsIncorrectPredictions() + "\n" +
                           "Science: " + getScienceIncorrectPredictions() + "\n" +
                           "Weather: " + getWeatherIncorrectPredictions() + "\n" +
                           "Entertainment: " + getEntertainmentIncorrectPredictions() + "\n" +
                           "\n" +
                           "-----Percent of Correct Predictions per Category-----\n" +
                           "Custom: " + getCustomPercentCorrect() + "%" + "\n" +
                           "Sports: " + getSportsPercentCorrect() + "%" + "\n" +
                           "Science: " + getSciencePercentCorrect() + "%" + "\n" +
                           "Weather: " + getWeatherPercentCorrect() + "%" + "\n" +
                           "Entertainment: " + getEntertainmentPercentCorrect() + "%" + "\n" +
                           "\n" +
                           "-----Percent of Incorrect Predictions per Category-----\n" +
                           "Custom: " + getCustomPercentIncorrect() + "%" + "\n" +
                           "Sports: " + getSportsPercentIncorrect() + "%" + "\n" +
                           "Science: " + getSciencePercentIncorrect() + "%" + "\n" +
                           "Weather: " + getWeatherPercentIncorrect() + "%" + "\n" +
                           "Entertainment: " + getEntertainmentPercentIncorrect() + "%" + "\n" +
                           "\n" +
                           "-----Percent of Category Predictions Resolved out of Overall Predictions-----\n" +
                           "Custom: " + getCustomPercentPredictionsMade() + "%" + "\n" +
                           "Sports: " + getSportsPercentPredictionsMade() + "%" + "\n" +
                           "Science: " + getSciencePercentPredictionsMade() + "%" + "\n" +
                           "Weather: " + getWeatherPercentPredictionsMade() + "%" + "\n" +
                           "Entertainment: " + getEntertainmentPercentPredictionsMade() + "%" + "\n" +
                           "\n" +
                           "Based off of your statistical results:");
        if (getMostCorrectPredictionType().equals(getLeastCorrectPredictionType())) {
            System.out.println("You are best at accurately predicting " + getMostCorrectPredictionType() + " type predictions." + "\n" +
                               "Try making predictions within other categories of the Envisionary app.");
        } else {
            System.out.println("You are best at accurately predicting " + getMostCorrectPredictionType() + " type predictions." + "\n" +
                               "You should avoid making " + getLeastCorrectPredictionType() + " type predictions to improve your overall correct predictions." + "\n");
        }
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // toJson
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Method to convert the UserStatistics.UserInferentialStatistics object to JSON.
    //
    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // fromJson
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Static method to create an UserStatistics.UserInferentialStatistics object from JSON.
    //
    public static UserInferentialStatistics fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, UserInferentialStatistics.class);
    }
}
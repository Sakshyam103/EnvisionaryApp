package backend.UserStatistics;

import com.google.gson.annotations.SerializedName;
import com.google.gson.Gson;

//■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
// UserStatistics.UserDescriptiveStatistics object class - Written by Brandon LaPointe
//■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
// Represents a single user's descriptive statistics within the Envisionary web app.
//
public class UserDescriptiveStatistics {
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // UserStatistics.UserDescriptiveStatistics object variables
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    @SerializedName("predictionCount")
    protected int predictionCount;			// Number of predictions made by the user
    @SerializedName("correctPredictions")
    protected int correctPredictions;		// Number of correct predictions made by the user
    @SerializedName("incorrectPredictions")
    protected int incorrectPredictions;		// Number of incorrect predictions made by the user
    @SerializedName("percentCorrect")
    protected double percentCorrect;		// Percentage of correct predictions made by the user
    @SerializedName("percentIncorrect")
    protected double percentIncorrect;		// Percentage of incorrect predictions made by the user

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Getter and setter methods for the UserStatistics.UserDescriptiveStatistics object variables
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    public int getPredictionCount() {
        return predictionCount;
    }
    public void setPredictionCount(int predictionCount) {
        this.predictionCount = predictionCount;
    }
    public int getCorrectPredictions() {
        return correctPredictions;
    }
    public void setCorrectPredictions(int correctPredictions) {
        this.correctPredictions = correctPredictions;
    }
    public int getIncorrectPredictions() {
        return incorrectPredictions;
    }
    public void setIncorrectPredictions(int incorrectPredictions) {
        this.incorrectPredictions = incorrectPredictions;
    }
    public double getPercentCorrect() {
        return percentCorrect;
    }
    public void setPercentCorrect(double percentCorrect) {
        this.percentCorrect = percentCorrect;
    }
    public double getPercentIncorrect() {
        return percentIncorrect;
    }
    public void setPercentIncorrect(double percentIncorrect) {
        this.percentIncorrect = percentIncorrect;
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Default Empty Constructor
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    public UserDescriptiveStatistics() {}

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Constructor with Parameters
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    public UserDescriptiveStatistics(int predictionCount, int correctPredictions, int incorrectPredictions, double percentCorrect, double percentIncorrect) {
        this.predictionCount = predictionCount;
        this.correctPredictions = correctPredictions;
        this.incorrectPredictions = incorrectPredictions;
        this.percentCorrect = percentCorrect;
        this.percentIncorrect = percentIncorrect;
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // printUserDescriptiveStatistics
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Method to print the UserStatistics.UserDescriptiveStatistics object details to the console.
    //
    public void printUserDescriptiveStatistics() {
        System.out.println("Prediction Count: " + getPredictionCount());
        System.out.println("Correct Predictions: " + getCorrectPredictions());
        System.out.println("Incorrect Predictions: " + getIncorrectPredictions());
        System.out.println("Percentage of Correct Predictions: " + getPercentCorrect() + "%");
        System.out.println("Percentage of Incorrect Predictions: " + getPercentIncorrect() + "%");
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // toJson
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Method to convert the UserStatistics.UserDescriptiveStatistics object to JSON.
    //
    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // fromJson
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Static method to create an UserStatistics.UserDescriptiveStatistics object from JSON.
    //
    public static UserDescriptiveStatistics fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, UserDescriptiveStatistics.class);
    }
}

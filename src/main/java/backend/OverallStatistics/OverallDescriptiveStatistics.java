package backend.OverallStatistics;

import com.google.gson.annotations.SerializedName;
import com.google.gson.Gson;
import java.util.ArrayList;

//■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
// OverallStatistics.OverallDescriptiveStatistics object class - Written by Brandon LaPointe
//■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
// Represents the overall descriptive statistics of all users within the Envisionary web app.
//
public class OverallDescriptiveStatistics {
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // OverallStatistics.OverallDescriptiveStatistics object variables
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    @SerializedName("overallPredictionCount")
    protected int overallPredictionCount;               // Number of predictions made by all users
    @SerializedName("overallCorrectPredictions")
    protected int overallCorrectPredictions;            // Number of correct predictions made by all users
    @SerializedName("overallIncorrectPredictions")
    protected int overallIncorrectPredictions;          // Number of incorrect predictions made by all users
    @SerializedName("overallPercentCorrect")
    protected double overallPercentCorrect;             // Percentage of correct predictions made by all users
    @SerializedName("overallPercentIncorrect")
    protected double overallPercentIncorrect;           // Percentage of incorrect predictions made by all users
    @SerializedName("minPercentCorrect")
    protected double minPercentCorrect;                 // Minimum value of percent correct among the users
    @SerializedName("maxPercentCorrect")
    protected double maxPercentCorrect;                 // Maximum value of percent correct among the users
    @SerializedName("meanCorrectPercentage")
    protected double meanCorrectPercentage;             // Mean (average) of correct percentages among users
    @SerializedName("medianCorrectPercentage")
    protected double medianCorrectPercentage;           // Median of correct percentages among users
    @SerializedName("modeCorrectPercentage")
    protected ArrayList<Double> modeCorrectPercentage;  // Mode of correct percentages among users
    @SerializedName("standardDeviation")
    protected double standardDeviation;                 // Standard deviation of correct percentages among users

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Getter and setter methods for the OverallStatistics.OverallDescriptiveStatistics object variables
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    public int getOverallPredictionCount() {
        return overallPredictionCount;
    }
    public void setOverallPredictionCount(int overallPredictionCount) {
        this.overallPredictionCount = overallPredictionCount;
    }

    public int getOverallCorrectPredictions() {
        return overallCorrectPredictions;
    }
    public void setOverallCorrectPredictions(int overallCorrectPredictions) {
        this.overallCorrectPredictions = overallCorrectPredictions;
    }

    public int getOverallIncorrectPredictions() {
        return overallIncorrectPredictions;
    }
    public void setOverallIncorrectPredictions(int overallIncorrectPredictions) {
        this.overallIncorrectPredictions = overallIncorrectPredictions;
    }

    public double getOverallPercentCorrect() {
        return overallPercentCorrect;
    }
    public void setOverallPercentCorrect(double overallPercentCorrect) {
        this.overallPercentCorrect = overallPercentCorrect;
    }

    public double getOverallPercentIncorrect() {
        return overallPercentIncorrect;
    }
    public void setOverallPercentIncorrect(double overallPercentIncorrect) {
        this.overallPercentIncorrect = overallPercentIncorrect;
    }

    public double getMinPercentCorrect() {
        return minPercentCorrect;
    }
    public void setMinPercentCorrect(double minPercentCorrect) {
        this.minPercentCorrect = minPercentCorrect;
    }

    public double getMaxPercentCorrect() {
        return maxPercentCorrect;
    }
    public void setMaxPercentCorrect(double maxPercentCorrect) {
        this.maxPercentCorrect = maxPercentCorrect;
    }

    public double getMeanCorrectPercentage() {
        return meanCorrectPercentage;
    }
    public void setMeanCorrectPercentage(double meanCorrectPercentage) {
        this.meanCorrectPercentage = meanCorrectPercentage;
    }

    public double getMedianCorrectPercentage() {
        return medianCorrectPercentage;
    }
    public void setMedianCorrectPercentage(double medianCorrectPercentage) {
        this.medianCorrectPercentage = medianCorrectPercentage;
    }

    public ArrayList<Double> getModeCorrectPercentage() {
        return modeCorrectPercentage;
    }
    public void setModeCorrectPercentage(ArrayList<Double> modeCorrectPercentage) {
        this.modeCorrectPercentage = modeCorrectPercentage;
    }

    public double getStandardDeviation() {
        return standardDeviation;
    }
    public void setStandardDeviation(double standardDeviation) {
        this.standardDeviation = standardDeviation;
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Default Empty Constructor
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    public OverallDescriptiveStatistics() {}

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // printOverallDescriptiveStatistics
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Method to print the OverallStatistics.OverallDescriptiveStatistics object details to the console.
    //
    public void printOverallDescriptiveStatistics() {
        System.out.println("OverallPredictionCount: " + getOverallPredictionCount() + "\n" +
                           "Overall Correct Predictions: " + getOverallCorrectPredictions() + "\n" +
                           "Overall Incorrect Predictions: " + getOverallIncorrectPredictions() + "\n" +
                           "Overall Percent Correct: " + getOverallPercentCorrect() + "%" + "\n" +
                           "Overall Percent Incorrect: " + getOverallPercentIncorrect() + "%" + "\n" +
                           "Minimum Percent Correct: " + getMinPercentCorrect() + "%" + "\n" +
                           "Maximum Percent Correct: " + getMaxPercentCorrect() + "%" + "\n" +
                           "Mean Correct Percentage: " + getMeanCorrectPercentage()  + "%" + "\n" +
                           "Median Correct Percentage: " + getMedianCorrectPercentage()  + "%" + "\n" +
                           "Mode Correct Percentage: " + getModeCorrectPercentage()  + "%" + "\n" +
                           "Standard Deviation: " + getStandardDeviation() + "%");
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // toJson
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Method to convert the OverallStatistics.OverallDescriptiveStatistics object to JSON.
    //
    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // fromJson
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Static method to create an OverallStatistics.OverallDescriptiveStatistics object from JSON.
    //
    public static OverallDescriptiveStatistics fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, OverallDescriptiveStatistics.class);
    }
}
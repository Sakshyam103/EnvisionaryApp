package backend.FootballMatchPredictions;

import backend.BasePredictionObject.Prediction;
import com.google.gson.annotations.SerializedName;
import com.google.gson.Gson;

//■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
// FootballMatchPredictions.FootballMatchPrediction object class - Written by Brandon LaPointe
//■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
// Represents a football (soccer) match prediction made by an Envisionary web app user.
//
public final class FootballMatchPrediction {
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // FootballMatchPredictions.FootballMatchPrediction object variables
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
	@SerializedName("prediction")
    private Prediction prediction = new Prediction();				// BasePredictionsObject.Prediction object
	@SerializedName("predictionMatch")
    private FootballMatch predictionMatch = new FootballMatch();	// Football match object
	@SerializedName("predictedMatchTeam")
    private String predictedMatchTeam;								// Predicted team name within the football match
	@SerializedName("predictedMatchOutcome")
    private String predictedMatchOutcome;							// Predicted football match outcome for the team ("win", "loss", "draw")

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Getter and setter methods for the FootballMatchPredictions.FootballMatchPrediction object variables
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    public FootballMatch getPredictionMatch() {
        return predictionMatch;
    }
    public void setPredictionMatch(FootballMatch predictionMatch) {
        this.predictionMatch = predictionMatch;
    }

    public Prediction getPrediction() {
        return prediction;
    }
    public void setPrediction(Prediction prediction) {
        this.prediction = prediction;
    }

    public String getPredictedMatchTeam() {
        return predictedMatchTeam;
    }
    public void setPredictedMatchTeam(String predictedMatchTeam) {
        this.predictedMatchTeam = predictedMatchTeam;
    }

    public String getPredictedMatchOutcome() {
        return predictedMatchOutcome;
    }
    public void setPredictedMatchOutcome(String predictedMatchOutcome) {
        this.predictedMatchOutcome = predictedMatchOutcome;
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Default Empty Constructor
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    public FootballMatchPrediction() {}

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Constructor with Parameters
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    public FootballMatchPrediction(FootballMatch predictionMatch, Prediction prediction, String predictedMatchTeam, String predictedMatchOutcome) {
        this.predictionMatch = predictionMatch;
        this.prediction = prediction;
        this.predictedMatchTeam = predictedMatchTeam;
        this.predictedMatchOutcome = predictedMatchOutcome;
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // printPredictionDetails
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Method to print the FootballMatchPredictions.FootballMatchPrediction object details to the console.
    //
    public void printPredictionDetails() {
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("Football Match BasePredictionsObject.Prediction Details:");
        System.out.println("BasePredictionsObject.Prediction Type: " + prediction.getPredictionType());
        System.out.println("BasePredictionsObject.Prediction Content: " + prediction.getPredictionContent());
        System.out.println("BasePredictionsObject.Prediction Made Date: " + prediction.getPredictionMadeDate());
        System.out.println("BasePredictionsObject.Prediction End Date: " + prediction.getPredictionEndDate());
        System.out.println("Reminder Frequency: " + prediction.getRemindFrequency());
        System.out.println("-------------------------------");
        System.out.println("Football Match Details:");
        predictionMatch.printMatchDetails();
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------");
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // toJson
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Method to convert the FootballMatchPredictions.FootballMatchPrediction object to JSON.
    //
    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // fromJson
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Static method to create a FootballMatchPredictions.FootballMatchPrediction object from JSON.
    //
    public static FootballMatchPrediction fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, FootballMatchPrediction.class);
    }
}
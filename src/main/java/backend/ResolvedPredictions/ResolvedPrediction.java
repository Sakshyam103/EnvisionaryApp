package backend.ResolvedPredictions;

import com.google.gson.annotations.SerializedName;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

//■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
// ResolvedPredictions.ResolvedPrediction object class - Written by Brandon LaPointe
//■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
// Represents a resolved prediction made by a user within the Envisionary web app.
//
public class ResolvedPrediction {
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // ResolvedPredictions.ResolvedPrediction object variables
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    @SerializedName("predictionType")
    private String predictionType; 		    // Custom, Sport, Entertainment, Weather, Science
    @SerializedName("predictionContent")
    private String predictionContent; 	    // The text content of the prediction
    @SerializedName("predictionMadeDate")
    private String predictionMadeDate; 	    // Date of the prediction being created
    @SerializedName("predictionEndDate")
    private String predictionEndDate; 	    // Date of the prediction ending (null if not set)
    @SerializedName("resolution")
    public boolean resolution; 			// Boolean value of the resolution of the prediction
    @SerializedName("resolvedDate")
    private String resolvedDate; 			// Date of the resolution of the prediction

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Getter and setter methods for the ResolvedPredictions.ResolvedPrediction object variables
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    public String getPredictionType() {
        return predictionType;
    }
    public void setPredictionType(String predictionType) {
        this.predictionType = predictionType;
    }
    public String getPredictionContent() {
        return predictionContent;
    }
    public void setPredictionContent(String predictionContent) {
        this.predictionContent = predictionContent;
    }
    public String getPredictionMadeDate() {
        return predictionMadeDate;
    }
    public void setPredictionMadeDate(String predictionMadeDate) {
        this.predictionMadeDate = predictionMadeDate;
    }
    public String getPredictionEndDate() {
        return predictionEndDate;
    }
    public void setPredictionEndDate(String predictionEndDate) {
        this.predictionEndDate = predictionEndDate;
    }
    public boolean getResolution() {
        return resolution;
    }
    public void setResolution(boolean resolution) {
        this.resolution = resolution;
    }
    public String getResolvedDate() {
        return resolvedDate;
    }
    public void setResolvedDate(String resolvedDate) {
        this.resolvedDate = resolvedDate;
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Default Empty Constructor
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    public ResolvedPrediction() {}

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Constructor with Parameters (String madeDate and endDate)
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    public ResolvedPrediction(String predictionType, String predictionContent, String predictionMadeDate, String predictionEndDate) {
        this.predictionType = predictionType;
        this.predictionContent = predictionContent;
        this.predictionMadeDate = predictionMadeDate;
        this.predictionEndDate = predictionEndDate;
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // toJson
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Method to convert the ResolvedPredictions.ResolvedPrediction object to JSON.
    //
    public void printPredictionDetails() {
        System.out.println(
                "--------------------------------------------------------------------------------" + "\n" +
                        "Resolved BasePredictionsObject.Prediction Details:" + "\n" +
                        "BasePredictionsObject.Prediction Type: " + getPredictionType() + "\n" +
                        "BasePredictionsObject.Prediction Content: " + getPredictionContent() + "\n" +
                        "BasePredictionsObject.Prediction Made Date: " + getPredictionMadeDate() + "\n" +
                        "BasePredictionsObject.Prediction End Date: " + getPredictionEndDate() + "\n" +
                        "Resolution: " + getResolution() + "\n" +
                        "Resolved Date: " + getResolvedDate() + "\n" +
                        "--------------------------------------------------------------------------------"
        );
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // toJson
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Method to convert the ResolvedPredictions.ResolvedPrediction object to JSON.
    //
    public String toJson() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // fromJson
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Static method to create a ResolvedPredictions.ResolvedPrediction object from JSON.
    //
    public static ResolvedPrediction fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, ResolvedPrediction.class);
    }
}
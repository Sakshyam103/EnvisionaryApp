package backend.BasePredictionObject;

import com.google.gson.annotations.SerializedName;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

//■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
// BasePredictionsObject.Prediction object class
//■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
// Represents a prediction made by a user within the Envisionary web app.
//
public class Prediction {
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // BasePredictionsObject.Prediction object variables
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    @SerializedName("predictionType")
    private String predictionType;         // Custom, Entertainment, FootballMatch, Science, Weather
    @SerializedName("predictionContent")
    private String predictionContent;      // The text content of the prediction
    @SerializedName("predictionMadeDate")
    private String predictionMadeDate; 		// Date of the prediction being created
    @SerializedName("predictionEndDate")
    private String predictionEndDate;  		// Date of the prediction ending (null if not set)
    @SerializedName("remindFrequency")
    private String remindFrequency;         // Set reminder custom date(s), daily, weekly, monthly, ...


    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Getter and setter methods for the BasePredictionsObject.Prediction object variables
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
    public String getRemindFrequency() {
        return remindFrequency;
    }
    public void setRemindFrequency(String remindFrequency) {
        this.remindFrequency = remindFrequency;
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Default Empty Constructor
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    public Prediction() {}

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Constructor with Parameters (String madeDate and endDate)
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    public Prediction(String predictionType, String predictionContent, String predictionMadeDate, String predictionEndDate, String remindFrequency) {
        this.predictionType = predictionType;
        this.predictionContent = predictionContent;
        this.predictionMadeDate = predictionMadeDate;
        this.predictionEndDate = predictionEndDate;
        this.remindFrequency = remindFrequency;
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Constructor with Parameters (ZonedDateTime madeDate and endDate)
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    public Prediction(String predictionType, String predictionContent, ZonedDateTime predictionMadeDate, ZonedDateTime predictionEndDate, String remindFrequency) {
        this.predictionType = predictionType;
        this.predictionContent = predictionContent;
        this.predictionMadeDate = predictionMadeDate.toString();
        this.predictionEndDate = predictionEndDate.toString();
        this.remindFrequency = remindFrequency;
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Constructor with Parameters (LocalDateTime madeDate and endDate)
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    public Prediction(String predictionType, String predictionContent, LocalDateTime predictionMadeDate, LocalDateTime predictionEndDate, String remindFrequency) {
        this.predictionType = predictionType;
        this.predictionContent = predictionContent;
        this.predictionMadeDate = predictionMadeDate.toString();
        this.predictionEndDate = predictionEndDate.toString();
        this.remindFrequency = remindFrequency;
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // printPredictionDetails
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Method to print the BasePredictionsObject.Prediction object details to the console.
    //
    public void printPredictionDetails() {
        System.out.println("BasePredictionsObject.Prediction Type: " + predictionType);
        System.out.println("BasePredictionsObject.Prediction Content: " + predictionContent);
        System.out.println("BasePredictionsObject.Prediction Made Date: " + predictionMadeDate);

        if (predictionEndDate != null) {
            System.out.println("BasePredictionsObject.Prediction End Date: " + predictionEndDate);
        } else {
            System.out.println("BasePredictionsObject.Prediction End Date: Not set");
        }

        System.out.println("Reminder Frequency: " + remindFrequency);
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // toJson
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Method to convert the BasePredictionsObject.Prediction object to JSON.
    //
    public String toJson() {
        Gson gson = new GsonBuilder().create();
        return gson.toJson(this);
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // fromJson
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Static method to create a BasePredictionsObject.Prediction object from JSON.
    //
    public static Prediction fromJson(String json) {
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(json, Prediction.class);
    }
}
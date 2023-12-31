package backend.CelestialBodyPredictions;

import backend.BasePredictionObject.Prediction;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public final class CelestialBodyPrediction {
    //*****************************************************************************
    // celestialBodyPrediction object variables
    @SerializedName("prediction")
    private Prediction prediction = new Prediction();

    @SerializedName("celestialBody")
    private CelestialBody celestialBody = new CelestialBody();

    //*****************************************************************************
    // getter and setter methods for CelestialBodyPrediction object variables
    public Prediction getPrediction() {
        return prediction;
    }
    public void setPrediction(Prediction prediction) {
        this.prediction = prediction;
    }
    public CelestialBody getCelestialBody() {
        return celestialBody;
    }
    public void setCelestialBody(CelestialBody celestialBody) {
        this.celestialBody = celestialBody;
    }

    //*****************************************************************************
    // default empty constructor
    public CelestialBodyPrediction() {}

    //*****************************************************************************
    // constructor with parameters
    public CelestialBodyPrediction(Prediction prediction, CelestialBody celestialBody) {
        this.prediction = prediction;
        this.celestialBody = celestialBody;
    }

    //*****************************************************************************
    // printPredictionDetails
    public void printPredictionDetails() {
        System.out.println(
                "--------------------------------------------------------------------------------" + "\n" +
                "Celestial Body Prediction Details:" + "\n" +
                "Prediction Type: " + prediction.getPredictionType() + "\n" +
                "Prediction Content: " + prediction.getPredictionContent() + "\n" +
                "Prediction Made Date: " + prediction.getPredictionMadeDate() + "\n" +
                "Prediction End Date: " + prediction.getPredictionEndDate() + "\n" +
                "Reminder Frequency: " + prediction.getRemindFrequency() + "\n" +
                "Celestial Body Prediction Details:" + "\n" +
                "Celestial Body Type: " + celestialBody.getCelestialBodyType() + "\n" +
                "Known Count: " + celestialBody.getKnownCount() + "\n" +
                "Last Updated: " + celestialBody.getUpdatedDate() + "\n" +
                "--------------------------------------------------------------------------------"
        );
    }

    //*****************************************************************************
    //toJson
    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    //*****************************************************************************
    //fromJson
    public static CelestialBodyPrediction fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, CelestialBodyPrediction.class);
    }
}
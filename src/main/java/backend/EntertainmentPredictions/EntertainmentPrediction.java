//package backend.EntertainmentPredictions;
//
//import backend.BasePredictionObject.Prediction;
//import com.google.gson.annotations.SerializedName;
//import com.google.gson.Gson;
//
//public final class EntertainmentPrediction {
//
//    // make those variables
//
//    @SerializedName("prediction")
//    private Prediction prediction = new Prediction();
//
//    @SerializedName("movieTitle")
//    private String movieTitle;
//
//    @SerializedName("predictedYear")
//    private int predictedYear;
//
//    // get those variables
//
//    public Prediction getPrediction() {return prediction;}
//
//    public String getMovieTitle() {return movieTitle;}
//
//    public int getPredictedYear() {return predictedYear;}
//
//    // set those variables
//
//
//    public void setPrediction(Prediction prediction) {this.prediction = prediction;}
//
//    public void setMovieTitle(String movieTitle) {this.movieTitle = movieTitle;}
//
//    public void setPredictedYear(int predictedYear) {this.predictedYear = predictedYear;}
//
//    // the empty one
//
//    public EntertainmentPrediction() {}
//
//    // the complete one
//
//    public EntertainmentPrediction(Prediction prediction, String movieTitle, int predictedYear){
//        this.prediction = prediction;
//        this.movieTitle = movieTitle;
//        this.predictedYear = predictedYear;
//    }
//
//    // to view the movie prediction
//
//    public void printPredictionDetails() {
//        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------");
//        System.out.println("Entertainment BasePredictionsObject.Prediction Details");
//        System.out.println("BasePredictionsObject.Prediction Type: " + prediction.getPredictionType());
//        System.out.println("BasePredictionsObject.Prediction Content: " + prediction.getPredictionContent());
//        System.out.println("BasePredictionsObject.Prediction Made Date: " + prediction.getPredictionMadeDate());
//        System.out.println("BasePredictionsObject.Prediction End Date: " + prediction.getPredictionEndDate());
//        System.out.println("Reminder Frequency: " + prediction.getRemindFrequency());
//        System.out.println("-------------------------------");
//        System.out.println("Entertainment Prediction Details:");
//        System.out.println("Movie Title as Entered by User: " + movieTitle);
//        System.out.println("Predicted Year of Release: " + predictedYear);
//        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------");
//    }
//}

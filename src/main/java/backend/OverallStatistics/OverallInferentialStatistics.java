package backend.OverallStatistics;

import com.google.gson.annotations.SerializedName;
import com.google.gson.Gson;
import java.util.ArrayList;

//■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
// OverallStatistics.OverallInferentialStatistics object class
//■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
// Represents the overall inferential statistics of all users within the Envisionary web app.
//
public class OverallInferentialStatistics {
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // OverallStatistics.OverallInferentialStatistics object variables
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    @SerializedName("customPredictionCount")
    protected int customPredictionCount;                                // Number of custom type predictions made by all users
    @SerializedName("sportsPredictionCount")
    protected int sportsPredictionCount;                                // Number of sports type predictions made by all users
    @SerializedName("sciencePredictionCount")
    protected int sciencePredictionCount;                               // Number of science type predictions made by all users
    @SerializedName("weatherPredictionCount")
    protected int weatherPredictionCount;                               // Number of weather type predictions made by all users
    @SerializedName("entertainmentPredictionCount")
    protected int entertainmentPredictionCount;                         // Number of entertainment type predictions made by all users
    @SerializedName("predictionsMadeCount")
    protected int predictionsMadeCount;                                 // Number of total predictions made by all users
    @SerializedName("customCorrectPredictions")
    protected int customCorrectPredictions;                             // Number of correct custom type predictions made by all users
    @SerializedName("sportsCorrectPredictions")
    protected int sportsCorrectPredictions;                             // Number of correct sports type predictions made by all users
    @SerializedName("scienceCorrectPredictions")
    protected int scienceCorrectPredictions;                            // Number of correct science type predictions made by all users
    @SerializedName("weatherCorrectPredictions")
    protected int weatherCorrectPredictions;                            // Number of correct weather type predictions made by all users
    @SerializedName("entertainmentCorrectPredictions")
    protected int entertainmentCorrectPredictions;                      // Number of correct entertainment type predictions made by all users
    @SerializedName("customIncorrectPredictions")
    protected int customIncorrectPredictions;                           // Number of incorrect custom type predictions made by all users
    @SerializedName("sportsIncorrectPredictions")
    protected int sportsIncorrectPredictions;                           // Number of incorrect sports type predictions made by all users
    @SerializedName("scienceIncorrectPredictions")
    protected int scienceIncorrectPredictions;                          // Number of incorrect science type predictions made by all users
    @SerializedName("weatherIncorrectPredictions")
    protected int weatherIncorrectPredictions;                          // Number of incorrect weather type predictions made by all users
    @SerializedName("entertainmentIncorrectPredictions")
    protected int entertainmentIncorrectPredictions;                    // Number of incorrect custom type predictions made by all users
    @SerializedName("customPercentCorrect")
    protected double customPercentCorrect;                              // Percentage of correct custom type predictions made by all users
    @SerializedName("sportsPercentCorrect")
    protected double sportsPercentCorrect;                              // Percentage of correct sports type predictions made by all users
    @SerializedName("sciencePercentCorrect")
    protected double sciencePercentCorrect;                             // Percentage of correct science type predictions made by all users
    @SerializedName("weatherPercentCorrect")
    protected double weatherPercentCorrect;                             // Percentage of correct weather type predictions made by all users
    @SerializedName("entertainmentPercentCorrect")
    protected double entertainmentPercentCorrect;                       // Percentage of correct entertainment type predictions made by all users
    @SerializedName("customPercentIncorrect")
    protected double customPercentIncorrect;                            // Percentage of incorrect custom type predictions made by all users
    @SerializedName("sportsPercentIncorrect")
    protected double sportsPercentIncorrect;                            // Percentage of incorrect custom type predictions made by all users
    @SerializedName("sciencePercentIncorrect")
    protected double sciencePercentIncorrect;                           // Percentage of incorrect custom type predictions made by all users
    @SerializedName("weatherPercentIncorrect")
    protected double weatherPercentIncorrect;                           // Percentage of incorrect custom type predictions made by all users
    @SerializedName("entertainmentPercentIncorrect")
    protected double entertainmentPercentIncorrect;                     // Percentage of incorrect custom type predictions made by all users
    @SerializedName("customPercentPredictionsMade")
    protected double customPercentPredictionsMade;                      // Percentage of the custom predictions made out of all users' resolved predictions
    @SerializedName("sportsPercentPredictionsMade")
    protected double sportsPercentPredictionsMade;                      // Percentage of the sports predictions made out of all users' resolved predictions
    @SerializedName("sciencePercentPredictionsMade")
    protected double sciencePercentPredictionsMade;                     // Percentage of the science predictions made out of all users' resolved predictions
    @SerializedName("weatherPercentPredictionsMade")
    protected double weatherPercentPredictionsMade;                     // Percentage of the weather predictions made out of all users' resolved predictions
    @SerializedName("entertainmentPercentPredictionsMade")
    protected double entertainmentPercentPredictionsMade;               // Percentage of the entertainment predictions made out of all users' resolved predictions
    @SerializedName("mostCorrectPredictionType")
    protected String mostCorrectPredictionType;                         // Type of prediction made by all users with the greatest percent correct
    @SerializedName("leastCorrectPredictionType")
    protected String leastCorrectPredictionType;                        // Type of prediction made by all users with the least percent correct
    @SerializedName("minPercentCorrectCustom")
    protected double minPercentCorrectCustom;                           // Minimum percent of correct custom predictions among all users
    @SerializedName("maxPercentCorrectCustom")
    protected double maxPercentCorrectCustom;                           // Maximum percent of correct custom predictions among all users
    @SerializedName("minPercentCorrectSports")
    protected double minPercentCorrectSports;                           // Minimum percent of correct sports predictions among all users
    @SerializedName("maxPercentCorrectSports")
    protected double maxPercentCorrectSports;                           // Maximum percent of correct sports predictions among all users
    @SerializedName("minPercentCorrectScience")
    protected double minPercentCorrectScience;                          // Minimum percent of correct science predictions among all users
    @SerializedName("maxPercentCorrectScience")
    protected double maxPercentCorrectScience;                          // Maximum percent of correct science predictions among all users
    @SerializedName("minPercentCorrectWeather")
    protected double minPercentCorrectWeather;                          // Minimum percent of correct weather predictions among all users
    @SerializedName("maxPercentCorrectWeather")
    protected double maxPercentCorrectWeather;                          // Maximum percent of correct weather predictions among all users
    @SerializedName("minPercentCorrectEntertainment")
    protected double minPercentCorrectEntertainment;                    // Minimum percent of correct entertainment predictions among all users
    @SerializedName("maxPercentCorrectEntertainment")
    protected double maxPercentCorrectEntertainment;                    // Maximum percent of correct entertainment predictions among all users
    @SerializedName("meanCorrectPercentageCustom")
    protected double meanCorrectPercentageCustom;                       // Mean (average) of correct percentages of custom predictions among all users
    @SerializedName("meanCorrectPercentageSports")
    protected double meanCorrectPercentageSports;                       // Mean (average) of correct percentages of sports predictions among all users
    @SerializedName("meanCorrectPercentageScience")
    protected double meanCorrectPercentageScience;                      // Mean (average) of correct percentages of science predictions among all users
    @SerializedName("meanCorrectPercentageWeather")
    protected double meanCorrectPercentageWeather;                      // Mean (average) of correct percentages of weather predictions among all users
    @SerializedName("meanCorrectPercentageEntertainment")
    protected double meanCorrectPercentageEntertainment;                // Mean (average) of correct percentages of entertainment predictions among all users
    @SerializedName("medianCorrectPercentageCustom")
    protected double medianCorrectPercentageCustom;                     // Median of correct percentages of custom predictions among all users
    @SerializedName("medianCorrectPercentageSports")
    protected double medianCorrectPercentageSports;                     // Median of correct percentages of sports predictions among all users
    @SerializedName("medianCorrectPercentageScience")
    protected double medianCorrectPercentageScience;                    // Median of correct percentages of science predictions among all users
    @SerializedName("medianCorrectPercentageWeather")
    protected double medianCorrectPercentageWeather;                    // Median of correct percentages of weather predictions among all users
    @SerializedName("medianCorrectPercentageEntertainment")
    protected double medianCorrectPercentageEntertainment;              // Median of correct percentages of entertainment predictions among all users
    @SerializedName("modeCorrectPercentageCustom")
    protected ArrayList<Double> modeCorrectPercentageCustom;            // Mode(s) of correct percentages of custom predictions among all users
    @SerializedName("modeCorrectPercentageSports")
    protected ArrayList<Double> modeCorrectPercentageSports;            // Mode(s) of correct percentages of sports predictions among all users
    @SerializedName("modeCorrectPercentageScience")
    protected ArrayList<Double> modeCorrectPercentageScience;           // Mode(s) of correct percentages of science predictions among all users
    @SerializedName("modeCorrectPercentageWeather")
    protected ArrayList<Double> modeCorrectPercentageWeather;           // Mode(s) of correct percentages of weather predictions among all users
    @SerializedName("modeCorrectPercentageEntertainment")
    protected ArrayList<Double> modeCorrectPercentageEntertainment;     // Mode(s) of correct percentages of entertainment predictions among all users
    @SerializedName("standardDeviationCorrectPercentageCustom")
    protected double standardDeviationCorrectPercentageCustom;          // Standard deviation of correct percentages of custom predictions among all users
    @SerializedName("standardDeviationCorrectPercentageSports")
    protected double standardDeviationCorrectPercentageSports;          // Standard deviation of correct percentages of sports predictions among all users
    @SerializedName("standardDeviationCorrectPercentageScience")
    protected double standardDeviationCorrectPercentageScience;          // Standard deviation of correct percentages of science predictions among all users
    @SerializedName("standardDeviationCorrectPercentageWeather")
    protected double standardDeviationCorrectPercentageWeather;          // Standard deviation of correct percentages of weather predictions among all users
    @SerializedName("standardDeviationCorrectPercentageEntertainment")
    protected double standardDeviationCorrectPercentageEntertainment;    // Standard deviation of correct percentages of entertainment predictions among all users

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Getter and setter methods for the OverallStatistics.OverallInferentialStatistics object variables
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

    public double getMinPercentCorrectCustom() {
        return minPercentCorrectCustom;
    }

    public void setMinPercentCorrectCustom(double minPercentCorrectCustom) {
        this.minPercentCorrectCustom = minPercentCorrectCustom;
    }

    public double getMaxPercentCorrectCustom() {
        return maxPercentCorrectCustom;
    }

    public void setMaxPercentCorrectCustom(double maxPercentCorrectCustom) {
        this.maxPercentCorrectCustom = maxPercentCorrectCustom;
    }

    public double getMinPercentCorrectSports() {
        return minPercentCorrectSports;
    }

    public void setMinPercentCorrectSports(double minPercentCorrectSports) {
        this.minPercentCorrectSports = minPercentCorrectSports;
    }

    public double getMaxPercentCorrectSports() {
        return maxPercentCorrectSports;
    }

    public void setMaxPercentCorrectSports(double maxPercentCorrectSports) {
        this.maxPercentCorrectSports = maxPercentCorrectSports;
    }

    public double getMinPercentCorrectScience() {
        return minPercentCorrectScience;
    }

    public void setMinPercentCorrectScience(double minPercentCorrectScience) {
        this.minPercentCorrectScience = minPercentCorrectScience;
    }

    public double getMaxPercentCorrectScience() {
        return maxPercentCorrectScience;
    }

    public void setMaxPercentCorrectScience(double maxPercentCorrectScience) {
        this.maxPercentCorrectScience = maxPercentCorrectScience;
    }

    public double getMinPercentCorrectWeather() {
        return minPercentCorrectWeather;
    }

    public void setMinPercentCorrectWeather(double minPercentCorrectWeather) {
        this.minPercentCorrectWeather = minPercentCorrectWeather;
    }

    public double getMaxPercentCorrectWeather() {
        return maxPercentCorrectWeather;
    }

    public void setMaxPercentCorrectWeather(double maxPercentCorrectWeather) {
        this.maxPercentCorrectWeather = maxPercentCorrectWeather;
    }

    public double getMinPercentCorrectEntertainment() {
        return minPercentCorrectEntertainment;
    }

    public void setMinPercentCorrectEntertainment(double minPercentCorrectEntertainment) {
        this.minPercentCorrectEntertainment = minPercentCorrectEntertainment;
    }

    public double getMaxPercentCorrectEntertainment() {
        return maxPercentCorrectEntertainment;
    }

    public void setMaxPercentCorrectEntertainment(double maxPercentCorrectEntertainment) {
        this.maxPercentCorrectEntertainment = maxPercentCorrectEntertainment;
    }

    public double getMeanCorrectPercentageCustom() {
        return meanCorrectPercentageCustom;
    }

    public void setMeanCorrectPercentageCustom(double meanCorrectPercentageCustom) {
        this.meanCorrectPercentageCustom = meanCorrectPercentageCustom;
    }

    public double getMeanCorrectPercentageSports() {
        return meanCorrectPercentageSports;
    }

    public void setMeanCorrectPercentageSports(double meanCorrectPercentageSports) {
        this.meanCorrectPercentageSports = meanCorrectPercentageSports;
    }

    public double getMeanCorrectPercentageScience() {
        return meanCorrectPercentageScience;
    }

    public void setMeanCorrectPercentageScience(double meanCorrectPercentageScience) {
        this.meanCorrectPercentageScience = meanCorrectPercentageScience;
    }


    public double getMeanCorrectPercentageWeather() {
        return meanCorrectPercentageWeather;
    }

    public void setMeanCorrectPercentageWeather(double meanCorrectPercentageWeather) {
        this.meanCorrectPercentageWeather = meanCorrectPercentageWeather;
    }


    public void setMeanCorrectPercentageEntertainment(double meanCorrectPercentageEntertainment) {
        this.meanCorrectPercentageEntertainment = meanCorrectPercentageEntertainment;
    }

    public double getMeanCorrectPercentageEntertainment() {
        return meanCorrectPercentageEntertainment;
    }

    public double getMedianCorrectPercentageCustom() {
        return medianCorrectPercentageCustom;
    }

    public void setMedianCorrectPercentageCustom(double medianCorrectPercentageCustom) {
        this.medianCorrectPercentageCustom = medianCorrectPercentageCustom;
    }

    public double getMedianCorrectPercentageSports() {
        return medianCorrectPercentageSports;
    }

    public void setMedianCorrectPercentageSports(double medianCorrectPercentageSports) {
        this.medianCorrectPercentageSports = medianCorrectPercentageSports;
    }

    public double getMedianCorrectPercentageScience() {
        return medianCorrectPercentageScience;
    }

    public void setMedianCorrectPercentageScience(double medianCorrectPercentageScience) {
        this.medianCorrectPercentageScience = medianCorrectPercentageScience;
    }

    public double getMedianCorrectPercentageWeather() {
        return medianCorrectPercentageWeather;
    }

    public void setMedianCorrectPercentageWeather(double medianCorrectPercentageWeather) {
        this.medianCorrectPercentageWeather = medianCorrectPercentageWeather;
    }

    public double getMedianCorrectPercentageEntertainment() {
        return medianCorrectPercentageEntertainment;
    }

    public void setMedianCorrectPercentageEntertainment(double medianCorrectPercentageEntertainment) {
        this.medianCorrectPercentageEntertainment = medianCorrectPercentageEntertainment;
    }

    public ArrayList<Double> getModeCorrectPercentageCustom() {
        return modeCorrectPercentageCustom;
    }

    public void setModeCorrectPercentageCustom(ArrayList<Double> modeCorrectPercentageCustom) {
        this.modeCorrectPercentageCustom = modeCorrectPercentageCustom;
    }

    public ArrayList<Double> getModeCorrectPercentageSports() {
        return modeCorrectPercentageSports;
    }

    public void setModeCorrectPercentageSports(ArrayList<Double> modeCorrectPercentageSports) {
        this.modeCorrectPercentageSports = modeCorrectPercentageSports;
    }

    public ArrayList<Double> getModeCorrectPercentageScience() {
        return modeCorrectPercentageScience;
    }

    public void setModeCorrectPercentageScience(ArrayList<Double> modeCorrectPercentageScience) {
        this.modeCorrectPercentageScience = modeCorrectPercentageScience;
    }

    public ArrayList<Double> getModeCorrectPercentageWeather() {
        return modeCorrectPercentageWeather;
    }

    public void setModeCorrectPercentageWeather(ArrayList<Double> modeCorrectPercentageWeather) {
        this.modeCorrectPercentageWeather = modeCorrectPercentageWeather;
    }

    public ArrayList<Double> getModeCorrectPercentageEntertainment() {
        return modeCorrectPercentageEntertainment;
    }

    public void setModeCorrectPercentageEntertainment(ArrayList<Double> modeCorrectPercentageEntertainment) {
        this.modeCorrectPercentageEntertainment = modeCorrectPercentageEntertainment;
    }

    public double getStandardDeviationCorrectPercentageCustom() {
        return standardDeviationCorrectPercentageCustom;
    }

    public void setStandardDeviationCorrectPercentageCustom(double standardDeviationCorrectPercentageCustom) {
        this.standardDeviationCorrectPercentageCustom = standardDeviationCorrectPercentageCustom;
    }

    public double getStandardDeviationCorrectPercentageSports() {
        return standardDeviationCorrectPercentageSports;
    }

    public void setStandardDeviationCorrectPercentageSports(double standardDeviationCorrectPercentageSports) {
        this.standardDeviationCorrectPercentageSports = standardDeviationCorrectPercentageSports;
    }

    public double getStandardDeviationCorrectPercentageScience() {
        return standardDeviationCorrectPercentageScience;
    }

    public void setStandardDeviationCorrectPercentageScience(double standardDeviationCorrectPercentageScience) {
        this.standardDeviationCorrectPercentageScience = standardDeviationCorrectPercentageScience;
    }

    public double getStandardDeviationCorrectPercentageWeather() {
        return standardDeviationCorrectPercentageWeather;
    }

    public void setStandardDeviationCorrectPercentageWeather(double standardDeviationCorrectPercentageWeather) {
        this.standardDeviationCorrectPercentageWeather = standardDeviationCorrectPercentageWeather;
    }

    public double getStandardDeviationCorrectPercentageEntertainment() {
        return standardDeviationCorrectPercentageEntertainment;
    }

    public void setStandardDeviationCorrectPercentageEntertainment(double standardDeviationCorrectPercentageEntertainment) {
        this.standardDeviationCorrectPercentageEntertainment = standardDeviationCorrectPercentageEntertainment;
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Default Empty Constructor
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    //
    public OverallInferentialStatistics() {}

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // printOverallInferentialStatistics
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Method to print the OverallStatistics.OverallInferentialStatistics object details to the console.
    //
    public void printOverallInferentialStatistics() {
        System.out.println("-----Overall BasePredictionsObject.Prediction Counts per Category-----\n" +
                "Custom: " + getCustomPredictionCount() + "\n" +
                "Sports: " + getSportsPredictionCount()  + "\n" +
                "Science: " + getSciencePredictionCount() + "\n" +
                "Weather: " + getWeatherPredictionCount() + "\n" +
                "Entertainment: " + getEntertainmentPredictionCount() + "\n" +
                "\n" +
                "-----Overall Number of Correct Predictions per Category-----\n" +
                "Custom: " + getCustomCorrectPredictions() + "\n" +
                "Sports: " + getSportsCorrectPredictions() + "\n" +
                "Science: " + getScienceCorrectPredictions() + "\n" +
                "Weather: " + getWeatherCorrectPredictions() + "\n" +
                "Entertainment: " + getEntertainmentCorrectPredictions() + "\n" +
                "\n" +
                "-----Overall Number of Incorrect Predictions per Category-----\n" +
                "Custom: " + getCustomIncorrectPredictions() + "\n" +
                "Sports: " + getSportsIncorrectPredictions() + "\n" +
                "Science: " + getScienceIncorrectPredictions() + "\n" +
                "Weather: " + getWeatherIncorrectPredictions() + "\n" +
                "Entertainment: " + getEntertainmentIncorrectPredictions() + "\n" +
                "\n" +
                "-----Overall Percent of Correct Predictions per Category-----\n" +
                "Custom: " + getCustomPercentCorrect() + "%" + "\n" +
                "Sports: " + getSportsPercentCorrect() + "%" + "\n" +
                "Science: " + getSciencePercentCorrect() + "%" + "\n" +
                "Weather: " + getWeatherPercentCorrect() + "%" + "\n" +
                "Entertainment: " + getEntertainmentPercentCorrect() + "%" + "\n" +
                "\n" +
                "-----Overall Percent of Incorrect Predictions per Category-----\n" +
                "Custom: " + getCustomPercentIncorrect() + "%" + "\n" +
                "Sports: " + getSportsPercentIncorrect() + "%" + "\n" +
                "Science: " + getSciencePercentIncorrect() + "%" + "\n" +
                "Weather: " + getWeatherPercentIncorrect() + "%" + "\n" +
                "Entertainment: " + getEntertainmentPercentIncorrect() + "%" + "\n" +
                "\n" +
                "-----Overall Percent of Category Predictions Resolved out of Overall Predictions-----\n" +
                "Custom: " + getCustomPercentPredictionsMade() + "%" + "\n" +
                "Sports: " + getSportsPercentPredictionsMade() + "%" + "\n" +
                "Science: " + getSciencePercentPredictionsMade() + "%" + "\n" +
                "Weather: " + getWeatherPercentPredictionsMade() + "%" + "\n" +
                "Entertainment: " + getEntertainmentPercentPredictionsMade() + "%" + "\n" +
                "\n" +
                "-----Minimum of each Category Predictions Resolved-----\n" +
                "Custom: " + getMinPercentCorrectCustom() + "%" + "\n" +
                "Sports: " + getMinPercentCorrectSports() + "%" + "\n" +
                "Science: " + getMinPercentCorrectScience() + "%" + "\n" +
                "Weather: " + getMinPercentCorrectWeather() + "%" + "\n" +
                "Entertainment: " + getMinPercentCorrectEntertainment() + "%" + "\n" +
                "\n" +
                "-----Maximum of each Category Predictions Resolved-----\n" +
                "Custom: " + getMaxPercentCorrectCustom() + "%" + "\n" +
                "Sports: " + getMaxPercentCorrectSports() + "%" + "\n" +
                "Science: " + getMaxPercentCorrectScience() + "%" + "\n" +
                "Weather: " + getMaxPercentCorrectWeather() + "%" + "\n" +
                "Entertainment: " + getMaxPercentCorrectEntertainment() + "%" + "\n" +
                "\n" +
                "-----Mean Correct Percentage per Category-----\n" +
                "Custom: " + getMeanCorrectPercentageCustom() + "%" + "\n" +
                "Sports: " + getMeanCorrectPercentageSports() + "%" + "\n" +
                "Science: " + getMeanCorrectPercentageScience() + "%" + "\n" +
                "Weather: " + getMeanCorrectPercentageWeather() + "%" + "\n" +
                "Entertainment: " + getMeanCorrectPercentageEntertainment() + "%" + "\n" +
                "\n" +
                "-----Median of Correct Percentages per Category-----\n" +
                "Custom: " + getMedianCorrectPercentageCustom() + "%" + "\n" +
                "Sports: " + getMedianCorrectPercentageSports() + "%" + "\n" +
                "Science: " + getMedianCorrectPercentageScience() + "%" + "\n" +
                "Weather: " + getMedianCorrectPercentageWeather() + "%" + "\n" +
                "Entertainment: " + getMedianCorrectPercentageEntertainment() + "%" + "\n" +
                "\n" +
                "-----Mode of Correct Percentages per Category-----\n" +
                "Custom: " + getModeCorrectPercentageCustom() + "%" + "\n" +
                "Sports: " + getModeCorrectPercentageSports() + "%" + "\n" +
                "Science: " + getModeCorrectPercentageScience() + "%" + "\n" +
                "Weather: " + getModeCorrectPercentageWeather() + "%" + "\n" +
                "Entertainment: " + getModeCorrectPercentageEntertainment() + "%" + "\n" +
                "\n" +
                "-----Standard Deviation of Correct Percentage per Category-----\n" +
                "Custom: " + getStandardDeviationCorrectPercentageCustom() + "%" + "\n" +
                "Sports: " + getStandardDeviationCorrectPercentageSports() + "%" + "\n" +
                "Science: " + getStandardDeviationCorrectPercentageScience() + "%" + "\n" +
                "Weather: " + getStandardDeviationCorrectPercentageWeather() + "%" + "\n" +
                "Entertainment: " + getStandardDeviationCorrectPercentageEntertainment() + "%" + "\n" +
                "\n" +
                "Based off of all users' statistical results:\n" +
                "Envisionary users are most accurately predicting " + getMostCorrectPredictionType() + " type predictions." + "\n" +
                "Envisionary users are least accurately predicting " + getLeastCorrectPredictionType() + " type predictions." + "\n");
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // toJson
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Method to convert the OverallStatistics.OverallInferentialStatistics object to JSON.
    //
    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // fromJson
    //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    // Static method to create an OverallStatistics.OverallInferentialStatistics object from JSON.
    //
    public static OverallInferentialStatistics fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, OverallInferentialStatistics.class);
    }
}
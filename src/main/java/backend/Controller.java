package backend;

import backend.CelestialBodyPredictions.CelestialBody;
import backend.CelestialBodyPredictions.CelestialBodyPrediction;
import backend.CelestialBodyPredictions.MongoDBCelestialBodyData;
import backend.CelestialBodyPredictions.SavePlanets;
import backend.CustomPredictions.CustomPrediction;
import backend.CustomPredictions.SaveCustomPredictions;
import backend.EntertainmentPredictions.buildEntertainmentPrediction;
import backend.EntertainmentPredictions.entertainmentAmountCheck;
import backend.FootballMatchPredictions.FootballMatchList;
import backend.FootballMatchPredictions.FootballMatchPrediction;
import backend.FootballMatchPredictions.MongoDBFootballMatchData;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
import backend.FootballMatchPredictions.SaveFootballPredictions;
import backend.OverallStatistics.MongoDBOverallDescriptiveStatistics;
import backend.OverallStatistics.MongoDBOverallInferentialStatistics;
import backend.OverallStatistics.OverallDescriptiveStatistics;
import backend.OverallStatistics.OverallInferentialStatistics;
import backend.ResolvedPredictions.ResolvedPrediction;
import backend.UserInfo.MongoDBEnvisionaryUsers;
import backend.UserStatistics.UserDescriptiveStatistics;
import backend.UserStatistics.UserInferentialStatistics;
import backend.WeatherPredictions.SaveWeatherPredictions;
import backend.WeatherPredictions.WeatherPrediction;
import org.bson.Document;
import org.springframework.web.bind.annotation.*;
//import org.springframework.web.bind.annotation.RequestHeader;
//import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestHeader;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.http.MediaType;
// import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
// import org.springframework.stereotype.Service;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;

import java.io.StringReader;
import java.util.ArrayList;

import org.json.*;

import javax.json.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
// @RequestMapping("/whee")
public class Controller {
    public static String userId;
    private static String email;
    public static Document userDoc;

    // @GetMapping("/example")
    // public ResponseEntity<String> exampleEndpoint() {
    // return ResponseEntity.ok("Hello");

    // }
    // @Autowired
    // private SignInRequest signInRequest;

    @RequestMapping(value = "/login")
    public @ResponseBody String handleSignIn(@RequestBody(required = false) String idString) throws JSONException {
        SignInRequest signInRequest1 = new SignInRequest();
        signInRequest1.setIdToken(idString);
        System.out.println(signInRequest1.getIdToken());
        parseId(signInRequest1.getIdToken());
        if (MongoDBEnvisionaryUsers.retrieveUserEmail(userId) == null) {
            MongoDBEnvisionaryUsers.insertIndividualEnvisionaryUser(userId, email);
        }
        userDoc = GetUserInfo.getTheDoc();
        return "Login successful";
    }

    @RequestMapping(value = "/viewPrediction")
    public ArrayList<ResolvedPrediction> viewPrediction() {
        System.out.println("view prediction");
        // TODO: Make each category's view prediction and use correct retrieve methods.
        // THIS IS A TEST WITH A HARDCODED USER
        ArrayList<ResolvedPrediction> a = MongoDBEnvisionaryUsers.retrieveUserResolvedPredictions("TestUser");
        return a;
    }

    @RequestMapping(value = "/viewStatistics")
    public ArrayList<String> viewStatistics() {
        System.out.println("view statistics");
        ArrayList<String> a = new ArrayList<>();
        a.add("eat");
        a.add("ball");
        return a;
    }

    @RequestMapping(value = "/sendMatches")
    public @ResponseBody String sendMatches(@RequestBody(required = false) String idString) throws JSONException {
//        SignInRequest signInRequest1 = new SignInRequest();
//        signInRequest1.setIdToken(idString);
//        System.out.println(signInRequest1.getIdToken());
//        parseId(signInRequest1.getIdToken());
//        if (MongoDBEnvisionaryUsers.retrieveUserEmail(userId) == null) {
//            MongoDBEnvisionaryUsers.insertIndividualEnvisionaryUser(userId, email);
//        }
//        userDoc = GetUserInfo.getTheDoc();
        System.out.println(idString);

        return "";
    }

    @RequestMapping(value = "/viewNotification")
    public ArrayList<String> viewNotification() {
        System.out.println("view notification");
        ArrayList<String> a = new ArrayList<>();
        a.add("rice");
        a.add("ball");
        return a;
    }



    @RequestMapping(value = "/viewMatches")
    public ArrayList<String> viewMatches() throws JSONException {
        System.out.println("view matches");
        FootballMatchList a = MongoDBFootballMatchData.retrieveDocumentsWithinTimeFrameAndReturn("UpcomingWeek1");
        //System.out.println(a.toString());
        ArrayList<String> options = parseMatches(a);;

        return options;
    }

    public ArrayList<String> parseMatches(FootballMatchList a) throws JSONException {
        JSONObject jsonObject = new JSONObject(a);
        JSONArray footballMatches = (JSONArray) jsonObject.get("footballMatches");
        ArrayList<String> options = new ArrayList<>();
        for(int i = 0; i < footballMatches.length(); ++i){
            JSONObject main = footballMatches.getJSONObject(i);
            String homeTeam = (String)main.get("homeTeam");
            String awayTeam = (String) main.get("awayTeam");
            String option = homeTeam + " vs " + awayTeam;
            options.add(option);
        }
        return options;

    }

    @RequestMapping(value = "/viewCustomPredictions")
    public ArrayList<CustomPrediction> viewCustomPredictions() {
        System.out.println("view custom predictions");
        // THIS IS A TEST WITH A HARDCODED USER
        //return MongoDBEnvisionaryUsers.retrieveUserCustomPredictions("TestUser");
        return MongoDBEnvisionaryUsers.retrieveUserCustomPredictions(userId);
    }

    @RequestMapping(value = "/viewCelestialBodyPredictions")
    public ArrayList<CelestialBodyPrediction> viewCelestialBodyPredictions() {
        System.out.println("view celestial body predictions");
        // THIS IS A TEST WITH A HARDCODED USER
        //return MongoDBEnvisionaryUsers.retrieveUserCelestialBodyPredictions("TestUser");
        return MongoDBEnvisionaryUsers.retrieveUserCelestialBodyPredictions(userId);
    }

    @RequestMapping(value = "/viewFootballMatchPredictions")
    public ArrayList<FootballMatchPrediction> viewFootballMatchPredictions() {
        System.out.println("view football match predictions");
        // THIS IS A TEST WITH A HARDCODED USER
        //return MongoDBEnvisionaryUsers.retrieveUserFootballMatchPredictions("TestUser");
        return MongoDBEnvisionaryUsers.retrieveUserFootballMatchPredictions(userId);
    }

    @RequestMapping(value = "/viewWeatherPredictions")
    public ArrayList<WeatherPrediction> viewWeatherPredictions() {
        System.out.println("view weather predictions");
        // THIS IS A TEST WITH A HARDCODED USER
        //return MongoDBEnvisionaryUsers.retrieveUserWeatherPredictions("TestUser");
        return MongoDBEnvisionaryUsers.retrieveUserWeatherPredictions(userId);
    }

    @RequestMapping(value = "/viewEntertainmentPredictions")
    public ArrayList<ResolvedPrediction> viewEntertainmentPredictions() {
        System.out.println("view entertainment predictions");
        return MongoDBEnvisionaryUsers.retrieveUserResolvedEntertainmentPredictions(userId);
    }

    @RequestMapping(value = "/viewResolvedPredictions")
    public ArrayList<ResolvedPrediction> viewResolvedPredictions() {
        System.out.println("view resolved predictions");
        // THIS IS A TEST WITH A HARDCODED USER
        //return MongoDBEnvisionaryUsers.retrieveUserResolvedPredictions("TestUser");
        return MongoDBEnvisionaryUsers.retrieveUserResolvedPredictions(userId);
    }

    // These are all the methods to return the specific categories of resolved predictions
    @RequestMapping(value = "/viewResolvedCustomPredictions")
    public ArrayList<ResolvedPrediction> viewResolvedCustomPredictions() {
        System.out.println("view resolved custom predictions");
        // THIS IS A TEST WITH A HARDCODED USER
        //return MongoDBEnvisionaryUsers.retrieveUserResolvedCustomPredictions("TestUser");
        return MongoDBEnvisionaryUsers.retrieveUserResolvedCustomPredictions(userId);
    }

    // Same as above
    @RequestMapping(value = "/viewResolvedCelestialBodyPredictions")
    public ArrayList<ResolvedPrediction> viewResolvedCelestialBodyPredictions() {
        System.out.println("view resolved celestial body predictions");
        // THIS IS A TEST WITH A HARDCODED USER
        //return MongoDBEnvisionaryUsers.retrieveUserResolvedCelestialBodyPredictions("TestUser");
        return MongoDBEnvisionaryUsers.retrieveUserResolvedCelestialBodyPredictions(userId);
    }

    // Same as above
    @RequestMapping(value = "/viewResolvedFootballMatchPredictions")
    public ArrayList<ResolvedPrediction> viewResolvedFootballMatchPredictions() {
        System.out.println("view resolved football match predictions");
        // THIS IS A TEST WITH A HARDCODED USER
        //return MongoDBEnvisionaryUsers.retrieveUserResolvedFootballMatchPredictions("TestUser");
        return MongoDBEnvisionaryUsers.retrieveUserResolvedFootballMatchPredictions(userId);
    }

    // Same as above
    @RequestMapping(value = "/viewResolvedWeatherPredictions")
    public ArrayList<ResolvedPrediction> viewResolvedWeatherPredictions() {
        System.out.println("view resolved weather predictions");
        // THIS IS A TEST WITH A HARDCODED USER
        //return MongoDBEnvisionaryUsers.retrieveUserResolvedWeatherPredictions("TestUser");
        return MongoDBEnvisionaryUsers.retrieveUserResolvedWeatherPredictions(userId);
    }

    // Same as above
    @RequestMapping(value = "/viewResolvedEntertainmentPredictions")
    public ArrayList<ResolvedPrediction> viewResolvedEntertainmentPredictions() {
        System.out.println("view resolved entertainment predictions");
        // THIS IS A TEST WITH A HARDCODED USER
        //return MongoDBEnvisionaryUsers.retrieveUserResolvedEntertainmentPredictions("TestUser");
        return MongoDBEnvisionaryUsers.retrieveUserResolvedEntertainmentPredictions(userId);
    }

    // User descriptive statistics
    @RequestMapping(value = "/viewUserDescriptiveStatistics")
    public UserDescriptiveStatistics viewUserDescriptiveStatistics() {
        System.out.println("view user descriptive statistics");
        // THIS IS A TEST WITH A HARDCODED USER
        //return MongoDBEnvisionaryUsers.retrieveUserDescriptiveStatistics("TestUser");
        return MongoDBEnvisionaryUsers.retrieveUserDescriptiveStatistics(userId);
    }

    // User inferential statistics - mainly need to display the most and least correct categories for the user - I can explain more in person
    @RequestMapping(value = "/viewUserInferentialStatistics")
    public UserInferentialStatistics viewUserInferentialStatistics() {
        System.out.println("view user inferential statistics");
        // THIS IS A TEST WITH A HARDCODED USER
        //return MongoDBEnvisionaryUsers.retrieveUserInferentialStatistics("TestUser");
        return MongoDBEnvisionaryUsers.retrieveUserInferentialStatistics(userId);
    }

    // Overall descriptive statistics
    @RequestMapping(value = "/viewOverallDescriptiveStatistics")
    public OverallDescriptiveStatistics viewOverallDescriptiveStatistics() {
        System.out.println("view overall descriptive statistics");
        return MongoDBOverallDescriptiveStatistics.retrieveCollection();
    }

    // Overall inferential statistics - mainly need to display the most and least correct categories for the user - I can explain more in person
    @RequestMapping(value = "/viewOverallInferentialStatistics")
    public OverallInferentialStatistics viewOverallInferentialStatistics() {
        System.out.println("view overall inferential statistics");
        return MongoDBOverallInferentialStatistics.retrieveCollection();
    }

//    // Ignore for now
//    @RequestMapping(value = "/viewFootballMatchesToday")
//    public FootballMatchList viewFootballMatchesToday() {
//        System.out.println("view today's football matches");
//        return MongoDBFootballMatchData.retrieveDocumentsWithinTimeFrameAndReturn("Today");
//    }
//
//    // Ignore for now
//    @RequestMapping(value = "/viewFootballMatchesTomorrow")
//    public FootballMatchList viewFootballMatchesTomorrow() {
//        System.out.println("view tomorrow's football matches");
//        return MongoDBFootballMatchData.retrieveDocumentsWithinTimeFrameAndReturn("Tomorrow");
//    }

    // Just stick to this for the football matches
    @RequestMapping(value = "/viewFootballMatchesUpcomingWeek1")
    public FootballMatchList viewFootballMatchesUpcomingWeek1() {
        System.out.println("view upcoming week's football matches");
        return MongoDBFootballMatchData.retrieveDocumentsWithinTimeFrameAndReturn("UpcomingWeek1");
    }

//    // Ignore for now
//    @RequestMapping(value = "/viewFootballMatchesUpcomingWeek2")
//    public FootballMatchList viewFootballMatchesUpcomingWeek2() {
//        System.out.println("view second upcoming week's football matches");
//        return MongoDBFootballMatchData.retrieveDocumentsWithinTimeFrameAndReturn("UpcomingWeek2");
//    }
//
//    @RequestMapping(value = "/viewFootballMatchesUpcomingWeek3")
//    public FootballMatchList viewFootballMatchesUpcomingWeek3() {
//        System.out.println("view third upcoming week's football matches");
//        return MongoDBFootballMatchData.retrieveDocumentsWithinTimeFrameAndReturn("UpcomingWeek3");
//    }

    @RequestMapping(value = "/viewCelestialBodies")
    public ArrayList<CelestialBody> viewCelestialBodies() {
        System.out.println("view celestial bodies");
        return MongoDBCelestialBodyData.retrieveCollection();
    }
    //******************************************************************************************************************

    // Frontend to Backend
    // Saving the Predictions
    @RequestMapping(value = "/movies")
    public @ResponseBody String saveUserMovie(@RequestBody(required = false) String data) throws JSONException {
        System.out.println(data);
        StringReader stringReader = new StringReader(data);
        JsonReaderFactory factory = Json.createReaderFactory(null);
        JsonReader reader = factory.createReader(stringReader);
        JsonObject object = reader.readObject();
        return buildEntertainmentPrediction.buildMoviePrediction(object, userId);
    }

    @RequestMapping(value = "/custom")
    public @ResponseBody boolean saveUserCustom(@RequestBody(required = false) String data) throws JSONException {
        System.out.println(data);
        StringReader stringReader = new StringReader(data);
        JsonReaderFactory factory = Json.createReaderFactory(null);
        JsonReader reader = factory.createReader(stringReader);
        JsonObject object = reader.readObject();
        boolean success = SaveCustomPredictions.buildCustom(object);
        if(success){
            System.out.println("Custom Prediction Saved");
            return success;
        }
        else{
            System.out.println("Error in saving Custom Prediction");
            return success;
        }
    }

    @RequestMapping(value = "/weather")
    public @ResponseBody boolean saveUserWeather(@RequestBody(required = false) String data) throws JSONException {
        System.out.println(data);
        StringReader stringReader = new StringReader(data);
        JsonReaderFactory factory = Json.createReaderFactory(null);
        JsonReader reader = factory.createReader(stringReader);
        JsonObject object = reader.readObject();
        boolean success = SaveWeatherPredictions.buildWeather(object);
        if(success){
            System.out.println("Weather Prediction Saved");
            return success;
        }
        else{
            System.out.println("Error in saving Weather Prediction");
            return success;
        }
    }

    @RequestMapping(value = "/football")
    public @ResponseBody boolean saveUserFootball(@RequestBody(required = false) String data) throws JsonException {
        System.out.println(data);
        StringReader stringReader = new StringReader(data);
        JsonReaderFactory factory = Json.createReaderFactory(null);
        JsonReader reader = factory.createReader(stringReader);
        JsonObject object = reader.readObject();
        boolean success = SaveFootballPredictions.buildFootball(object);
        if(success){
            System.out.println("Football Prediction Saved");
            return success;
        }
        else{
            System.out.println("Error in saving Football Prediction");
            return success;
        }
    }

    @RequestMapping(value = "/space")
    public @ResponseBody boolean saveUserSpace(@RequestBody(required = false) String data) throws JSONException {
        System.out.println(data);
        StringReader stringReader = new StringReader(data);
        JsonReaderFactory factory = Json.createReaderFactory(null);
        JsonReader reader = factory.createReader(stringReader);
        JsonObject object = reader.readObject();
        boolean success = SavePlanets.buildSpace(object);
        if(success){
            System.out.println("Space Prediction Saved");
            return success;
        }
        else{
            System.out.println("Error in saving Space Prediction");
            return success;
        }
    }

    @RequestMapping(value = "/customResolve")
    public @ResponseBody boolean resolveCustom(@RequestBody(required = false) String data) throws JSONException {
        System.out.println(data);
        StringReader stringReader = new StringReader(data);
        JsonReaderFactory factory = Json.createReaderFactory(null);
        JsonReader reader = factory.createReader(stringReader);
        JsonObject object = reader.readObject();
        boolean success = SaveCustomPredictions.resolveCustomPrediction(object);
        if(success){
            System.out.println("Custom Prediction Resolved");
            return success;
        }
        else{
            System.out.println("Error in saving Custom Prediction Resolved");
            return success;
        }
    }
    @RequestMapping(value = "/movieCheck")
    public @ResponseBody boolean checkMovie() throws JSONException {
        return entertainmentAmountCheck.makeOrBreak();
    }

    public void parseId(String id) throws JSONException {
        JSONObject jsonObject = new JSONObject(id);
        JSONObject idString = (JSONObject) jsonObject.get("idString");
        // System.out.println(idString);
        email = (String) idString.get("email");
        System.out.println("email: " + email);
        userId = (String) idString.get("sub");
        System.out.println(userId);
    }

    @Component
    static class SignInRequest {
        private String idToken;

        public String getIdToken() {
            return idToken;
        }

        public void setIdToken(String idToken) {
            this.idToken = idToken;
        }

    }
}

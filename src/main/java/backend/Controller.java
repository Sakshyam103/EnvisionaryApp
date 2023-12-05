package backend;

import backend.CelestialBodyPredictions.CelestialBody;
import backend.CelestialBodyPredictions.CelestialBodyPrediction;
import backend.CelestialBodyPredictions.MongoDBCelestialBodyData;
import backend.CustomPredictions.CustomPrediction;
import backend.FootballMatchPredictions.FootballMatchList;
import backend.FootballMatchPredictions.MongoDBFootballMatchData;
import backend.FootballMatchPredictions.FootballMatchPrediction;
import backend.Notifications.Notification;
import backend.OverallStatistics.MongoDBOverallDescriptiveStatistics;
import backend.OverallStatistics.MongoDBOverallInferentialStatistics;
import backend.OverallStatistics.OverallDescriptiveStatistics;
import backend.OverallStatistics.OverallInferentialStatistics;
import backend.UserStatistics.UserDescriptiveStatistics;
import backend.UserStatistics.UserInferentialStatistics;
import backend.WeatherPredictions.DailyForecast;
import backend.WeatherPredictions.MongoDBWeatherData;
import backend.WeatherPredictions.WeatherPrediction;
import backend.ResolvedPredictions.ResolvedPrediction;
import backend.UserInfo.MongoDBEnvisionaryUsers;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
import backend.UserInfo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.StringReader;
import java.util.ArrayList;

import org.json.*;

import javax.json.Json;
import javax.json.JsonReader;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
// @RequestMapping("/whee")
public class Controller {
    private static String userId;
    private static String email;

    // @GetMapping("/example")
    // public ResponseEntity<String> exampleEndpoint() {
    // return ResponseEntity.ok("Hello");

    // }
    // @Autowired
    // private SignInRequest signInRequest;

    @RequestMapping(value = "/login")
    public @ResponseBody String handleSignIn(@RequestBody(required = false) String idString) throws JSONException {
        System.out.println("---test");
        SignInRequest signInRequest1 = new SignInRequest();
        signInRequest1.setIdToken(idString);
        System.out.println(signInRequest1.getIdToken());
        parseId(signInRequest1.getIdToken());
        if (MongoDBEnvisionaryUsers.retrieveUserEmail(userId) == null) {
            MongoDBEnvisionaryUsers.insertIndividualEnvisionaryUser(userId, email);
        }
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
        // SEE BELOW
        // I ADDED METHODS FOR viewUserDescriptiveStatistics, viewUserInferentialStatistics, viewOverallDescriptiveStatistics, and viewOverallInferentialStatistics
        return a;
    }

    @RequestMapping(value = "/viewNotification")
    public ArrayList<Notification> viewNotification() {
        System.out.println("view notification");
        ArrayList<Notification> userNotifications = MongoDBEnvisionaryUsers.retrieveUserNotifications(userId);
        return userNotifications;
    }

    @RequestMapping(value = "/viewMatches")
    public FootballMatchList viewMatches() {
        System.out.println("view matches");
        FootballMatchList a = MongoDBFootballMatchData.retrieveDocumentsWithinTimeFrameAndReturn("UpcomingWeek1");
        return a;
    }

    //******************************************************************************************************************
    // Additional methods to gather specific data from mongodb
    //******************************************************************************************************************
    @RequestMapping(value = "/viewCustomPredictions")
    public ArrayList<CustomPrediction> viewCustomPredictions() {
        System.out.println("view custom predictions");
        // THIS IS A TEST WITH A HARDCODED USER
        //return MongoDBEnvisionaryUsers.retrieveUserCustomPredictions("TestUser");
        //
        return MongoDBEnvisionaryUsers.retrieveUserCustomPredictions(userId);
    }

    @RequestMapping(value = "/viewCelestialBodyPredictions")
    public ArrayList<CelestialBodyPrediction> viewCelestialBodyPredictions() {
        System.out.println("view custom predictions");
        // THIS IS A TEST WITH A HARDCODED USER
        //return MongoDBEnvisionaryUsers.retrieveUserCelestialBodyPredictions("TestUser");
        return MongoDBEnvisionaryUsers.retrieveUserCelestialBodyPredictions(userId);
    }

    @RequestMapping(value = "/viewFootballMatchPredictions")
    public ArrayList<FootballMatchPrediction> viewFootballMatchPredictions() {
        System.out.println("view custom predictions");
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

//    @RequestMapping(value = "/viewEntertainmentPredictions")
//    public ArrayList<EntertainmentPrediction> viewEntertainmentPredictions() {
//        System.out.println("view custom predictions");
//        // THIS IS A TEST WITH A HARDCODED USER
//        //return MongoDBEnvisionaryUsers.retrieveUserEntertainmentPredictions("TestUser");
//        return MongoDBEnvisionaryUsers.retrieveUserEntertainmentPredictions(userId);
//    }

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
        System.out.println("view resolved custom predictions");
        // THIS IS A TEST WITH A HARDCODED USER
        //return MongoDBEnvisionaryUsers.retrieveUserResolvedCelestialBodyPredictions("TestUser");
        return MongoDBEnvisionaryUsers.retrieveUserResolvedCelestialBodyPredictions(userId);
    }

    // Same as above
    @RequestMapping(value = "/viewResolvedFootballMatchPredictions")
    public ArrayList<ResolvedPrediction> viewResolvedFootballMatchPredictions() {
        System.out.println("view resolved custom predictions");
        // THIS IS A TEST WITH A HARDCODED USER
        //return MongoDBEnvisionaryUsers.retrieveUserResolvedFootballMatchPredictions("TestUser");
        return MongoDBEnvisionaryUsers.retrieveUserResolvedFootballMatchPredictions(userId);
    }

    // Same as above
    @RequestMapping(value = "/viewResolvedWeatherPredictions")
    public ArrayList<ResolvedPrediction> viewResolvedWeatherPredictions() {
        System.out.println("view resolved custom predictions");
        // THIS IS A TEST WITH A HARDCODED USER
        //return MongoDBEnvisionaryUsers.retrieveUserResolvedWeatherPredictions("TestUser");
        return MongoDBEnvisionaryUsers.retrieveUserResolvedWeatherPredictions(userId);
    }

    // Same as above
    @RequestMapping(value = "/viewResolvedEntertainmentPredictions")
    public ArrayList<ResolvedPrediction> viewResolvedEntertainmentPredictions() {
        System.out.println("view resolved custom predictions");
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

    // Ignore for now
    @RequestMapping(value = "/viewFootballMatchesToday")
    public FootballMatchList viewFootballMatchesToday() {
        System.out.println("view today's football matches");
        return MongoDBFootballMatchData.retrieveDocumentsWithinTimeFrameAndReturn("Today");
    }

    // Ignore for now
    @RequestMapping(value = "/viewFootballMatchesTomorrow")
    public FootballMatchList viewFootballMatchesTomorrow() {
        System.out.println("view tomorrow's football matches");
        return MongoDBFootballMatchData.retrieveDocumentsWithinTimeFrameAndReturn("Tomorrow");
    }

    // Just stick to this for the football matches
    @RequestMapping(value = "/viewFootballMatchesUpcomingWeek1")
    public FootballMatchList viewFootballMatchesUpcomingWeek1() {
        System.out.println("view upcoming week's football matches");
        return MongoDBFootballMatchData.retrieveDocumentsWithinTimeFrameAndReturn("UpcomingWeek1");
    }

    // Ignore for now
    @RequestMapping(value = "/viewFootballMatchesUpcomingWeek2")
    public FootballMatchList viewFootballMatchesUpcomingWeek2() {
        System.out.println("view second upcoming week's football matches");
        return MongoDBFootballMatchData.retrieveDocumentsWithinTimeFrameAndReturn("UpcomingWeek2");
    }

    // Ignore for now
    @RequestMapping(value = "/viewFootballMatchesUpcomingWeek3")
    public FootballMatchList viewFootballMatchesUpcomingWeek3() {
        System.out.println("view third upcoming week's football matches");
        return MongoDBFootballMatchData.retrieveDocumentsWithinTimeFrameAndReturn("UpcomingWeek3");
    }

    @RequestMapping(value = "/viewCelestialBodies")
    public ArrayList<CelestialBody> viewCelestialBodies() {
        System.out.println("view celestial bodies");
        return MongoDBCelestialBodyData.retrieveCollection();
    }

    // I don't think that this will be used outside the updater class, but I threw it in just in case.
    @RequestMapping(value = "/viewOswegoWeather")
    public DailyForecast viewOswegoWeather() {
        System.out.println("view today's oswego weather forecast");
        return MongoDBWeatherData.retrieveCollection();
    }
    //******************************************************************************************************************

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

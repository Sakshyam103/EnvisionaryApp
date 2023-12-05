package backend;

import backend.EntertainmentPredictions.buildEntertainmentPrediction;
import backend.FootballMatchPredictions.FootballMatchList;
import backend.FootballMatchPredictions.MongoDBFootballMatchData;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
import backend.ResolvedPredictions.ResolvedPrediction;
import backend.UserInfo.MongoDBEnvisionaryUsers;
import backend.UserInfo.User;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
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

    @RequestMapping(value = "/viewNotification")
    public ArrayList<String> viewNotification() {
        System.out.println("view notification");
        ArrayList<String> a = new ArrayList<>();
        a.add("rice");
        a.add("ball");
        return a;
    }

    @RequestMapping(value = "/viewMatches")
    public FootballMatchList viewMatches() {
        System.out.println("view matches");
        FootballMatchList a = MongoDBFootballMatchData.retrieveDocumentsWithinTimeFrameAndReturn("UpcomingWeek1");
        return a;
    }

    @RequestMapping(value = "/movies")
    public @ResponseBody String getUserMovie(@RequestBody(required = false) String data) throws JSONException {
        System.out.println(data);
        StringReader stringReader = new StringReader(data);
        JsonReaderFactory factory = Json.createReaderFactory(null);
        JsonReader reader = factory.createReader(stringReader);
        JsonObject object = reader.readObject();
        buildEntertainmentPrediction.buildMoviePrediction(object, userId);
        System.out.println("completed");
        return "Movie Prediction Saved";
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

package backend;

//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
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

import java.util.ArrayList;

import org.json.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
// @RequestMapping("/whee")
public class Controller {

    // @GetMapping("/example")
    // public ResponseEntity<String> exampleEndpoint() {
    // return ResponseEntity.ok("Hello");

    // }
    // @Autowired
    // private SignInRequest signInRequest;

    @RequestMapping(value = "/example")
    public @ResponseBody String handleSignIn(@RequestBody(required = false) String idString) {
        System.out.println("---test");
        SignInRequest signInRequest1 = new SignInRequest();
        signInRequest1.setIdToken(idString);
        System.out.println(signInRequest1.getIdToken());
        parseId(signInRequest1.getIdToken());
        return "Login successful";

    }

    @RequestMapping(value = "/viewPrediction")
    public ArrayList<String> viewPrediction() {
        System.out.println("view prediction");
        ArrayList<String> a = new ArrayList<>();
        a.add("apple");
        a.add("ball");
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

    public void parseId(String id) {
        JSONObject jsonObject = new JSONObject(id);
        JSONObject idString = (JSONObject) jsonObject.get("idString");
        // System.out.println(idString);
        String email = (String) idString.get("email");
        System.out.println("email: " + email);
        String sub = (String) idString.get("sub");
        System.out.println(sub);
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

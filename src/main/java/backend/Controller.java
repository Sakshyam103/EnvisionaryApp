package backend;

//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
//@RequestMapping("/whee")
public class Controller {

    // @GetMapping("/example")
    // public ResponseEntity<String> exampleEndpoint() {
    //     return ResponseEntity.ok("Hello");

    // }
    // @Autowired
    // private SignInRequest signInRequest;


    @RequestMapping(value = "/example")
    public @ResponseBody String handleSignIn(@RequestBody(required = false) String idString) {
     System.out.println("---test");
     SignInRequest signInRequest1 = new SignInRequest();
     signInRequest1.setIdToken(idString); 
     System.out.println(signInRequest1.getIdToken());
    return "hello World";

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

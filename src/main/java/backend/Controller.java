package backend;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/whee")
public class Controller {

    @GetMapping("/example")
    public ResponseEntity<String> exampleEndpoint() {
        return ResponseEntity.ok("Hello");

    }

    // @GetMapping("/example")
    // public String handleSignIn(@RequestBody SignInRequest signInRequest) {

    // String idToken = signInRequest.getIdToken();

    // return "hello world";

    // }

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

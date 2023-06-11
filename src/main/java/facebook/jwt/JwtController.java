package facebook.jwt;

import facebook.account.Account;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
//@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000/", maxAge = 3600)
public class JwtController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/authenticate")
    public JwtResponse authenticate(@RequestBody JwtRequest jwtRequest) throws Exception {
        log.info("User {} tries to log in.", jwtRequest.getLogin());
        return authenticationService.authenticate(jwtRequest);
    }

    @PostMapping("/logout")
    public void logout(Account account) throws Exception {
        log.info("User {} tries to log out.", account.getLogin());
        //authenticationService.logout(account.getLogin());
    }
}

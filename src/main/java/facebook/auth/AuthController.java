package facebook.auth;

import facebook.confirmation.AccountAuthentication;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/authentication")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody LoginAndPassword loginAndPassword) {
        log.info("User {} attempts to log in", loginAndPassword);
        return authService.login(loginAndPassword.getLogin(),loginAndPassword.getPassword());
    }

    @PostMapping("/logout")
    public void logout(AccountAuthentication accountAuthentication) {
        log.info("User {} attempts to log out.", accountAuthentication.getName());
        authService.logout(accountAuthentication.getToken());
    }
}

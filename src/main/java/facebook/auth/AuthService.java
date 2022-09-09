package facebook.auth;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Map;

@Service
@AllArgsConstructor
@Slf4j
public class AuthService {

    private final AuthRepository authRepository;
    private final AuthenticationManager authenticationManager;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    //private final AccountRepository accountRepository;




    // LOGIN
    public Map<String, Object> login(String login, String password) {
      /*  Account account = accountRepository.findByLogin(login);
        if(Objects.isNull(account)){
            throw new BadCredentialsException("Account is a null.... :( ");
        }

        if (Strings.isEmpty(account.getFirstName())) {
            throw new BadCredentialsException("Provided credentials are invalid");
        }
        log.info("Login step 1: OK");*/


        if (Strings.isEmpty(login) || Strings.isEmpty(password)) {
            throw new BadCredentialsException("Provided credentials" + login + " + " + password + " are invalid");
        }
        log.info("Login step 1: OK");
        final UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(login, password);
        final Authentication authentication = authenticationManager.authenticate(authToken);
        final String sessionLogin = authentication.getName();
        final String sessionToken = bCryptPasswordEncoder.encode(sessionLogin);
        log.info("Login step 2: OK");

        Auth auth = Auth.builder()
                .login(sessionLogin)
                .token(sessionToken)
                .validTime(LocalDateTime.now().plusDays(1))
                .build();

        authRepository.save(auth);
        log.info("Login step 3: OK");

        return Map.of(
                "login", login,
                "token", sessionToken);
    }

    // LOGOUT
    @Transactional
    public void logout(String token) {
        authRepository.deleteAllByToken(token);
    }

}

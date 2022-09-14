package facebook.jwt;

import facebook.account.Account;
import facebook.account.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    @Autowired
    private JWTUtility jwtUtility;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private AccountRepository accountRepository;

    public JwtResponse authenticate(JwtRequest jwtRequest) throws Exception {
        log.info("Authentication: step 1");
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(jwtRequest.getLogin(), jwtRequest.getPassword());
            authenticationManager.authenticate(authenticationToken);
        } catch (BadCredentialsException e) {
            log.info("Login failed for user: {}, bad credentials", jwtRequest.getLogin());
            throw new Exception("Invalid credentials", e);
        }
        log.info("Authentication: step 2");
        Account account = accountRepository.findByLogin(jwtRequest.getLogin());
        final UserDetails userDetails = userService.loadUserByUsername(jwtRequest.getLogin());
        log.info("Authentication: step 3");
        final String token = jwtUtility.generateToken(userDetails);
        log.info("Authentication successes.");
        return new JwtResponse(account.getId(), token);
    }

    // LOGOUT
    @Transactional
    public void logout(String token) {
        //accountRepository.deleteAllByToken(token);
    }


}

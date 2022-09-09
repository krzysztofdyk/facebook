package facebook.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @Autowired
    private JWTUtility jwtUtility;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String home(){
        return "Welcome to Daily Code Buffer!!";
    }

    @PostMapping("/authenticate")
    public JwtResponse authenticate(@RequestBody JwtRequest jwtRequest) throws Exception {
        try {
            UsernamePasswordAuthenticationToken authenticationToken =  new UsernamePasswordAuthenticationToken(jwtRequest.getLogin(), jwtRequest.getPassword());
            authenticationManager.authenticate(authenticationToken);
        } catch (BadCredentialsException e){
            throw new Exception("Invalid credentials", e);
        }
        final UserDetails userDetails = userService.loadUserByUsername(jwtRequest.getLogin());
        final String token = jwtUtility.generateToken(userDetails);
        return new JwtResponse(token);
    }
}

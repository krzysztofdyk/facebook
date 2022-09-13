package facebook.jwt;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class JwtRequest {

    private String login;
    private String password;

}

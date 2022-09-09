package facebook.confirmation;

import facebook.account.Account;
import lombok.Builder;
import lombok.ToString;
import lombok.Value;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@ToString
@Value
public class AccountAuthentication extends AbstractAuthenticationToken {

    String token;
    String username;
    Account account;
    Long accountId;
    boolean isAuthenticated;

    @Builder
    public AccountAuthentication(Collection<? extends GrantedAuthority> authorities, String token, String username, Account account, Long accountId, boolean isAuthenticated) {
        super(authorities);
        this.token = token;
        this.username = username;
        this.account = account;
        this.accountId = accountId;
        this.isAuthenticated = isAuthenticated;
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    @Override
    public Account getDetails() {
        return account;
    }

    @Override
    public Object getPrincipal() {
        return accountId;
    }

    @Override
    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
    }

    @Override
    public String getName() {
        return username;
    }

}
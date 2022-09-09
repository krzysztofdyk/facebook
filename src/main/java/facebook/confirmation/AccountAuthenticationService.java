package facebook.confirmation;

import facebook.account.Account;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AccountAuthenticationService {

    public Account getAccountFromContext() {
        AccountAuthentication authentication = getAccountAuthenticationFromContext();
        return authentication.getAccount();
    }

    public AccountAuthentication getAccountAuthenticationFromContext() {
        return (AccountAuthentication) SecurityContextHolder.getContext().getAuthentication();
    }
}

package facebook.jwt;

import facebook.account.Account;
import facebook.account.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {

        Account account = accountRepository.findByLogin(login);
        if (Objects.isNull(account)) {
            throw new UsernameNotFoundException("Account is null :(");
        }
        return account;
        //return new User(sessionLogin, sessionPassword, new ArrayList<>());
        //return new User("user_kris","password_1234",new ArrayList<>());
    }
}

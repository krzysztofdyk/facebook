package facebook.jwt;

import facebook.account.Account;
import facebook.account.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        //Account account = accountRepository.findByLogin(login);
        //String sessionLogin = account.getLogin();
        //String sessionPassword = account.getPassword();
        //logic to get the user from database
        return new User("user_kris","password_1234",new ArrayList<>());
    }
}

package facebook.admin;

import facebook.account.*;
import lombok.Data;
import org.springframework.stereotype.Service;

@Service
@Data
public class AdminService {

    private final AccountService accountService;
    private final AccountMapper accountMapper;

    public void updateRole(Long id, AccountRole accountRole){
        Account account = accountService.getAccountEntity(id);
        account.setAccountRole(null);
        account.setAccountRole(accountRole);
    }

}

package facebook.admin;

import facebook.enums.KeyStatus;
import facebook.enums.AccountRole;
import facebook.account.*;
import lombok.Data;
import org.springframework.stereotype.Service;

@Service
@Data
public class AdminService {

    private final AccountService accountService;
    private final AccountMapper accountMapper;
    private final AccountRepository accountRepository;

    public void updateRole(Long id, AccountRole accountRole){
        Account account = accountService.getAccountEntity(id);
        account.setAccountRole(null);
        account.setAccountRole(accountRole);
    }

    public void changeActiveStatus(Long id){
        Account account = accountService.getAccountEntity(id);
        if(account.getKeyStatus().equals(KeyStatus.Active)){
            account.setKeyStatus(KeyStatus.Inactive);
        }else{
            account.setKeyStatus(KeyStatus.Active);
        }
        accountRepository.save(account);
    }
}

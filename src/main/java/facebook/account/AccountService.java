package facebook.account;


import facebook.enums.KeyStatus;
import facebook.enums.AccountRole;
import facebook.enums.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;


@AllArgsConstructor
@Service
@Slf4j
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    public void createAccount(AccountDto accountDto){
        Account account = createAccountEntityFromDto(accountDto);
        accountRepository.save(account);
        log.info("Account was created.");
    }

    private Account createAccountEntityFromDto(AccountDto accountDto) {
        return Account.builder()
                .keyStatus(KeyStatus.Active)
                .firstName(accountDto.getFirstName())
                .lastName(accountDto.getLastName())
                .email(accountDto.getEmail())
                .city(accountDto.getCity())
                .accountStatus(AccountStatus.Candidate)
                .accountRole(AccountRole.Employee)
                .available(true)
                .build();
    }

    public List<Account> getAllAccounts(){
        List<Account> accounts = accountRepository.findAll();
        List<Account> activeAccounts = accounts.stream()
                                                .filter(account -> account.getKeyStatus().equals(KeyStatus.Active))
                                                .collect(Collectors.toList());
        log.info("All active accounts were delivered.");
        return activeAccounts;
    }

    public Account getAccount(Long id){
        Account account = accountRepository.findById(id).orElseThrow(()->new EntityNotFoundException(String.format("Account ID: %s not found",id)));
        if (id.equals(account.getId())) {
            log.info("Account was delivered.");
        }
        return account;
    }

    public Account getAccountEntity(Long id){
        log.info("Account entity was delivered.");
        return accountRepository.getById(id);
    }

    public void updateAccount(Long id, AccountDto accountDto){
        Account account = accountRepository.getById(id);
        account.setFirstName(accountDto.getFirstName());
        account.setLastName(accountDto.getLastName());
        account.setEmail(accountDto.getEmail());
        account.setCity(accountDto.getCity());
        log.info("Account with ID: {} was updated.",id);
    }

    public void deleteAccount(Long id){
        Account account = accountRepository.getById(id);
        accountRepository.delete(account);
        log.info("Account with ID: {} was deleted.", id);
    }
}

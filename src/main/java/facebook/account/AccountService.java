package facebook.account;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


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
        List<AccountRole> accountRoles = new ArrayList<>();
        accountRoles.add(AccountRole.Employee);
        return Account.builder()
                .firstName(accountDto.getFirstName())
                .lastName(accountDto.getLastName())
                .email(accountDto.getEmail())
                .city(accountDto.getCity())
                .accountStatus(AccountStatus.Candidate)
                .accountRoles(accountRoles)
                .available(true)
                .build();
    }

    public List<AccountDto> getAllAccounts(){
        List<Account> accounts = accountRepository.findAll();
        log.info("All accounts were delivered.");
        return accountMapper.mapToAccountDtoList(accounts);

    }

    public AccountDto getAccountDto(Long id){
        Account account = accountRepository.getById(id);
        log.info("Account was delivered.");
        return accountMapper.mapToAccountDto(account);
    }

    public Account getAccount(Long id){
        log.info("Account entity was delivered.");
        return accountRepository.getById(id);
    }

    public void updateAccount(Long id, AccountDto accountDto){
        Account account = accountRepository.getById(id);
        account.setFirstName(account.getFirstName());
        account.setLastName(accountDto.getLastName());
        account.setEmail(account.getEmail());
        account.setCity(account.getCity());
        log.info("Account was updated.");
    }

    public void deleteAccount(Long id){
        Account account = accountRepository.getById(id);
        accountRepository.delete(account);
        log.info("Account was deleted.");
    }


}

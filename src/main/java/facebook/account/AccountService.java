package facebook.account;

import facebook.email.EmailService;
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
    private final EmailService emailService;

    public void createAccount(AccountDto accountDto){
        Account account = createAccountEntityFromDto(accountDto);
        accountRepository.save(account);
     /*   try {
            emailService.sendEmail("dyk.krzysztof@gmail.com", "Account for " + accountDto.getFirstName() + " " + accountDto.getLastName() + " was created.", "Your account was created.");
        } catch(Exception e){
            throw new RuntimeException("There was some problem with sending an email.");
        }*/
        log.info("Account with ID: {} was created.", account.getId());
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
                .balance(100L)
                .build();
    }

    public List<AccountDtoResponse> getAllAccounts(Boolean isActive) {
        List<Account> accounts = accountRepository.findAll();
        if (isActive.equals(true)) {
            log.info("Active accounts were shown.");
            List<Account> activeAccounts = accounts.stream()
                    .filter(account -> account.getKeyStatus().equals(KeyStatus.Active))
                    .collect(Collectors.toList());
            return accountMapper.mapToAccountDtoResponseList(activeAccounts);
        } else if (isActive == null || isActive.equals(false)) {
            log.info("Active and inactive accounts were shown.");
            return accountMapper.mapToAccountDtoResponseList(accounts);
        }
        return null;
    }

    private Boolean isActive(Account account){
        return account.getKeyStatus().equals(KeyStatus.Active);
    }

    public AccountDtoResponse getAccount(Long id){
        Account account = accountRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException(String.format("Account ID: %s was not found",id)));
        if (id.equals(account.getId())) {
            log.info("Account with ID: {} was shown.", id);
        }
        return accountMapper.mapToAccountDtoResponse(account);
    }

    public Account getAccountEntity(Long accountId){
        return accountRepository.getById(accountId);
    }

    public void updateAccount(Long accountId, AccountDto accountDto){
        Account account = accountRepository.getById(accountId);
        account.setFirstName(accountDto.getFirstName());
        account.setLastName(accountDto.getLastName());
        account.setEmail(accountDto.getEmail());
        account.setCity(accountDto.getCity());
        accountRepository.save(account);
        log.info("Account with ID: {} was updated.",accountId);
    }

    public void deleteAccount(Long accountId){
        Account account = accountRepository.getById(accountId);
        accountRepository.delete(account);
        log.info("Account with ID: {} was deleted.", accountId);
    }
}

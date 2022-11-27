package facebook.account;

import facebook.email.EmailService;
import facebook.enums.AccountRole;
import facebook.enums.AccountStatus;
import facebook.enums.KeyStatus;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


@AllArgsConstructor
@Service
@Slf4j
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final EmailService emailService;

    public void createAccount(AccountDto accountDto) {
        Account account = createAccountFromDto(accountDto);
        accountRepository.save(account);
     /*   try {
            emailService.sendEmail("dyk.krzysztof@gmail.com", "Account for " + accountDto.getFirstName() + " " + accountDto.getLastName() + " was created.", "Your account was created.");
        } catch(Exception e){
            throw new RuntimeException("There was some problem with sending an email.");
        }*/
        log.info("Account with ID: {} was created.", account.getId());
    }

    private Account createAccountFromDto(AccountDto accountDto) {
        validateEmail(accountDto.getEmail());
        return Account.builder()
                .login(accountDto.getLogin())
                .password(accountDto.getPassword())
                .keyStatus(KeyStatus.Active)
                .firstName(accountDto.getFirstName())
                .lastName(accountDto.getLastName())
                .email(accountDto.getEmail())
                .city(accountDto.getCity())
                .accountStatus(AccountStatus.Candidate)
                .accountRole(AccountRole.Technican)
                .available(true)
                .balance(100L)
                .build();
    }

    public void validateEmail(String email) {
        List<Account> accounts = accountRepository.findAll();
        List<String> emails = accounts.stream().map(Account::getEmail).collect(Collectors.toList());
        for (String existingEmail : emails) {
            if (email.equals(existingEmail)) {
                throw new IllegalArgumentException("Email validation: email with name already exists in database.");
            }
        }
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Email validation: failed.");
        }
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

    private Boolean isActive(Account account) {
        return account.getKeyStatus().equals(KeyStatus.Active);
    }

    public AccountDtoResponse getAccount(String login) {
        Account account = accountRepository.findByLogin(login)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Account ID: %s was not found", login)));
        if (login.equals(account.getLogin())) {
            log.info("Account with ID: {} was shown.", login);
        }
        return accountMapper.mapToAccountDtoResponse(account);
    }

    public Account getAccountEntity(Long accountId) {
        return accountRepository.getById(accountId);
    }

    public void updateAccount(Long accountId, AccountDto accountDto) {
        Account account = accountRepository.getById(accountId);
        account.setFirstName(accountDto.getFirstName());
        account.setLastName(accountDto.getLastName());
        account.setEmail(accountDto.getEmail());
        account.setCity(accountDto.getCity());
        accountRepository.save(account);
        log.info("Account with ID: {} was updated.", accountId);
    }

    public void deleteAccount(Long accountId) {
        Account account = accountRepository.getById(accountId);
        //accountRepository.delete(account);
        account.setKeyStatus(KeyStatus.Inactive);
        log.info("Account with ID: {} was inactivated.", accountId);
    }
}

package facebook.account;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/accounts")
@CrossOrigin(origins = "http://localhost:8080", maxAge = 3600)
public class AccountController {

    private final AccountService accountService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<AccountDtoResponse> getAllAccounts(@RequestParam(name = "isActive", required = false) Boolean isActive) {
        return accountService.getAllAccounts(isActive);
    }

    @GetMapping("/{accountLogin}")
    @ResponseStatus(HttpStatus.OK)
    public AccountDtoResponse getAccount(@PathVariable(name = "accountLogin") String accountLogin) {
        return accountService.getAccount(accountLogin);
    }

    @PostMapping
    public HttpStatus createAccount(@RequestBody AccountDto accountDto) {
        accountService.createAccount(accountDto);
        return HttpStatus.CREATED;
    }

    @PutMapping("/{accountId}")
    public HttpStatus updateAccount(@PathVariable Long accountId, @RequestBody AccountDto accountDto) {
        accountService.updateAccount(accountId, accountDto);
        return HttpStatus.ACCEPTED;
    }

    @DeleteMapping("/{accountId}")
    public HttpStatus deleteAccount(@PathVariable Long accountId) {
        accountService.deleteAccount(accountId);
        return HttpStatus.NO_CONTENT;
    }
}

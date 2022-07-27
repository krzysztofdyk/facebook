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
public class AccountController {

    private final AccountService accountService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Account> getAllAccounts(){
        return accountService.getAllAccounts();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Account getAccount(@PathVariable(name = "id") Long id){
        return accountService.getAccount(id);
    }

    @PostMapping
    public HttpStatus createAccount(@RequestBody AccountDto accountDto){
        accountService.createAccount(accountDto);
        return HttpStatus.CREATED;
    }

    @PutMapping("/{id}")
    public HttpStatus updateAccount(@PathVariable Long id, @RequestBody AccountDto accountDto){
        accountService.updateAccount(id,accountDto);
        return HttpStatus.ACCEPTED;
    }

    @DeleteMapping("/{id}")
    public HttpStatus deleteAccount (@PathVariable Long id){
        accountService.deleteAccount(id);
        return HttpStatus.NO_CONTENT;
    }
}

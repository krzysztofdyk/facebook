package facebook.account;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/account")
public class AccountController {

    private final AccountService accountService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<AccountDto> getAllAccounts(){
        return accountService.getAllAccounts();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AccountDto getAccount(@PathVariable Long id){
        return accountService.getAccountDto(id);
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

package facebook.admin;

import facebook.enums.AccountRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/accounts/admin")
public class AdminController {

    private final AdminService adminService;

    @PutMapping("/{accountId}/role")
    @ResponseStatus(HttpStatus.OK)
    public void updateRole(@PathVariable Long accountId, @RequestBody AccountRole accountRole){
        adminService.updateRole(accountId, accountRole);
    }

    @PutMapping("{accountId}/status")
    @ResponseStatus(HttpStatus.OK)
    public void changeKeyStatus(@PathVariable Long accountId){
        adminService.changeActiveStatus(accountId);
    }

    @PutMapping("/{accountId}/balance")
    @ResponseStatus(HttpStatus.OK)
    public void addMoney(@PathVariable Long accountId, @RequestBody MoneyDto moneyDto){
        adminService.addMoney(accountId,moneyDto);
    }
}

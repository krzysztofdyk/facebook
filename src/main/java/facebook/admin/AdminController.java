package facebook.admin;

import facebook.account.AccountRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/accounts")
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void addRole(@PathVariable Long id, AccountRole accountRole){
        adminService.updateRole(id, accountRole);
    }

}

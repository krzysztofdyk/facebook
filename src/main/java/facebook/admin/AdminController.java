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

    @PostMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateRole(@PathVariable Long id, @RequestBody AccountRole accountRole){
        adminService.updateRole(id, accountRole);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void changeKeyStatus(@PathVariable Long id){
        adminService.changeActiveStatus(id);
    }

}

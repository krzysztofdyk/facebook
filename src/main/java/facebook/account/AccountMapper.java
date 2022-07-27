package facebook.account;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Component
public class AccountMapper {

    public List<AccountDto> mapToAccountDtoList(List<Account> accounts){
        return accounts.stream().map(this::mapToAccountDto).collect(Collectors.toList());
    }

    public AccountDto mapToAccountDto(Account account){
        return AccountDto.builder()
                .firstName(account.getFirstName())
                .lastName(account.getLastName())
                .email(account.getEmail())
                .city(account.getCity())
                .build();
    }

}

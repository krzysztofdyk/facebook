package facebook.account;

import facebook.transfer.Transfer;
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

    public List<AccountDtoResponse> mapToAccountDtoResponseList(List<Account> accounts){
        return accounts.stream().map(this::mapToAccountDtoResponse).collect(Collectors.toList());
    }


    public AccountDtoResponse mapToAccountDtoResponse(Account account){
        List<Transfer> transferList = account.getTransferList();
        List<Long> idList = transferList.stream().map(Transfer::getId).collect(Collectors.toList());
        Long checkImageId = account.getImage() == null ? null : account.getImage().getId();
        return AccountDtoResponse.builder()
                .id(account.getId())
                .imageId(checkImageId)
                .keyStatus(account.getKeyStatus())
                .firstName(account.getFirstName())
                .lastName(account.getLastName())
                .email(account.getEmail())
                .city(account.getCity())
                .accountStatus(account.getAccountStatus())
                .accountRole(account.getAccountRole())
                .available(account.getAvailable())
                .balance(account.getBalance())
                .transferIdList(idList)
                .build();
    }

}

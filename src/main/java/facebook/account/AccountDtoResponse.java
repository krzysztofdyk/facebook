package facebook.account;

import com.sun.istack.NotNull;
import facebook.enums.AccountRole;
import facebook.enums.AccountStatus;
import facebook.enums.KeyStatus;
import facebook.transfer.Transfer;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.util.List;


@Data
@Builder
public class AccountDtoResponse {
    private Long id;
    private Long imageId;
    @Enumerated(EnumType.STRING)
    private KeyStatus keyStatus;
    private String firstName;
    private String lastName;
    private String email;
    private String city;
    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus;
    @Enumerated(EnumType.STRING)
    private AccountRole accountRole;
    private Boolean available;
    private Long balance;
    private List<Long> transferIdList;
}

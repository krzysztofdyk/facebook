package facebook.account;

import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class AccountDto {

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    private String email;
    private String city;
}

package facebook.account;

import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Component;

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

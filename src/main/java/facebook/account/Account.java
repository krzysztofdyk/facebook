package facebook.account;

import com.sun.istack.NotNull;
import facebook.enums.KeyStatus;
import facebook.enums.AccountRole;
import facebook.enums.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Builder
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated()
    @NotNull
    private KeyStatus keyStatus;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    private String email;
    private String city;

    @Enumerated()
    @NotNull
    private AccountStatus accountStatus;

    @Enumerated()
    @NotNull
    private AccountRole accountRole;

    @NotNull
    private Boolean available;

    private Long balance;

}

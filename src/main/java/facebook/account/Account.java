package facebook.account;

import com.sun.istack.NotNull;
import facebook.enums.AccountRole;
import facebook.enums.AccountStatus;
import facebook.enums.KeyStatus;
import facebook.image.Image;
import facebook.transfer.Transfer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Builder
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String login;

    private String password;

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

    @OneToMany(mappedBy = "fromAccount")
    private List<Transfer> transferList;

    @OneToOne(mappedBy = "account")
    private Image image;

}

package facebook.transfer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import facebook.account.Account;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Builder
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transfer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(scale = 4)
    private Long amount;

    private String currency;

    @JsonIgnore
    @ManyToOne
    @JoinColumn (name = "from_account_id")
    private Account fromAccount;

    @OneToOne
    private Account toAccount;

    private LocalDate localDate;

    private LocalTime localTime;
}

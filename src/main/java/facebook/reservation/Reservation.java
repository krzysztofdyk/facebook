package facebook.reservation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import facebook.account.Account;
import facebook.house.House;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Builder
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private LocalDate startDate;
    private LocalDate endDate;
    private Long totalPrice;

    @OneToOne
    @JoinColumn (name = "house_id")
    private House house;

    @JsonIgnore
    @OneToOne
    @JoinColumn (name = "account_id")
    private Account account;
}

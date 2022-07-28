package facebook.reservation;

import com.sun.istack.NotNull;
import facebook.account.Account;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ReservationDto {
    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;
}

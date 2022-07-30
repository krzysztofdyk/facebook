package facebook.transfer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.time.LocalTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class TransferDtoResponse {
    private Long id;
    private String title;
    private Long amount;
    private String currency;
    private Long fromAccountId;
    private Long toAccountId;
    private LocalDate localDate;
    private LocalTime localTime;
}



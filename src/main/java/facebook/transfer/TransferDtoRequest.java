package facebook.transfer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TransferDtoRequest {
    private String title;
    private Long amount;
    private String currency;
    private Long toAccountId;
}

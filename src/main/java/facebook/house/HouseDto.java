package facebook.house;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class HouseDto {

    private String name;
    private Long unitPrice;
    private Long area;
    private String description;

}

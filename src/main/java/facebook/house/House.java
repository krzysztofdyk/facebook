package facebook.house;

import com.fasterxml.jackson.annotation.JsonIgnore;
import facebook.reservation.Reservation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class House {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private Long unitPrice;
    private Long area;
    private String description;

    @JsonIgnore
    @OneToOne (mappedBy = "house")
    private Reservation reservation;
}

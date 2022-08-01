package facebook.image;

import com.fasterxml.jackson.annotation.JsonIgnore;
import facebook.account.Account;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;

@Builder
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Transactional
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name="account_id")
    private Account account;

    @Lob
    //@Column(name = "file_data", columnDefinition="blob")
    @JsonIgnore
    private byte[] imageByte;

    private String imageName;
}

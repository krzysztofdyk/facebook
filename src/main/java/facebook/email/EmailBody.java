package facebook.email;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.mail.Session;

@Data // Lombok creates constructor, getters, setters
@Component
public class EmailBody {
   private Session session;
   private String sender;
   private String receiver;
   private String title;
   private String body;
}


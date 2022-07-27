package facebook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan
public class FacebookApplication {

	public static void main(String[] args) {
		SpringApplication.run(FacebookApplication.class, args);
	}

}

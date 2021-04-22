package fx.fx;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * FxApplication.java
 *
 * The application's main class. Uses SpringApplication.run to launch the application.
 * Can be ran by typing " ./mvnw spring-boot:run  " in the console (make sure you're in the fx directory).
 * @author Yosif Gorelyov
 * @date 20/04/2021
 */

@SpringBootApplication
public class FxApplication {
	public static void main(String[] args) {
		SpringApplication.run(FxApplication.class, args);
	}

}

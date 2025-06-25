package brifu.puckdle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PuckdleApplication {

	public static void main(String[] args) {
		System.out.println("Puckdle Application has started successfully!");
		SpringApplication.run(PuckdleApplication.class, args);
	}
}

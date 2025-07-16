package brifu.puckdle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class PuckdleApplication {

	public static void main(String[] args) {
		System.out.println("Puckdle Application has started successfully!");
		SpringApplication.run(PuckdleApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}

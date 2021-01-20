package io.ioak.emailflow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class EmailflowServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmailflowServiceApplication.class, args);
	}

}

package de.interhyp.acceptancetest.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class AcceptanceTestExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(AcceptanceTestExampleApplication.class, args);
	}

}

package de.interhyp.acceptancetestdemo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.support.serializer.JsonDeserializer;

@SpringBootApplication
@EnableKafka
public class AcceptanceTestDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(AcceptanceTestDemoApplication.class, args);
	}

}

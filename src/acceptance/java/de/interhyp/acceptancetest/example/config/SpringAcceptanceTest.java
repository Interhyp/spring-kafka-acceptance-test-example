package de.interhyp.acceptancetest.example.config;

import de.interhyp.acceptancetest.example.AcceptanceTestExampleApplication;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.jupiter.api.Tag;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.ActiveProfiles;

@CucumberContextConfiguration
@SpringBootTest(classes = {AcceptanceTestExampleApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles({"kafka-embedded"})
@Tag("AcceptanceTest")
@EmbeddedKafka(partitions = 1, topics = {"user-example-topic"}, controlledShutdown = true,
    brokerProperties = {"log.dir=build/tmp/kafka-data/${spring.application.name}"})
public class SpringAcceptanceTest {

}

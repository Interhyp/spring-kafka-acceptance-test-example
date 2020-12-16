package de.interhyp.acceptancetestdemo.config;

import de.interhyp.acceptancetestdemo.AcceptanceTestDemoApplication;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.jupiter.api.Tag;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@CucumberContextConfiguration
@SpringBootTest(classes = {AcceptanceTestDemoApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles({"kafka-embedded"})
@Tag("AcceptanceTest")
@EmbeddedKafka(partitions = 1, topics = {"user-demo-topic"}, controlledShutdown = true,
    brokerProperties = {"log.dir=build/tmp/kafka-data/${spring.application.name}"})
public class SpringAcceptanceTest {

}

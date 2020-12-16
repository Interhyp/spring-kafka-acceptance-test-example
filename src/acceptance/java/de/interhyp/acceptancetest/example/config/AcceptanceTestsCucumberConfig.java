package de.interhyp.acceptancetest.example.config;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

/**
 * Runner for Cucumber acceptance tests.
 */
@RunWith(Cucumber.class)
@CucumberOptions(features = {"classpath:features/example"},
    glue = {"de.interhyp.acceptancetest.example.library",
        "de.interhyp.acceptancetest.example.config",
        "de.interhyp.acceptancetest.example.steps"},
    tags = "not @Pending")
public class AcceptanceTestsCucumberConfig {
}


package de.interhyp.acceptancetestdemo.config;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

/**
 * Runner for Cucumber acceptance tests.
 */
@RunWith(Cucumber.class)
@CucumberOptions(features = {"classpath:features/demo"},
    glue = {"de.interhyp.acceptancetestdemo.library",
        "de.interhyp.acceptancetestdemo.config",
        "de.interhyp.acceptancetestdemo.steps"},
    tags = "not @Pending")
public class AcceptanceTestsCucumberConfig {
}


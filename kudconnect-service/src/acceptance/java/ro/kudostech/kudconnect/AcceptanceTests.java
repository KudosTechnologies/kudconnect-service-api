package ro.kudostech.kudconnect;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = "classpath:features",
    glue = "ro.kudostech.kudconnect",
    plugin = {
      "pretty",
      "html:build/reports/cucumber.html",
      "json:build/reports/cucumber-json/cucumber.json"
    })
public class AcceptanceTests {}

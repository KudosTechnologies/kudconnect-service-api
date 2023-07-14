package ro.kudostech.kudconnect;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
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
public class AcceptanceTests {

}

package ro.kudostech.kudconnect.step;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.jose4j.lang.JoseException;
import ro.kudostech.kudconnect.AutowiredIgnoreWarning;
import ro.kudostech.kudconnect.api.client.Candidate;

public class CrudCandidateSteps {

  @AutowiredIgnoreWarning private TestContext testContext;



  @Given("the system has no existing candidate with email {string}")
  public void systemHasNoCandidateWithEmail(String email) throws JoseException {
    assertThat(
            testContext.getCandidatesApi().listCandidates().stream()
                .anyMatch(candidate -> candidate.getEmail().equals(email)))
        .isFalse();
  }

  @When("I create a candidate with email {string}")
  public void createCandidate(String email) throws JoseException {
    Candidate candidate = new Candidate().email(email).firstName("John");
    testContext.getCandidatesApi().createCandidate(candidate);
  }

  @Then("the system should have a candidate with email {string}")
  public void systemShouldHaveCandidate(String email) throws JoseException {
    assertThat(
            testContext.getCandidatesApi().listCandidates().stream()
                .anyMatch(candidate -> candidate.getEmail().equals(email)))
        .isTrue();
  }
}

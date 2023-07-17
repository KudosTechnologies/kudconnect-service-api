package ro.kudostech.kudconnect.step;

import static org.assertj.core.api.Assertions.assertThat;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import ro.kudostech.kudconnect.AutowiredIgnoreWarning;
import ro.kudostech.kudconnect.api.client.Candidate;
import ro.kudostech.kudconnect.configuration.TestContext;

public class CrudCandidateSteps {

  @AutowiredIgnoreWarning private TestContext testContext;
  
  

  @Given("the system has no existing candidate with email {string}")
  public void systemHasNoCandidateWithEmail(String email) {
    assertThat(
            testContext.getCandidatesApiWithAdminAccess().listCandidates().stream()
                .anyMatch(candidate -> candidate.getEmail().equals(email)))
        .isFalse();
  }

  @When("I create a candidate with email {string}")
  public void createCandidate(String email) {
    Candidate candidate = new Candidate().email(email).firstName("John");
    testContext.getCandidatesApiWithAdminAccess().createCandidate(candidate);
  }

  @Then("the system should have a candidate with email {string}")
  public void systemShouldHaveCandidate(String email) {
    assertThat(
            testContext.getCandidatesApiWithAdminAccess().listCandidates().stream()
                .anyMatch(candidate -> candidate.getEmail().equals(email)))
        .isTrue();
  }
}

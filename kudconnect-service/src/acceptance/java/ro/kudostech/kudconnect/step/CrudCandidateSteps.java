package ro.kudostech.kudconnect.step;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CrudCandidateSteps {
  @Given("the system has no existing candidate with email {string}")
  public void systemHasNoCandidateWithEmail(String email) {
    System.out.printf("");
  }

    @When("I create a candidate with email {string}")
    public void createCandidate(String email) {
        System.out.printf("");
    }

    @Then("the system should have a candidate with email {string}")
    public void systemShouldHaveCandidate(String email) {
    System.out.println("");
    }
}

Feature: Candidates can be created

  Scenario: Create new candidates
    Given the system has no existing candidate with email "john.doe@example.com"
    When I create a candidate with email "john.doe@example.com"
    Then the system should have a candidate with email "john.doe@example.com"
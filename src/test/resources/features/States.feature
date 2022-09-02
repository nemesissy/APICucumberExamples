Feature: States

  Scenario: Count States
    Given The API is available
    When I get the states
    Then I should have <52> states

  Scenario: Verify States JSON
    Given The API is available
    When I get the states
    Then It should match the structure in <states.json>

  Scenario Outline: Verify States Details
    Given The API is available
    When I get the states
    Then Then verify the "<name>", "<key>", "<id>", "<fluff>"
    Examples:
      | name        | key                       | id        | fluff
      | Texas       | Geography-State-04000US48 | 04000US48   | 1
      | Colorado    | Geography-State-04000US08 | 04000US08   | 2
      | Mississippi | Geography-State-04000US28 | 04000US28   | 3

  Scenario: Verify States Details using standard Hamcrest
    Given The API is available
    When I get a state "Texas"
    Then Verify some attributes using different hamcrest matchers

  Scenario: Verify States Details using custom Hamcrest
    Given The API is available
    When I get a state "Texas"
    Then Verify some attributes using custom hamcrest matchers
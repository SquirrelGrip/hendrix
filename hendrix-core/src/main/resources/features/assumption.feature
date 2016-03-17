Feature: Assumption Feature

  Scenario: Assume PASS then PASS
    Given assumption true
    Then pass

  Scenario: Assume FAIL then PASS
    Given assumption false
    Then pass

  Scenario: Assume PASS then FAIL
    Given assumption true
    Then fail

  Scenario: Assume FAIL then FAIL
    Given assumption false
    Then fail


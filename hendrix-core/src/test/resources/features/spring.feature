Feature: Cukes injection

  Scenario: annotated bean injected
    Then I have ice

  @spring
  Scenario: xml bean injected
    Then I have glass

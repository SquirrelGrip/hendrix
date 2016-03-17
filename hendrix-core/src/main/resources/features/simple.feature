@foo
Feature:

  Background:
    Given backgroundStep

  @blah
  Scenario: scenario
    Given scenarioStep

  @bar
  Scenario Outline: scenarioOutline
    Given scenarioOutline step x <x>
    When scenarioOutline step y

  @zap
    Examples:
      | x |
      | a |
      | b |

  @another
  Scenario Outline: add
    Given <x> plus <y> equals <result>

  Examples:
      | x   | y   | result |
      | 1   | 2   | 3      |
      | 100 | 200 | 300    |
      | 100 | 200 | 301    |

  Scenario: failure
    Given 1 plus 2 equals 4
    Given scenarioStep

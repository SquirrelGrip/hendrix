Feature: ForkJoin

  Scenario Outline: Fork Join
    Given a wait of <x> seconds
    When all threads join before
    Given a wait of <x> seconds
    When all threads join after
    Then all examples finish

    Examples:
      | x |
      | 1 |
      | 2 |
      | 3 |
      | 4 |

  Scenario Outline: Non Fork Join
    Given a wait of <x> seconds
    Given a wait of <x> seconds
    When all threads join after
    Then all examples finish

    Examples:
      | x |
      | 2 |

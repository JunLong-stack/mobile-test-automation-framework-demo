@e2e @android
Feature: Android Settings automation demo

  Scenario: Navigate to a settings category
    Given the Settings app is launched
    When the user opens Network and internet settings
    Then the Network and internet screen should be displayed

  Scenario Outline: Search for a settings option
    Given the Settings app is launched
    When the user searches for "<term>"
    Then "<term>" search results should be displayed

    Examples:
      | term      |
      | Bluetooth |
      | Battery   |

  Scenario Outline: Scroll to and open a hidden settings section
    Given the Settings app is launched
    When the user scrolls to "<section>"
    Then the "<section>" settings screen should be displayed

    Examples:
      | section     |
      | System      |
      | About phone |

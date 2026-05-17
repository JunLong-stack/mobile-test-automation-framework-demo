@e2e @android
Feature: Android Settings automation demo 

  Scenario: Navigate to a settings category
    Given the Settings app is launched
    When the user opens Network and internet settings
    Then the Network and internet screen should be displayed

  Scenario: Search for a settings option
    Given the Settings app is launched
    When the user searches for "Bluetooth"
    Then "Bluetooth" search results should be displayed

  Scenario: Scroll and open a hidden settings option
    Given the Settings app is launched
    When the user scrolls to "System"
    Then the "System" settings screen should be displayed
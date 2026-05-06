Feature: Login functionality

  Scenario: Successful login with valid credentials
    Given the mobile application is launched
    When the user enters username "testuser"
    And the user enters password "password123"
    And the user taps the login button
    Then the user should be logged in successfully
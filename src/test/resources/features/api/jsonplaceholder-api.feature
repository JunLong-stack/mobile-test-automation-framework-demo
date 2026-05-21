@api
Feature: JSONPlaceholder REST API

  Demonstrates the framework's API testing layer (REST Assured), independent
  of the mobile UI stack.

  Scenario: Fetching all users returns the full collection
    When a GET request is sent to "/users"
    Then the response status code should be 200
    And the response should contain 10 users

  Scenario: Fetching a single user returns its details
    When a GET request is sent to "/users/1"
    Then the response status code should be 200
    And the response field "name" should equal "Leanne Graham"
    And the response field "email" should not be empty

  Scenario Outline: Fetching a user by id returns that id
    When a GET request is sent to "/users/<id>"
    Then the response status code should be 200
    And the response field "id" should equal "<id>"

    Examples:
      | id |
      | 1  |
      | 5  |
      | 10 |

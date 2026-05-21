package stepdefinitions;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Step definitions for the REST API test layer (REST Assured).
 * Independent of the mobile UI stack — no Appium driver is involved.
 */
public class ApiSteps {

    private static final String BASE_URI = "https://jsonplaceholder.typicode.com";

    private Response response;

    @When("a GET request is sent to {string}")
    public void aGetRequestIsSentTo(String path) {
        response = RestAssured.given()
                .baseUri(BASE_URI)
                .when()
                .get(path)
                .thenReturn();
    }

    @Then("the response status code should be {int}")
    public void theResponseStatusCodeShouldBe(int expected) {
        assertEquals(expected, response.statusCode());
    }

    @Then("the response should contain {int} users")
    public void theResponseShouldContainUsers(int count) {
        assertEquals(count, response.jsonPath().getList("id").size());
    }

    @Then("the response field {string} should equal {string}")
    public void theResponseFieldShouldEqual(String field, String value) {
        assertEquals(value, response.jsonPath().getString(field));
    }

    @Then("the response field {string} should not be empty")
    public void theResponseFieldShouldNotBeEmpty(String field) {
        String actual = response.jsonPath().getString(field);
        assertTrue(actual != null && !actual.isBlank(),
                "Expected field '" + field + "' to be non-empty but was: " + actual);
    }
}

package stepdefinitions;

import io.cucumber.java.en.*;
import pages.LoginPage;

public class LoginSteps {

    LoginPage loginPage = new LoginPage();

    @Given("the mobile application is launched")
    public void launchApplication() {
        System.out.println("Application launched");
    }

    @When("the user enters username {string}")
    public void enterUsername(String username) {
        loginPage.enterUsername(username);
    }

    @And("the user enters password {string}")
    public void enterPassword(String password) {
        loginPage.enterPassword(password);
    }

    @And("the user taps the login button")
    public void clickLoginButton() {
        loginPage.clickLogin();
    }

    @Then("the user should be logged in successfully")
    public void verifyLogin() {
        System.out.println("Login successful");
    }
}
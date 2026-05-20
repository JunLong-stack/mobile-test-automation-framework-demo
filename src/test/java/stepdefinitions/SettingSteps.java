package stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.SettingsPage;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SettingSteps {

    private final SettingsPage settingsPage = new SettingsPage();

    @Given("the Settings app is launched")
    public void theSettingsAppIsLaunched() {
        // Driver lifecycle is owned by Hooks#initDriver; this step
        // documents the precondition every scenario depends on.
    }

    @When("the user opens Network and internet settings")
    public void theUserOpensNetworkAndInternetSettings() {
        settingsPage.openNetworkAndInternet();
    }

    @Then("the Network and internet screen should be displayed")
    public void theNetworkAndInternetScreenShouldBeDisplayed() {
        assertTrue(
                settingsPage.isNetworkScreenDisplayed());
    }

    @When("the user searches for {string}")
    public void theUserSearchesFor(String searchTerm) {
        settingsPage.searchFor(searchTerm);
    }

    @Then("{string} search results should be displayed")
    public void searchResultsShouldBeDisplayed(String searchTerm) {
        assertTrue(
                settingsPage.isSearchResultDisplayed(searchTerm));
    }

    @When("the user scrolls to {string}")
    public void theUserScrollsTo(String settingName) {
        settingsPage.scrollTo(settingName);
    }

    @Then("the {string} settings screen should be displayed")
    public void theSettingScreenShouldBeDisplayed(String settingName) {
        assertTrue(
                settingsPage.isSettingScreenDisplayed(settingName));
    }
}

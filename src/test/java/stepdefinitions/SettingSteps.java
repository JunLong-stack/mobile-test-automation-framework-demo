package stepdefinitions;

import base.BaseTest;
import io.cucumber.java.After;
import io.cucumber.java.en.*;
import org.junit.Assert;
import pages.SettingsPage;

public class SettingSteps extends BaseTest {

    private final SettingsPage settingsPage = new SettingsPage();

    @Given("the Settings app is launched")
    public void theSettingsAppIsLaunched() throws Exception {
        setUp();
    }

    @When("the user opens Network and internet settings")
    public void theUserOpensNetworkAndInternetSettings() {
        settingsPage.openNetworkAndInternet();
    }

    @Then("the Network and internet screen should be displayed")
    public void theNetworkAndInternetScreenShouldBeDisplayed() {
        Assert.assertTrue(
                settingsPage.isNetworkScreenDisplayed());
    }

    @When("the user searches for {string}")
    public void theUserSearchesFor(String searchTerm) {
        settingsPage.searchFor(searchTerm);
    }

    @Then("{string} search results should be displayed")
    public void searchResultsShouldBeDisplayed(String searchTerm) {
        Assert.assertTrue(
                settingsPage.isSearchResultDisplayed(searchTerm));
    }

    @When("the user scrolls to {string}")
    public void theUserScrollsTo(String settingName) {
        settingsPage.scrollTo(settingName);
    }

    @Then("the {string} settings screen should be displayed")
    public void theSettingScreenShouldBeDisplayed(String settingName) {
        Assert.assertTrue(
                settingsPage.isSettingScreenDisplayed(settingName));
    }

    @After
    public void tearDownScenario() {

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        tearDown();
    }
}
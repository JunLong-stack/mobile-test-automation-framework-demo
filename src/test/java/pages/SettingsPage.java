package pages;

import base.DriverFactory;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import utils.ConfigReader;
import utils.WaitUtils;

/**
 * Page object representing Android Settings app interactions.
 */
public class SettingsPage {

        private final int explicitWait = ConfigReader.getIntProperty("explicitWait");

        private AndroidDriver driver() {
                return DriverFactory.getDriver();
        }

        // Locators
        private final By networkAndInternetLocator = AppiumBy.androidUIAutomator(
                        "new UiSelector().text(\"Network & internet\")");

        private final By internetLocator = AppiumBy.androidUIAutomator(
                        "new UiSelector().text(\"Internet\")");

        private final By searchButtonLocator = AppiumBy.id(
                        "com.android.settings:id/search_action_bar_title");

        private final By searchInputLocator = AppiumBy.id(
                        "com.google.android.settings.intelligence:id/open_search_view_edit_text");

        public void openNetworkAndInternet() {
                WaitUtils.waitForClickable(
                                driver(),
                                networkAndInternetLocator,
                                explicitWait).click();
        }

        public boolean isNetworkScreenDisplayed() {
                return WaitUtils.waitForVisible(
                                driver(),
                                internetLocator,
                                explicitWait).isDisplayed();
        }

        public void searchFor(String searchTerm) {
                openSearchView();

                WaitUtils.waitForVisible(
                                driver(),
                                searchInputLocator,
                                explicitWait).sendKeys(searchTerm);
        }

        /**
         * Opens the Settings search view. Tapping the search entry launches a
         * separate app (Settings Intelligence); the tap occasionally fails to
         * trigger that transition, so retry while still on the home screen.
         */
        private void openSearchView() {
                for (int attempt = 1; attempt <= 3; attempt++) {
                        if (isVisible(searchButtonLocator, 3)) {
                                WaitUtils.waitForClickable(
                                                driver(),
                                                searchButtonLocator,
                                                explicitWait).click();
                        }
                        if (isVisible(searchInputLocator, 8)) {
                                return;
                        }
                }
        }

        private boolean isVisible(By locator, int timeoutInSeconds) {
                try {
                        WaitUtils.waitForVisible(driver(), locator, timeoutInSeconds);
                        return true;
                } catch (TimeoutException e) {
                        return false;
                }
        }

        public boolean isSearchResultDisplayed(String searchTerm) {
                By searchResultLocator = AppiumBy.androidUIAutomator(
                                "new UiSelector().textContains(\""
                                                + searchTerm +
                                                "\")");

                return WaitUtils.waitForVisible(
                                driver(),
                                searchResultLocator,
                                explicitWait).isDisplayed();
        }

        public void scrollTo(String settingName) {

                WaitUtils.waitForClickable(
                                driver(),
                                AppiumBy.androidUIAutomator(
                                                "new UiScrollable(new UiSelector().scrollable(true))"
                                                                + ".scrollIntoView("
                                                                + "new UiSelector().textContains(\""
                                                                + settingName
                                                                + "\"))"),
                                explicitWait).click();
        }

        public void openSetting(String settingName) {
                By settingLocator = AppiumBy.androidUIAutomator(
                                "new UiSelector().textContains(\""
                                                + settingName
                                                + "\")");

                WaitUtils.waitForClickable(
                                driver(),
                                settingLocator,
                                explicitWait).click();
        }

        public boolean isSettingScreenDisplayed(String settingName) {

                By screenLocator = AppiumBy.androidUIAutomator(
                                "new UiSelector().textContains(\""
                                                + settingName
                                                + "\")");

                return WaitUtils.waitForVisible(
                                driver(),
                                screenLocator,
                                explicitWait).isDisplayed();
        }
}

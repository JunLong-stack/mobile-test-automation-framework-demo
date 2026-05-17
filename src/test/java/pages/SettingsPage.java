package pages;

import base.BaseTest;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
import utils.ConfigReader;
import utils.WaitUtils;

/**
 * Page object representing Android Settings app interactions.
 */
public class SettingsPage extends BaseTest {

        private final int EXPLICIT_WAIT = ConfigReader.getIntProperty("explicitWait");

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
                                driver,
                                networkAndInternetLocator,
                                EXPLICIT_WAIT).click();
        }

        public boolean isNetworkScreenDisplayed() {
                return WaitUtils.waitForVisible(
                                driver,
                                internetLocator,
                                EXPLICIT_WAIT).isDisplayed();
        }

        public void searchFor(String searchTerm) {

                WaitUtils.waitForClickable(
                                driver,
                                searchButtonLocator,
                                EXPLICIT_WAIT).click();

                WaitUtils.waitForVisible(
                                driver,
                                searchInputLocator,
                                EXPLICIT_WAIT).click();

                WaitUtils.waitForVisible(
                                driver,
                                searchInputLocator,
                                EXPLICIT_WAIT).sendKeys(searchTerm);
        }

        public boolean isSearchResultDisplayed(String searchTerm) {
                By searchResultLocator = AppiumBy.androidUIAutomator(
                                "new UiSelector().textContains(\""
                                                + searchTerm +
                                                "\")");

                return WaitUtils.waitForVisible(
                                driver,
                                searchResultLocator,
                                EXPLICIT_WAIT).isDisplayed();
        }

        public void scrollTo(String settingName) {

                WaitUtils.waitForClickable(
                                driver,
                                AppiumBy.androidUIAutomator(
                                                "new UiScrollable(new UiSelector().scrollable(true))"
                                                                + ".scrollIntoView("
                                                                + "new UiSelector().textContains(\""
                                                                + settingName
                                                                + "\"))"),
                                EXPLICIT_WAIT).click();
        }

        public void openSetting(String settingName) {
                System.out.println("Trying to open setting: " + settingName);
                By settingLocator = AppiumBy.androidUIAutomator(
                                "new UiSelector().textContains(\""
                                                + settingName
                                                + "\")");

                WaitUtils.waitForClickable(
                                driver,
                                settingLocator,
                                EXPLICIT_WAIT).click();
        }

        public boolean isSettingScreenDisplayed(String settingName) {

                By screenLocator = AppiumBy.androidUIAutomator(
                                "new UiSelector().textContains(\""
                                                + settingName
                                                + "\")");

                return WaitUtils.waitForVisible(
                                driver,
                                screenLocator,
                                EXPLICIT_WAIT).isDisplayed();
        }
}
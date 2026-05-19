package stepdefinitions;

import base.DriverFactory;
import io.appium.java_client.android.AndroidDriver;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import utils.ScreenshotUtils;

import java.net.MalformedURLException;

/**
 * Cucumber lifecycle hooks. Owns driver setup, teardown,
 * and failure-screenshot capture so step definitions stay focused
 * on test logic.
 */
public class Hooks {

    @Before
    public void initDriver() throws MalformedURLException {
        DriverFactory.initDriver();
    }

    @After
    public void tearDownScenario(Scenario scenario) {
        AndroidDriver driver = DriverFactory.getDriver();

        if (scenario.isFailed() && driver != null) {
            ScreenshotUtils.capture(driver, scenario);
        }

        DriverFactory.quitDriver();
    }
}

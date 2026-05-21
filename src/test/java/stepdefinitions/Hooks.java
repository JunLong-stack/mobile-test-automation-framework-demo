package stepdefinitions;

import base.DriverFactory;
import io.appium.java_client.android.AndroidDriver;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.ScreenshotUtils;

import java.net.MalformedURLException;

/**
 * Cucumber lifecycle hooks. Owns driver setup, teardown,
 * and failure-screenshot capture so step definitions stay focused
 * on test logic.
 */
public class Hooks {

    private static final Logger log = LoggerFactory.getLogger(Hooks.class);

    @Before("@e2e")
    public void initDriver(Scenario scenario) throws MalformedURLException {
        log.info("Starting scenario: {}", scenario.getName());
        DriverFactory.initDriver();
    }

    @After("@e2e")
    public void tearDownScenario(Scenario scenario) {
        AndroidDriver driver = DriverFactory.getDriver();

        if (scenario.isFailed() && driver != null) {
            log.warn("Scenario failed: {} — capturing screenshot", scenario.getName());
            ScreenshotUtils.capture(driver, scenario);
        } else {
            log.info("Scenario finished: {} [{}]", scenario.getName(), scenario.getStatus());
        }

        DriverFactory.quitDriver();
    }
}

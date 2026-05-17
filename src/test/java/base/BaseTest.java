package base;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import utils.ConfigReader;

import java.net.MalformedURLException;
import java.net.URI;
import java.time.Duration;

/**
 * Base test class responsible for Appium driver setup
 * and teardown for Android UI automation tests.
 */
public class BaseTest {

    protected static AndroidDriver driver = null;

    protected static void setUp() throws MalformedURLException {
        UiAutomator2Options options = new UiAutomator2Options()
                .setPlatformName(ConfigReader.getProperty("platformName"))
                .setDeviceName(ConfigReader.getProperty("deviceName"))
                .setAutomationName(ConfigReader.getProperty("automationName"))
                .setAppPackage(ConfigReader.getProperty("appPackage"))
                .setAppActivity(ConfigReader.getProperty("appActivity"));

        driver = new AndroidDriver(
            URI.create(
                ConfigReader.getProperty("appiumServerUrl")
            ).toURL(),
            options
        );

        driver.manage().timeouts().implicitlyWait(
            Duration.ofSeconds(
                ConfigReader.getIntProperty("implicitWait")
            )
        );
    }

    protected static void tearDown() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
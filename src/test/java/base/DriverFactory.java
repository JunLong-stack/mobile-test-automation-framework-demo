package base;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.ConfigReader;

import java.net.MalformedURLException;
import java.net.URI;
import java.time.Duration;

/**
 * Thread-safe Appium driver lifecycle. One AndroidDriver instance
 * is kept per thread, enabling parallel scenario execution.
 */
public class DriverFactory {

    private DriverFactory() {
        throw new IllegalStateException("Utility class");
    }

    private static final Logger log = LoggerFactory.getLogger(DriverFactory.class);
    private static final ThreadLocal<AndroidDriver> DRIVER = new ThreadLocal<>();

    public static void initDriver() throws MalformedURLException {
        log.info("Initializing AndroidDriver for device '{}'",
                ConfigReader.getProperty("deviceName"));

        UiAutomator2Options options = new UiAutomator2Options()
                .setPlatformName(ConfigReader.getProperty("platformName"))
                .setDeviceName(ConfigReader.getProperty("deviceName"))
                .setAutomationName(ConfigReader.getProperty("automationName"))
                .setAppPackage(ConfigReader.getProperty("appPackage"))
                .setAppActivity(ConfigReader.getProperty("appActivity"));

        // Start every scenario from a clean app state: relaunch the target
        // activity even if the app is already running, and stop the app when
        // the session ends. Prevents UI state leaking between scenarios.
        options.setCapability("appium:forceAppLaunch", true);
        options.setCapability("appium:shouldTerminateApp", true);

        AndroidDriver driver = new AndroidDriver(
                URI.create(ConfigReader.getProperty("appiumServerUrl")).toURL(),
                options);

        driver.manage().timeouts().implicitlyWait(
                Duration.ofSeconds(ConfigReader.getIntProperty("implicitWait")));

        DRIVER.set(driver);
    }

    public static AndroidDriver getDriver() {
        return DRIVER.get();
    }

    public static void quitDriver() {
        AndroidDriver driver = DRIVER.get();
        if (driver != null) {
            log.info("Quitting AndroidDriver");
            driver.quit();
            DRIVER.remove();
        }
    }
}

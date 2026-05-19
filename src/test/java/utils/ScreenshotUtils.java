package utils;

import io.appium.java_client.android.AndroidDriver;
import io.cucumber.java.Scenario;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Utility class for capturing screenshots on test failure
 * and attaching them to the Cucumber report.
 */
public class ScreenshotUtils {

    private ScreenshotUtils() {
        throw new IllegalStateException("Utility class");
    }

    private static final Logger log = LoggerFactory.getLogger(ScreenshotUtils.class);
    private static final Path SCREENSHOT_DIR = Paths.get("target", "screenshots");
    private static final DateTimeFormatter TIMESTAMP =
            DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss");

    public static void capture(AndroidDriver driver, Scenario scenario) {
        try {
            byte[] screenshot = ((TakesScreenshot) driver)
                    .getScreenshotAs(OutputType.BYTES);

            scenario.attach(screenshot, "image/png", scenario.getName());

            Allure.addAttachment(
                    scenario.getName(),
                    "image/png",
                    new ByteArrayInputStream(screenshot),
                    "png");

            Files.createDirectories(SCREENSHOT_DIR);
            String fileName = sanitize(scenario.getName())
                    + "_" + LocalDateTime.now().format(TIMESTAMP) + ".png";
            Files.write(SCREENSHOT_DIR.resolve(fileName), screenshot);
        } catch (IOException | RuntimeException e) {
            log.warn("Failed to capture screenshot: {}", e.getMessage());
        }
    }

    private static String sanitize(String name) {
        return name.replaceAll("[^a-zA-Z0-9.-]", "_");
    }
}

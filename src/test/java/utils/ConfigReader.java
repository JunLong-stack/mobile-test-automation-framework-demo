package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Utility class for loading and accessing framework
 * configuration values from the config.properties file.
 */
public class ConfigReader {

    private ConfigReader() {
        throw new IllegalStateException("Utility class");
    }

    private static final Properties properties = new Properties();

    static {
        try (InputStream inputStream = ConfigReader.class
                .getClassLoader()
                .getResourceAsStream("config.properties")) {

            if (inputStream == null) {
                throw new RuntimeException("config.properties file not found");
            }

            properties.load(inputStream);

        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties file", e);
        }
    }

    public static String getProperty(String key) {
        String value = properties.getProperty(key);

        if (value == null || value.trim().isEmpty()) {
            throw new RuntimeException("Property not found: " + key);
        }

        return value;
    }

    public static int getIntProperty(String key) {
        return Integer.parseInt(getProperty(key));
    }
}
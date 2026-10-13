package utils;

import pages.BaseClass;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Loads src/main/resources/config.properties into BaseClass.properties.
 *
 * Any key can be overridden for one run with a JVM property of the same name,
 * e.g. mvn test -Dbrowser=firefox -Dheadless=true
 */
public class PropertyLoader {

    /**
     * Reads the file, then applies -D overrides.
     */
    public PropertyLoader() {
        // try-with-resources closes the file even when reading fails
        try (InputStream configFile = new FileInputStream("src/main/resources/config.properties")) {
            BaseClass.properties.load(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // A -D value with the same key wins over the file
        for (String key : BaseClass.properties.stringPropertyNames()) {
            String override = System.getProperty(key);
            if (override != null && !override.isEmpty()) {
                BaseClass.properties.setProperty(key, override);
            }
        }
    }
}

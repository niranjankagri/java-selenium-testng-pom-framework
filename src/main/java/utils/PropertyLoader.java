package utils;

import pages.BaseClass;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class PropertyLoader {
    public PropertyLoader() {
        try (InputStream configFile = new FileInputStream("src/main/resources/config.properties")) {
            BaseClass.properties.load(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (String key : BaseClass.properties.stringPropertyNames()) {
            String override = System.getProperty(key);
            if (override != null && !override.isEmpty()) {
                BaseClass.properties.setProperty(key, override);
            }
        }
    }
}

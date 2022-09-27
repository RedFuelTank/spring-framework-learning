package utils;

import java.io.IOException;
import java.io.StringReader;
import java.util.Properties;

public class ConfigLoader {
    public static Properties loadPropertiesFileFromPath(String path) {
        String content = FileReader.readContentFromPath(path);
        Properties properties = new Properties();

        try (StringReader stringReader = new StringReader(content)) {
            properties.load(stringReader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return properties;
    }
}

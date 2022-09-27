package utils;

import java.util.Properties;

public final class ApplicationProperties {
    private static final String APPLICATION_PROPERTIES_PATH = "application.properties";
    public static final String DATABASE_URL_KEY = "dbUrl";
    public static final String DATABASE_USER_KEY = "dbUser";
    public static final String DATABASE_PASSWORD_KEY = "dbPassword";
    private static Properties file = ConfigLoader.loadPropertiesFileFromPath(APPLICATION_PROPERTIES_PATH);

    private ApplicationProperties() {}

    public static String getDataBaseUrl() {
        return file.getProperty(DATABASE_URL_KEY);
    }

    public static String getDataBaseUser() {
        return file.getProperty(DATABASE_USER_KEY);
    }

    public static String getDataBasePassword() {
        return file.getProperty(DATABASE_PASSWORD_KEY);
    }
}

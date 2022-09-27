package database;

import org.apache.commons.lang3.StringUtils;
import utils.ApplicationProperties;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SqlExecutor {
    private static final ConnectionInfo CONNECTION_INFO = ConnectionInfo.of(ApplicationProperties.getDataBaseUrl(),
            ApplicationProperties.getDataBaseUser(), ApplicationProperties.getDataBasePassword());

    private static final DataSource DATA_SOURCE = DataSourceProvider.getDataSource(CONNECTION_INFO);

    public static <T> T executeQuery(String query, Class<T> object) {
        try (Connection connection = DATA_SOURCE.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet set = statement.executeQuery(query);
            if (set != null) {
                set.next();
                return castResultSetTo(set, object);
            }
            throw new IllegalArgumentException();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static <T> T castResultSetTo(ResultSet set, Class<T> object) throws SQLException {
        try {
            T instance = object.getDeclaredConstructor().newInstance();

            for (Field field : object.getDeclaredFields()) {
                String fieldName = field.getName();
                Class<?> type = field.getType();
                Object value = set.getObject(fieldName);
                Method setter = object.getMethod("set" + StringUtils.capitalize(fieldName), type);
                setter.invoke(instance, value);
            }
            return instance;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public static void executeUpdate(String query) {
        try (Connection connection = DATA_SOURCE.getConnection();
             Statement statement = connection.createStatement()) {

            statement.executeUpdate(query);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}

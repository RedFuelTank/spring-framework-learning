import database.ConnectionInfo;
import database.DataSourceProvider;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import utils.ApplicationProperties;
import utils.FileReader;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@WebListener
public class ContextListener implements ServletContextListener {
    private static final ConnectionInfo CONNECTION_INFO = ConnectionInfo.ofData(ApplicationProperties.getDataBaseUrl(),
            ApplicationProperties.getDataBaseUser(), ApplicationProperties.getDataBasePassword());

    private static final DataSource DATA_SOURCE = DataSourceProvider.getDataSource(CONNECTION_INFO);
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        String test = FileReader.readContentFromPath("order_table_create.sql");

        try (Connection connection = DATA_SOURCE.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(test);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Initialized");
    }
}

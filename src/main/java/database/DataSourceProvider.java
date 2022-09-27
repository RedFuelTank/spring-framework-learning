package database;


import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;

public class DataSourceProvider {
    public static DataSource getDataSource(ConnectionInfo connectionInfo) {
        return createBasicConnectionPool(connectionInfo);
    }

    private static DataSource createBasicConnectionPool(ConnectionInfo connectionInfo) {
        BasicDataSource pool = new BasicDataSource();
        pool.setDriverClassName("org.postgresql.Driver");
        pool.setUrl(connectionInfo.getDataBaseUrl());
        pool.setUsername(connectionInfo.getDataBaseUser());
        pool.setPassword(connectionInfo.getDataBasePassword());
        pool.setMaxTotal(3);
        pool.setInitialSize(1);

        try {
            // has the side effect of initializing the connection pool
            pool.getLogWriter();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return pool;
    }
}

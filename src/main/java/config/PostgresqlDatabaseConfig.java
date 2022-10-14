package config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:/application.properties")
public class PostgresqlDatabaseConfig {

    private Environment env;

    public PostgresqlDatabaseConfig(Environment env) {
        this.env = env;
    }

    @Bean
    public DataSource dataSource() {
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName("org.postgresql.Driver");
        ds.setUsername(env.getProperty("dbUser"));
        ds.setPassword(env.getProperty("dbPassword"));
        ds.setUrl(env.getProperty("dbUrl"));
        ds.setMaxTotal(3);
        ds.setInitialSize(1);

        var populator = new ResourceDatabasePopulator(
                new ClassPathResource("order_table_create.sql")
        );

        DatabasePopulatorUtils.execute(populator, ds);

        return ds;
    }

}
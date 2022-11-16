package config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:/application.properties")
public class HsqlDatabaseConfig {

    private Environment env;

    public HsqlDatabaseConfig(Environment env) {
        this.env = env;
    }

    @Bean
    public DataSource dataSource() {
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName("org.hsqldb.jdbcDriver");
        ds.setUrl(env.getProperty("hsql.url"));
        ds.setMaxTotal(3);
        ds.setInitialSize(1);

//        var populator = new ResourceDatabasePopulator(
//                new ClassPathResource("order_table_create.sql")
//        );
//
//        DatabasePopulatorUtils.execute(populator, ds);

        return ds;
    }

    @Bean(name = "dialect")
    public String getDialect() {
        return "org.hibernate.dialect.HSQLDialect";
    }
}
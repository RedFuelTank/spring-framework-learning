package config;

import jakarta.persistence.EntityManagerFactory;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableWebMvc
@EnableTransactionManagement
@ComponentScan(basePackages = {"orders", "model", "database", "config"})
public class Config {
    @Bean
    public EntityManagerFactory getEntityManager(DataSource dataSource, @Qualifier("dialect") String dialect) {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator(new ClassPathResource("schema.sql"));
        populator.execute(dataSource);

        LocalContainerEntityManagerFactoryBean manager = new LocalContainerEntityManagerFactoryBean();
        manager.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        manager.setDataSource(dataSource);
        manager.setPackagesToScan("model");
        manager.setJpaProperties(additionalProperties(dialect));
        manager.afterPropertiesSet();
        return manager.getObject();
    }

    private Properties additionalProperties(String dialect) {
        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", "validate");
        properties.setProperty("hibernate.dialect", dialect);
        properties.setProperty("hibernate.show_sql", "true");
        return properties;
    }

    @Bean
    public PlatformTransactionManager transactionManager(
            EntityManagerFactory entityManagerFactory) {

        return new JpaTransactionManager(entityManagerFactory);
    }
}

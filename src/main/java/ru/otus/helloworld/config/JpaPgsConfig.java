package ru.otus.helloworld.config;

import com.zaxxer.hikari.HikariConfig;
import java.util.Properties;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.AbstractJpaVendorAdapter;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableConfigurationProperties(JpaProperties.class)
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "pgsEntityManager",
    transactionManagerRef = "pgsTransactionManager",
    basePackages = "ru.otus.helloworld.dao.repository")
public class JpaPgsConfig extends HikariConfig {

    @Bean
    public LocalContainerEntityManagerFactoryBean pgsEntityManager(JpaProperties jpaProperties,
                                                                   @Qualifier("postgresDataSource") DataSource pgExtDataSource) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(pgExtDataSource);
        final Properties properties = new Properties();
        properties.putAll(jpaProperties.getProperties());
        em.setJpaProperties(properties);
        em.setPackagesToScan("ru.otus.helloworld.dao.entity");
        em.setJpaVendorAdapter(createJpaVendorAdapter(jpaProperties.isShowSql()));
        em.setPersistenceUnitName("jpaPgs");
        return em;
    }

    protected AbstractJpaVendorAdapter createJpaVendorAdapter(boolean isShowSql) {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setDatabasePlatform("org.hibernate.dialect.PostgreSQL10Dialect");
        vendorAdapter.setDatabase(Database.POSTGRESQL);
        vendorAdapter.setShowSql(isShowSql);
        return vendorAdapter;
    }

    @Bean
    public PlatformTransactionManager pgsTransactionManager(
        @Qualifier("pgsEntityManager") LocalContainerEntityManagerFactoryBean pgExtEntityManager) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(pgExtEntityManager.getObject());
        return transactionManager;
    }
}

package ru.otus.helloworld.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.flyway.FlywayDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@ConfigurationProperties(prefix = "user.datasource.hikari")
public class PgsDataSourceConfig extends HikariConfig {

    @FlywayDataSource
    @Bean(name = "postgresDataSource")
    public DataSource pgsDataSource() {
        return new HikariDataSource(this);
    }
}

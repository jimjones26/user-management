package com.uxfx.usermanagement.config;

import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import javax.sql.DataSource;
import org.flywaydb.core.Flyway;

@Configuration
public class DatabaseConfig {

    @Bean
    public FlywayMigrationStrategy flywayMigrationStrategy() {
        return flyway -> {
            // Do nothing - this prevents Flyway from auto-migrating
            // We'll handle migration explicitly
        };
    }

    @Bean
    public Flyway flyway(DataSource dataSource) {
        Flyway flyway = Flyway.configure()
            .dataSource(dataSource)
            .locations("classpath:db/migration")
            .baselineOnMigrate(true)
            .baselineVersion("0")
            .outOfOrder(true)
            .load();
        
        flyway.migrate();
        return flyway;
    }
}
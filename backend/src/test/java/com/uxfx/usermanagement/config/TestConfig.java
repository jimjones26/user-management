package com.uxfx.usermanagement.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

/**
 * Test configuration for database initialization in tests
 */
@TestConfiguration
public class TestConfig {
    
    /**
     * Primary test database configuration
     * This will be used instead of the application's main datasource during tests
     */
    @Bean
    @Primary
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5433/user_management_test");
        dataSource.setUsername("jjones");
        dataSource.setPassword("password");
        return dataSource;
    }
    
    /**
     * In-memory database for faster tests that don't require PostgreSQL
     */
    @Bean(name = "inMemoryDataSource")
    public DataSource inMemoryDataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("classpath:schema.sql")
                .addScript("classpath:test-data.sql")
                .build();
    }
}

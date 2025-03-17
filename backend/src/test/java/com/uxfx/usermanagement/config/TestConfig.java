package com.uxfx.usermanagement.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import javax.sql.DataSource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@TestConfiguration
public class TestConfig {
    
    @Bean
    @Primary
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5433/user_management");
        dataSource.setUsername("jjones");
        dataSource.setPassword("password");
        return dataSource;
    }
}
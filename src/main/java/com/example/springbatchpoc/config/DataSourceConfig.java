package com.example.springbatchpoc.config;

import javax.sql.DataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class DataSourceConfig {

    @Bean(name = "originDataSource")
    @ConfigurationProperties(prefix = "origin.datasource")
    public DataSource originDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean(name = "targetDataSource")
    @ConfigurationProperties(prefix = "target.datasource")
    public DataSource targetDataSource() {
        return DataSourceBuilder.create().build();
    }

}

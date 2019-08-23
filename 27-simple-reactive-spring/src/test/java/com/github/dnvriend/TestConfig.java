package com.github.dnvriend;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/*
    see: https://docs.spring.io/spring-data/r2dbc/docs/1.0.0.M2/reference/html/#reference
 */
@EnableR2dbcRepositories
@EnableAutoConfiguration
@EnableTransactionManagement
@Configuration
@ComponentScan
@EntityScan
public class TestConfig {
}

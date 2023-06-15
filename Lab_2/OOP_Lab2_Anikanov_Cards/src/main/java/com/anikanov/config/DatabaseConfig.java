package com.anikanov.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories("com.anikanov.repository")
@EntityScan("com.anikanov.domain.model")
@EnableJpaAuditing
public class DatabaseConfig {
}

package com.kuznetsov.databasecleaner.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

@ConfigurationProperties(prefix = "database-cleaner")
@Configuration
@Getter
@Setter
public class DatabaseCleanerProperties {
    private Set<String> allowedDateTimeColumns;
}

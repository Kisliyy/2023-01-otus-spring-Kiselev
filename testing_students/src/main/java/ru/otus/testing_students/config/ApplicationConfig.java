package ru.otus.testing_students.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(value = AppProperty.class)
public class ApplicationConfig {
}

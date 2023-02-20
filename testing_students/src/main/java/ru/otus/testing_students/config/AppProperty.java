package ru.otus.testing_students.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Locale;

@ConfigurationProperties(prefix = "application")
@Getter
@Setter
public class AppProperty {

    private String pathData;
    private int countRightAnswer;
    private Locale locale;
}

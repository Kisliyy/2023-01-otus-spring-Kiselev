package ru.otus.testing_students.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class AppConfig {

    private final String pathToQuestions;
    private final int countAnswer;

    public AppConfig(@Value("${application.path.questions}") String pathToQuestions,
                     @Value("${application.answer.wright.count}") int countAnswer) {
        this.pathToQuestions = pathToQuestions;
        this.countAnswer = countAnswer;
    }
}

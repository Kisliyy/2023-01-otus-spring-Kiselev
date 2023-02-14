package ru.otus.testing_students;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import ru.otus.testing_students.service.testing.TestingService;

import java.io.IOException;

@PropertySource("classpath:application.properties")
@ComponentScan("ru.otus.testing_students")
public class TestingStudentsApplication {
    public static void main(String[] args) throws IOException {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(TestingStudentsApplication.class);

        TestingService surveyService = context.getBean(TestingService.class);
        surveyService.conductSurvey();
    }
}

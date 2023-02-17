package ru.otus.testing_students;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.otus.testing_students.service.testing.TestingService;

import java.io.IOException;

@SpringBootApplication
public class TestingStudentsApplication {
    public static void main(String[] args) throws IOException {
        ConfigurableApplicationContext context = SpringApplication.run(TestingStudentsApplication.class);
        TestingService testingService = context.getBean(TestingService.class);
        testingService.conductSurvey();
        context.close();
    }
}
